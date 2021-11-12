package com.ldp.base_lib.baseui;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: Activity 模拟任务栈
 */
public class ActivityStack {

    private final Stack<Activity> stack;

    public ActivityStack() {
        stack = new Stack<>();
    }

    public final void addActivity(Activity activity) {
        stack.push(activity);
    }

    public final void removeActivity(Activity activity) {
        if (stack.size() > 0)
            stack.pop();
    }

    public final void finishAllActivity() {
        try {
            while (stack.size() > 0) {
                Activity activity = stack.pop();
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
