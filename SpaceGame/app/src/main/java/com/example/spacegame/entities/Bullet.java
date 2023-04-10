package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.spacegame.R;
import com.example.spacegame.SpaceGameView;

import java.util.Random;

public class Bullet extends AngleMovingObject {

    public Bullet(Context context, SpaceGameView spaceGameView, int screenX, int screenY, double startX, double startY, double startAngle) {
        super(context,spaceGameView,screenX,screenY);
        Bitmap scaledBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int) (this.length * 0.15), (int) (this.height * 0.075), false); //cross the left screen limit
        initialise(scaledBitmap,startX,startY,startAngle,10);
        this.setStatus(true);
        this.start();
    }

    protected void movementDebug(){
        System.out.println("bullet movement"+ Math.random()*10);
    }

    public void checkCollisions(){
        if (this.rect.left<=0 || this.rect.left>=this.usableScreenX || this.rect.top<=0 || this.rect.top>=this.usableScreenY){
            this.setStatus(false);
            this.interrupt();
            this.spaceGameView.destroyUnregisterBullet(this);
        }
    }

}