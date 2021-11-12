package com.dpdp.base_moudle.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dpdp.base_moudle.R;


/**
 * 首页 背景 上面是矩形 _底部是圆弧
 */
public class ArcRectFView extends View {

    private Paint paint;
    // 颜色
    private int color;
    // 矩形占的比例
    private int topRectFWeight;
    // 圆弧占的比例
    private int bottomArcWeight;
    private RectF topRectF;
    private Path path;
    private float allWeight;

    public ArcRectFView(Context context) {
        this(context, null);
    }

    public ArcRectFView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcRectFView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.ArcRectFView);
            color = typed.getColor(R.styleable.ArcRectFView_arc_rect_view_color, Color.parseColor("#75EEFA"));
            topRectFWeight = typed.getInt(R.styleable.ArcRectFView_arc_rect_view_rect_weight, 4);
            bottomArcWeight = typed.getInt(R.styleable.ArcRectFView_arc_rect_view_arc_weight, 1);
            typed.recycle();
        }

        allWeight = topRectFWeight + bottomArcWeight;

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        topRectF = new RectF();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();

        topRectF.left = 0;
        topRectF.top = 0;
        topRectF.right = width;
        topRectF.bottom = height / allWeight * topRectFWeight;

        path.moveTo(0, height / allWeight * topRectFWeight - 1);
        path.quadTo(width / 2, height, width, height / allWeight * topRectFWeight - 1);

        // 画顶部矩形
        canvas.drawRect(topRectF, paint);
        // 画弧形
        canvas.drawPath(path, paint);

    }

    public void setPaintColor(int color) {
        if (paint != null) {
            paint.setColor(color);
            invalidate();
        }
    }
}
