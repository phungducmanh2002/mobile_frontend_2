package com.example.appktx2.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class LayoutUtils {
    public static List<LinearLayout> GetAllChilds(ViewGroup viewGroup) {
        List<LinearLayout> linearLayouts = new ArrayList<>();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof LinearLayout) {
                linearLayouts.add((LinearLayout) child);
            } else if (child instanceof ViewGroup) {
                // Nếu là một ViewGroup khác, tiếp tục tìm kiếm trong đó
                linearLayouts.addAll(GetAllChilds((ViewGroup) child));
            }
        }
        return linearLayouts;
    }
}
