package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Space;

import com.example.spacegame.R;
import com.example.spacegame.SpaceGameView;

public class SpaceShip extends AngleMovingObject {
    float shipSpeed;

    float health;

    boolean movingState=false;
    double directionAngle=0;

    public SpaceShip(Context context, SpaceGameView spaceGameView, int screenX, int screenY) {
        super(context,spaceGameView, screenX, screenY);

        this.health = 100f;
        this.shipSpeed = 350;

        Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.playerbitmap);
        scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int)screenX/7, (int)screenX/7, false); //cross the left screen limit

        this.movingState=false;
        this.initialise(scaledBitmap, (double)screenX/2,(double)screenY/2);
        this.start();
    }

    public void setMovingState(boolean shouldMove)
    {
        this.movingState=shouldMove;
    }

    public float getX(){
        return this.rect.left;
    }

    public float getY() {
        return this.rect.top;
    }

    public void setX(float x)
    {
        this.setRectangle(x,this.getY());
    }

    public void setY(float y)
    {
        this.setRectangle(this.getX(),y);
    }
    public float getHealth()
    {
        return this.health;
    }

    public void takeDamage(float damage){
        this.health -= damage;
    }
    public void setHealth(float health){this.health = health;}
}
