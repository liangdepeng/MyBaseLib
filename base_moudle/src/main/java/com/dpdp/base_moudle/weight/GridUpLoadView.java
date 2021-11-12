package com.dpdp.base_moudle.weight;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dpdp.base_moudle.R;
import com.dpdp.base_moudle.base.ui.BaseActivity;
import com.dpdp.base_moudle.dialog.GlideEngine;
import com.dpdp.base_moudle.http.HttpCallback;
import com.dpdp.base_moudle.http.volley.HttpHelper;
import com.dpdp.base_moudle.interfaces.SingleCallback;
import com.dpdp.base_moudle.utils.DeviceUtil;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;


/**
 * 评论 上传图片
 * <p>
 * 适配屏幕需要自己根据布局计算 图片边长 slideWith
 */
public class GridUpLoadView extends FrameLayout {

    // 选中的 图片url list
    private ArrayList<String> selectPicList = new ArrayList<>();
    private CommitUpLoadImageAdapter upLoadImageAdapter;
    // 最大上传图片数量
    private int maxImageCounts = 5;
    // 图片最大选中数量 通过计算控制 选中图片数量
    private int picMaxSelectCounts = 5;
    private BaseActivity activity;
    // 末尾占位图（即添加图片的 占位图 占一个item位置）
    private static final String PLACE_HOLDER = "placeHolder";
    //标记是否 限制添加图片 图片达到上限之后 会限制不能上传
    private boolean isLimited = false;
    // 添加图片和删除图片 回调
    private ImageCountsChangedCallback callback;
    // 适配 屏幕 设置每一个 图片的边长 【屏幕宽度 减去 padding，margin】
    private int slideWith;
    private RecyclerView recyclerView;
    // 拖动
    private ItemTouchHelper itemTouchHelper;
    private View loadingView;

    public GridUpLoadView(@NonNull Context context) {
        this(context, null);
    }

