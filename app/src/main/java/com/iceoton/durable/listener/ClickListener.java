package com.iceoton.durable.listener;

import android.view.View;

/**
 * เป็น interface สำหรับไว้ implement การ click
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
