package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;

public class SpaceShip {
    RectF rect;

    float height;
    float length;

    float x;
    float y;

    int screenX;
    int screenY;

    float shipSpeed;
    int spaceShipMoving;

    float health = 100f;

    Bitmap bitmapUp;
    Bitmap bitmapLeft;
    Bitmap bitmapRight;
    Bitmap bitmapDown;
    Bitmap currentBitmap;

    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;
    final int UP = 3;
    final int DOWN = 4;

    public SpaceShip(Context context, int screenX, int screenY) {
        this.rect = new RectF();

        this.length = screenX / 10f;
        this.height = screenY / 10f;

        this.x = screenX / 2f;
        this.y = screenY / 2f;

        this.shipSpeed = 350;

        this.bitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        this.bitmapRight = Bitmap.createScaledBitmap(bitmapRight, (int) (length), (int) (height),false);

        this.bitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft);
        this.bitmapLeft = Bitmap.createScaledBitmap(bitmapLeft, (int) (length), (int) (height),false);

        this.bitmapDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipdown);
        this.bitmapDown = Bitmap.createScaledBitmap(bitmapDown, (int) (length), (int) (height),false);

        this.bitmapUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        this.bitmapUp = Bitmap.createScaledBitmap(bitmapUp, (int) (length), (int) (height),false);

        this.currentBitmap = this.bitmapRight;

        this.screenX = screenX;
        this.screenY = screenY;

    }

    public void setMovingState(int state)
    {
        this.spaceShipMoving = state;
    }

    public void update(long fps)
    {
        switch (this.spaceShipMoving)
        {
            case LEFT:
                this.x = this.x - this.shipSpeed / fps;
                this.currentBitmap = this.bitmapLeft;
                break;
            case RIGHT:
                this.x = this.x + this.shipSpeed / fps;
                this.currentBitmap = this.bitmapRight;
                break;
            case UP:
                this.y = this.y - this.shipSpeed / fps;
                this.currentBitmap = this.bitmapUp;
                break;
            case DOWN:
                this.y = this.y + this.shipSpeed / fps;
                this.currentBitmap = this.bitmapDown;
                break;
            default:
                this.currentBitmap = this.bitmapRight;
                break;
        }

        this.rect.top = this.y;
        this.rect.bottom = this.y + this.height;
        this.rect.left = this.x;
        this.rect.right = this.x + this.length;
    }

    public RectF getRect(){
        return this.rect;
    }

    public Bitmap getBitmap(){

        return this.currentBitmap;
    }

    public float getX(){
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getLength(){
        return this.length;
    }
}
