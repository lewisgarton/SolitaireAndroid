package com.example.lewis.solitairtest.solitairLogic;

import android.content.Context;
import android.view.*;
import android.widget.*;
import android.content.*;
import com.example.lewis.solitairtest.MainActivity;
import com.example.lewis.solitairtest.R;

/**
 * PlayingCardView is a extension of linear layout for holding a cardImageView.
 * This view allows for cards to be stacked and moved as a unit by encapsulating them in the parent
 * cards view.
 * This class also responds to users clicks and drags by implementation of OnClickListner, OnTouchListner
 * and OnDragListner.
 */
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

    /**
     * onClick registers the clicked view with the game and issues an update a game update request.
     * @param v
     */
    @Override
    public void onClick(View v) {
        mainActivity.selectedCard = cardInfo;
        mainActivity.update();
        Toast.makeText(getContext(), "" + this.cardInfo.cardId, Toast.LENGTH_SHORT).show();
    }

    /**
     * onTouch registers the card that is being dragged with the game, it also creates the dragShadow.
     * @param v
     * @param event
     * @return
     */
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

    /**
     * onDrag registers the view that the dragged card was dropped on with the game and issues an update request.
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onDrag(View v, DragEvent event){
        if(event.getAction() == DragEvent.ACTION_DROP){
            mainActivity.destinationCard = cardInfo;
            mainActivity.update();
        }
        return true;
    }
}