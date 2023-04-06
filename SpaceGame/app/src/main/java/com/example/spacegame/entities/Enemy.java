package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.spacegame.R;
import com.example.spacegame.SpaceGameView;

public class Enemy extends AngleMovingObject {
    boolean status = false;//true-visible and active on screen false-not

    float health;
    public Enemy(Context context, SpaceGameView spaceGameView, int screenX, int screenY) {
        super(context,spaceGameView,screenX,screenY);
        Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int) (this.length * 0.6), (int) (this.height * 0.6), false); //cross the left screen limit
        initialise(scaledBitmap);
        this.setStatus(true);
        this.start();

        this.health = 100;
    }

    public void takeDamage(float damage)
    {
        this.health -= damage;
    }

    public float getHealth()
    {
        return this.health;
    }
}
