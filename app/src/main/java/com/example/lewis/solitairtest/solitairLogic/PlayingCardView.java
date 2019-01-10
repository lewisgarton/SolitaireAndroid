package com.example.lewis.solitairtest.solitairLogic;

import android.content.Context;
import android.view.*;
import android.widget.*;
import android.content.*;
import com.example.lewis.solitairtest.MainActivity;
import com.example.lewis.solitairtest.R;

public class PlayingCardView extends LinearLayout implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {
    public MainActivity mainActivity;
    public CardInfo cardInfo;
    public Context context;
    public boolean isTopCard = true;

    public PlayingCardView(Context context, CardInfo cardInfo, boolean isTopCard){
        super(context);
        this.isTopCard = isTopCard;
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        this.context = context;
        this.cardInfo = cardInfo;

        // Create the card image
        PlayingCardImageView i = new PlayingCardImageView(context, cardInfo.cardId, cardInfo.faceUp, isTopCard);
        this.addView(i);


        mainActivity = (MainActivity) getContext();
        setOnClickListener(this);
        setOnTouchListener(this);
        setOnDragListener(this);
    }


    @Override
    public void onClick(View v) {
        mainActivity.selectedCard = cardInfo;
        mainActivity.update();
        Toast.makeText(getContext(), "" + this.cardInfo.cardId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int touchX, touchY;


        // Disallow dragging of face down cards and placeholder cards
        if(!cardInfo.faceUp || cardInfo.cardId == -1) return false;
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            mainActivity.selectedCard = cardInfo;
            ClipData data = ClipData.newPlainText("", "");

            // Get the touch point in screen coords
            touchX = (int) event.getX();
            touchY = (int) event.getY();
            CardShadowBuilder cardShadow = new CardShadowBuilder(v, touchX, touchY);
            v.startDrag(data, cardShadow, v, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event){
        if(event.getAction() == DragEvent.ACTION_DROP){
            mainActivity.destinationCard = cardInfo;
            mainActivity.update();
        }
        return true;
    }
}