package com.example.lewis.solitairtest.solitairLogic;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lewis.solitairtest.R;

/**
 * PlayingCardImageView is an extension of ImageView, it encapsulates the required initialization
 * for each card.
 */
public class PlayingCardImageView extends ImageView {
    int imageId;

    /**
     * Constructor sets card image and layout parameters.
     * @param context
     * @param cardId
     * @param faceUp
     * @param isTopCard
     */
    public PlayingCardImageView(Context context, int cardId, boolean faceUp, boolean isTopCard){
        super(context);
        // Figure out the resource id
        imageId = setCardImageId(cardId, faceUp);
        setImageResource(imageId);
        //Set layout params
        //TODO fix this!
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250);

        // Don't set a margin if the card is a top card
        if(isTopCard) lp.setMargins(0, 0, 0, 0);
        else lp.setMargins(0, 0, 0, -150);

        setLayoutParams(lp);
    }

    /**
     * setCardImage is a temporary class for resolving cardIds to image resource ids.
     * This is to be fixed in the future.
     * @param cardId
     * @param faceUp
     * @return
     */
    public int setCardImageId(int cardId, boolean faceUp) {

        int id = 0;
        if (!faceUp) id = R.drawable.unknown;
        else if (cardId == 101) id = R.drawable.clubs_a;
        else if (cardId == 102) id = R.drawable.clubs_2;
        else if (cardId == 103) id = R.drawable.clubs_3;
        else if (cardId == 104) id = R.drawable.clubs_4;
        else if (cardId == 105) id = R.drawable.clubs_5;
        else if (cardId == 106) id = R.drawable.clubs_6;
        else if (cardId == 107) id = R.drawable.clubs_7;
        else if (cardId == 108) id = R.drawable.clubs_8;
        else if (cardId == 109) id = R.drawable.clubs_9;
        else if (cardId == 110) id = R.drawable.clubs_10;
        else if (cardId == 111) id = R.drawable.clubs_j;
        else if (cardId == 112) id = R.drawable.clubs_q;
        else if (cardId == 113) id = R.drawable.clubs_k;
        else if (cardId == 201) id = R.drawable.spades_a;
        else if (cardId == 202) id = R.drawable.spades_2;
        else if (cardId == 203) id = R.drawable.spades_3;
        else if (cardId == 204) id = R.drawable.spades_4;
        else if (cardId == 205) id = R.drawable.spades_5;
        else if (cardId == 206) id = R.drawable.spades_6;
        else if (cardId == 207) id = R.drawable.spades_7;
        else if (cardId == 208) id = R.drawable.spades_8;
        else if (cardId == 209) id = R.drawable.spades_9;
        else if (cardId == 210) id = R.drawable.spades_10;
        else if (cardId == 211) id = R.drawable.spades_j;
        else if (cardId == 212) id = R.drawable.spades_q;
        else if (cardId == 213) id = R.drawable.spades_k;
        else if (cardId == 301) id = R.drawable.diamonds_a;
        else if (cardId == 302) id = R.drawable.diamonds_2;
        else if (cardId == 303) id = R.drawable.diamonds_3;
        else if (cardId == 304) id = R.drawable.diamonds_4;
        else if (cardId == 305) id = R.drawable.diamonds_5;
        else if (cardId == 306) id = R.drawable.diamonds_6;
        else if (cardId == 307) id = R.drawable.diamonds_7;
        else if (cardId == 308) id = R.drawable.diamonds_8;
        else if (cardId == 309) id = R.drawable.diamonds_9;
        else if (cardId == 310) id = R.drawable.diamonds_10;
        else if (cardId == 311) id = R.drawable.diamonds_j;
        else if (cardId == 312) id = R.drawable.diamonds_q;
        else if (cardId == 313) id = R.drawable.diamonds_k;
        else if (cardId == 401) id = R.drawable.hearts_a;
        else if (cardId == 402) id = R.drawable.hearts_2;
        else if (cardId == 403) id = R.drawable.hearts_3;
        else if (cardId == 404) id = R.drawable.hearts_4;
        else if (cardId == 405) id = R.drawable.hearts_5;
        else if (cardId == 406) id = R.drawable.hearts_6;
        else if (cardId == 407) id = R.drawable.hearts_7;
        else if (cardId == 408) id = R.drawable.hearts_8;
        else if (cardId == 409) id = R.drawable.hearts_9;
        else if (cardId == 410) id = R.drawable.hearts_10;
        else if (cardId == 411) id = R.drawable.hearts_j;
        else if (cardId == 412) id = R.drawable.hearts_q;
        else if (cardId == 413) id = R.drawable.hearts_k;
        else if (cardId == -1) id = R.drawable.empty_stack;
        return id;
    }
}
