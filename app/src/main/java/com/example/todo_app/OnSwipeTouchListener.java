package com.example.todo_app;

import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    private ListView listView;

    public OnSwipeTouchListener() {
        super();
    }

    public OnSwipeTouchListener(ListView listView) {
        super();

        this.listView = listView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            startX = event.getX();
            startY = event.getY();

        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            endX = event.getX();
            endY = event.getY();
            System.out.println(startX + " " + startY + " " + endX + " " + endY);
            ArrayList<Integer> listParams = getListViewHeight();
            int listHeight = listParams.get(0);
            int itemHeight = listParams.get(1);
            int itemId = (int) Math.floor(endY/itemHeight);

            ArrayAdapter arrayAdapter = (ArrayAdapter) listView.getAdapter();
            TextView textView = (TextView) arrayAdapter.getView(itemId, null, listView);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        return true;
    }



    private ArrayList<Integer> getListViewHeight(){
        ListAdapter mAdapter = listView.getAdapter();

        int totalHeight = 0;
        int itemHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            itemHeight = mView.getMeasuredHeight();
        }

        int finalHeight = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        System.out.println("HEight " + finalHeight);


        return new ArrayList<Integer>(Arrays.asList(totalHeight, itemHeight));
    }
}