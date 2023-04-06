package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import com.example.spacegame.R;
import com.example.spacegame.SpaceGameView;

public class Enemy extends AngleMovingObject {
    boolean status = false; //true-visible and active on screen false-not

    public Enemy(Context context, SpaceGameView spaceGameView, int screenX, int screenY) {
        super(context,spaceGameView,screenX,screenY);
        Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int) (this.length*0.4), (int) (this.height*0.4), false); //cross the left screen limit
        initialise(scaledBitmap);
        this.setStatus(true);
        this.start();
    }

}
