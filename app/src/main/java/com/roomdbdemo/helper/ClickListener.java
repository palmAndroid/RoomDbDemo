package com.roomdbdemo.helper;

import android.view.View;

/**
 * Created by ankita.shukla on 1/19/2017.
 */
public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);

}
