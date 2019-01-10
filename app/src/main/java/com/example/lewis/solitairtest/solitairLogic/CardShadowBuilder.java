package com.example.lewis.solitairtest.solitairLogic;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class CardShadowBuilder extends View.DragShadowBuilder {
    int touchX, touchY;

    public CardShadowBuilder(View view, int touchX, int touchY){
        super(view);
        this.touchX = touchX;
        this.touchY = touchY;
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point touchPoint){
        // Set the shadow size equal to the view
        shadowSize.set(getView().getWidth(), getView().getHeight());

        // Set the touch point in screen coords
        touchPoint.set(touchX, touchY);
    }
}