    public GridUpLoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridUpLoadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.ui_up_load_image_layout, this, true);
        recyclerView = findViewById(R.id.recyclerView);
        loadingView = findViewById(R.id.loading_v);
        selectPicList.add(PLACE_HOLDER);
        upLoadImageAdapter = new CommitUpLoadImageAdapter(context, selectPicList, s -> {
            if (callback != null) {
                callback.deleteImage(s);
            }

            if (selectPicList.size() == maxImageCounts && !selectPicList.contains(PLACE_HOLDER)) {
                selectPicList.remove(s);
                selectPicList.add(PLACE_HOLDER);
            } else {
                selectPicList.remove(s);
            }

            isLimited = false;
            upLoadImageAdapter.notifyDataSetChanged();
        });

        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(upLoadImageAdapter);
    }

    private String fileUploadUrl;

    /**
     * 初始化 设置最大图片数量 列
     */
    public GridUpLoadView initImageCounts(BaseActivity activity,String fileUploadUrl ,int maxImageCounts) {
        this.activity = activity;
        this.fileUploadUrl = fileUploadUrl;
        this.maxImageCounts = maxImageCounts;
        this.picMaxSelectCounts = maxImageCounts;
        return this;
    }

    /**
     * 适配屏幕需要自己计算  正方形 图片边长
     */
    public GridUpLoadView setSlideWith(int slideWith) {
        this.slideWith = slideWith;
        return this;
    }

    /**
     * 添加、删除图片回调
     */
    public GridUpLoadView setCallback(ImageCountsChangedCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 是否支持图片拖动
     */
    public GridUpLoadView openImageDrag(boolean isDrag) {
        if (isDrag && recyclerView != null && upLoadImageAdapter != null) {
            itemTouchHelper = new ItemTouchHelper(new DragItemTouchHelper(upLoadImageAdapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
        return this;
    }

    public RecyclerView getContentGridView() {
        return recyclerView;
    }

    private void addImage(List<String> imageUrls) {
        if (isLimited) {
            return;
        }
        upLoadImage(imageUrls);
    }

    private Vector<String> netImgs=new Vector<>();

    private void upLoadImage(List<String> imageUrls) {
        loadingView.setVisibility(VISIBLE);

        netImgs.clear();

        for (int i=0;i<imageUrls.size();i++){

            HttpHelper.getInstance().uploadImg(fileUploadUrl,new File(imageUrls.get(i)), new HttpCallback() {
                @Override
                public void onSuccess(Object gsonData, Object requestTag) {
                    if (gsonData instanceof String){
                        String imgUrl = String.valueOf(gsonData);
                        netImgs.add(imgUrl);

                        dealImgs(imageUrls);
                    }
                }

                @Override
                public void onError(String errorMsg, Object requestTag) {
                    ToastUtil.showMsg("图片上传失败");
                }
            });
        }

    }

    private synchronized void dealImgs(List<String> imageUrls) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (netImgs.size()== imageUrls.size()){

                    loadingView.setVisibility(GONE);
                    if (netImgs == null || netImgs.size() == 0)
                        return;
                    // 确保选中的图片都上传成功
                    if (imageUrls.size() == netImgs.size()) {

                        if (callback != null) {
                            callback.addImages(netImgs);
                        }

                        selectPicList.addAll(selectPicList.size() - 1, netImgs);

                        if (selectPicList.size() > maxImageCounts) {
                            selectPicList.remove(selectPicList.size() - 1);
                            isLimited = true;
                        }
                        upLoadImageAdapter.notifyDataSetChanged();
                        return;
                    }

                    ToastUtil.showMsg("图片上传失败，请重新上传");

                    isLimited = false;
                }
            }
        });


    }

    /**
     * @param take 1相册 2拍照 3选择文件
     */
    private void checkPicture(Integer take) {
        PictureSelectionModel selectionModel = null;

        if (take == 2) {
            //单独使用相机 媒体类型 PictureMimeType.ofImage()、ofVideo()
            selectionModel = PictureSelector.create(activity).openCamera(PictureMimeType.ofImage());
        } else if (take == 1) {
            //相册 媒体类型 PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()
            selectionModel = PictureSelector.create(activity).openGallery(PictureMimeType.ofImage());
        } else {
            return;
        }

        selectionModel
                .isCompress(true)
                .minimumCompressSize(150)// 小于多少kb的图片不压缩
                .compressQuality(60) //图片压缩后输出质量
                .selectionMode(PictureConfig.MULTIPLE)
                .isAndroidQTransform(Build.VERSION.SDK_INT>=29)
                // 最大数量 - 当前选中图片数量 selectPicList会有一个 末尾的占位图，会多减去一个 再加上 ，当图片达到上限时，占位图会被删除
                // 这边会出现 最小为1 但是 这是 没有入口进来，当删除一张图片 ，又会加上占位图 所以无需考虑特殊情况
                .maxSelectNum(picMaxSelectCounts - selectPicList.size() + 1)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        List<String> list = new ArrayList<>();
                        for (int pos = 0; pos < result.size(); pos++) {
                            String url;
                            if (Build.VERSION.SDK_INT >= 29) {
                                url = result.get(pos).getAndroidQToPath();
                            } else {
                                if (result.get(pos).isCompressed()) {
                                    url = result.get(pos).getCompressPath();
                                } else {
                                    url = result.get(pos).getPath();
                                }
                            }
                            if (!TextUtils.isEmpty(url))
                                list.add(url);
                        }
                        addImage(list);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

    public class CommitUpLoadImageAdapter extends RecyclerView.Adapter<CommitUpLoadImageAdapter.ViewHolder> {

        private Context context;
        private List<String> data;
        private SingleCallback<String> callback;

        public CommitUpLoadImageAdapter(Context context, List<String> data, SingleCallback<String> callback) {
            this.data = data;
            this.context = context;
            this.callback = callback;
        }

        public List<String> getData() {
            return data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.ui_item_upload_view, parent, false);
            return new ViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

            String s = data.get(position);
            boolean isPlaceholder = PLACE_HOLDER.equals(s);

            viewHolder.imageLayout.setVisibility(isPlaceholder ? GONE : VISIBLE);
            viewHolder.placeholderLayout.setVisibility(isPlaceholder ? VISIBLE : GONE);

            if (isPlaceholder) {
                viewHolder.placeholderLayout.setOnClickListener(v -> {
                    if (activity == null) return;
                        checkPicture(1);
                });
            } else {
                viewHolder.deleteIv.setOnClickListener(v -> {
                    if (callback != null) {
                        callback.callback(s);
                    }
                });


                Glide.with(context)
                        .load(parseUrl(s))
                        .transforms(new CenterCrop(),new RoundedCorners(DeviceUtil.dip2px(context, 4)))
                        .error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageIv);

            }

            viewHolder.imageLayout.setOnLongClickListener(v -> {
                if (!isPlaceholder && itemTouchHelper != null) {
                    itemTouchHelper.startDrag(viewHolder);
                }
                return false;
            });


            viewHolder.imageLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Class<?> aClass = Class.forName("com.example.administrator.food.activity.BigImageActivity");
                        Method method = aClass.getDeclaredMethod("startActivity", BaseActivity.class, View.class, String.class);
                        method.invoke(null,activity,viewHolder.imageIv,"translation");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageIv;
            public ImageView deleteIv;
            public FrameLayout imageLayout;
            public ImageView placeholderIv;
            public FrameLayout placeholderLayout;
            public FrameLayout contentLayout;

            public ViewHolder(View rootView) {
                super(rootView);
                if (slideWith != 0) {
                    ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
                    layoutParams.width = slideWith;
                    layoutParams.height = slideWith;
                    rootView.setLayoutParams(layoutParams);
                }
                this.imageIv = rootView.findViewById(R.id.image_iv);
                this.deleteIv = rootView.findViewById(R.id.delete_iv);
                this.imageLayout = rootView.findViewById(R.id.image_layout);
                this.placeholderIv = rootView.findViewById(R.id.placeholder_iv);
                this.contentLayout = rootView.findViewById(R.id.content_layout);
                this.placeholderLayout = rootView.findViewById(R.id.placeholder_layout);
            }
        }
    }

    private String parseUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.startsWith("http"))
            imageUrl = "http://120.26.8.235:88/pic/" + imageUrl;
        return imageUrl;
    }

    public class DragItemTouchHelper extends ItemTouchHelper.Callback {

        private final CommitUpLoadImageAdapter adapter;

        public DragItemTouchHelper(CommitUpLoadImageAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFlags, 0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int targetPosition = target.getAdapterPosition();

            if (adapter == null)
                return false;

            List<String> data = adapter.getData();
            if (data == null)
                return false;

            String s = data.get(targetPosition);

            if (PLACE_HOLDER.equals(s)) {
                return false;
            } else {
                Collections.swap(data, fromPosition, targetPosition);
                adapter.notifyItemMoved(fromPosition, targetPosition);
                if (callback!=null){
                    callback.swapImage(fromPosition,targetPosition);
                }
                return true;
            }
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (viewHolder != null) {
                viewHolder.itemView.setScaleX(1.1f);
                viewHolder.itemView.setScaleY(1.1f);
            }
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setScaleX(1f);
            viewHolder.itemView.setScaleY(1f);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    }

    public interface ImageCountsChangedCallback {
        void addImages(List<String> imageUrls);
        void deleteImage(String url);
        void swapImage(int fromPosition,int targetPosition);
    }
}
