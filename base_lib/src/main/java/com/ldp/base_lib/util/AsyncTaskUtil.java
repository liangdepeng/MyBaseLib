package com.ldp.base_lib.util;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-23
 * <p>
 * Summary: 异步任务 简单封装 startTask 与  cancelTask 一一对应 防止内存泄露
 */
public class AsyncTaskUtil {

    private static final HashMap<Object, MyTask<?>> TASK_HASH_MAP = new HashMap<>();

    public static void startTask(IAsyncListener<?> listener, Object taskTag) {
        MyTask<?> myTask = new MyTask<>(listener);
        TASK_HASH_MAP.put(taskTag, myTask);
        myTask.execute(taskTag);
    }

    public static void cancelTask(Object taskTag) {
        MyTask<?> myTask = TASK_HASH_MAP.get(taskTag);
        if (myTask != null) {
            myTask.cancel(true);
            TASK_HASH_MAP.remove(taskTag);
        }
    }

    public interface IAsyncListener<T> {
        void onPreExecute();

        void onUpdateProgress(int progress);

        void onComplete(T object);

        T doWork();
    }

    public static class AsyncSimpleListener<T> implements IAsyncListener<T>{

        @Override
        public void onPreExecute() {

        }

        @Override
        public void onUpdateProgress(int progress) {

        }

        @Override
        public void onComplete(T o) {

        }

        @Override
        public T doWork() {
            return null;
        }
    }

    public static class MyTask<T> extends AsyncTask<Object, Integer, T> {

        private final WeakReference<IAsyncListener<T>> listener;
        private Object tag;

        public MyTask(IAsyncListener<T> listener) {
            this.listener = new WeakReference<>(listener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.get().onPreExecute();
        }

        @Override
        protected void onPostExecute(T o) {
            super.onPostExecute(o);
            listener.get().onComplete(o);
            cancelTask(tag);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            listener.get().onUpdateProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(T o) {
            super.onCancelled(o);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param objects The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected T doInBackground(Object... objects) {
            tag = objects[0];
            return listener.get().doWork();
        }
    }
}
