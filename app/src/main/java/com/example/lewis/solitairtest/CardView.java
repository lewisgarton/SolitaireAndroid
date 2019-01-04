package com.example.lewis.solitairtest;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lewis.solitairtest.solitairLogic.CardLocation;

public class CardView extends TextView implements View.OnClickListener {
    public MainActivity mainActivity;
    public int cardId;
    public CardLocation location;

    public CardView(Context context, String text, int cardId, int col, int row, int type) {
        super(context);
        location = new CardLocation(cardId, col, row, type);
        this.cardId = cardId;

        setTextSize(10);
        setText(text);
        mainActivity = (MainActivity) getContext();
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mainActivity.selectedCard = location;
        mainActivity.update();
        Toast.makeText(getContext(), "" + this.cardId, Toast.LENGTH_SHORT).show();
    }
}
