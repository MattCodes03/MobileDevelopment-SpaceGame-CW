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
//    RectF rect;

    float height;
    float length;

    float shipSpeed;

    float health;

    boolean movingState=false;
    double directionAngle=0;

    public SpaceShip(Context context, SpaceGameView spaceGameView, int screenX, int screenY) {
        super(context,spaceGameView, screenX, screenY);

        this.health = 100f;
        this.shipSpeed = 350;

        Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.playerbitmap);
        this.length=screenX/3f;
        this.height=screenX/3f;
        scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int)this.length, (int)this.height, false); //cross the left screen limit
//        this.bitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
//        this.bitmapRight = Bitmap.createScaledBitmap(bitmapRight, (int) (length), (int) (height),false);
//
//        this.bitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft);
//        this.bitmapLeft = Bitmap.createScaledBitmap(bitmapLeft, (int) (length), (int) (height),false);
//
//        this.bitmapDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipdown);
//        this.bitmapDown = Bitmap.createScaledBitmap(bitmapDown, (int) (length), (int) (height),false);
//
//        this.bitmapUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
//        this.bitmapUp = Bitmap.createScaledBitmap(bitmapUp, (int) (length), (int) (height),false);

//        this.currentBitmap = this.bitmapRight;

//        this.screenX = screenX;
//        this.screenY = screenY;

//        this.spaceShipMoving = movingState.STOPPED;
        this.movingState=false;
        initialise(scaledBitmap);
        this.start();
    }

//    public void setMovingState(movingState state)
//    {
//        this.spaceShipMoving = state;
//    }
    public void setMovingState(boolean shouldMove)
    {
//        this.spaceShipMoving = state;
        this.movingState=shouldMove;
    }

    public void update(long fps)
    {
//        switch (this.movingState) // spaceShipMoving)
//        {
//            case LEFT:
//                this.x = this.x - this.shipSpeed / fps;
//                this.currentBitmap = this.bitmapLeft;
//                break;
//            case RIGHT:
//                this.x = this.x + this.shipSpeed / fps;
//                this.currentBitmap = this.bitmapRight;
//                break;
//            case UP:
//                this.y = this.y - this.shipSpeed / fps;
//                this.currentBitmap = this.bitmapUp;
//                break;
//            case DOWN:
//                this.y = this.y + this.shipSpeed / fps;
//                this.currentBitmap = this.bitmapDown;
//                break;
//            default:
//                this.currentBitmap = this.bitmapRight;
//                break;
//        }

//        this.rect.top = this.y;
//        this.rect.bottom = this.y + this.height;
//        this.rect.left = this.x;
//        this.rect.right = this.x + this.length;


    }

//    public RectF getRect(){
//        return this.rect;
//    }

//    public Bitmap getBitmap(){
//        return this.bitmap;
//    }

//    public float getX(){
//        return this.x;
//    }
    public float getX(){
        return this.rect.left;
    }

//    public float getY() {
//        return this.y;
//    }
    public float getY() {
        return this.rect.top;
    }

    public void setX(float x)
    {
        this.setRectangle(x,this.getY());
//        this.x = x;
    }

    public void setY(float y)
    {
        this.setRectangle(this.getX(),y);
//        this.y = y;
    }

    public float getLength(){
        return this.rect.width(); //length;
    }

    public float getHeight()
    {
        return this.rect.height(); //height;
    }

    public float getHealth()
    {
        return this.health;
    }

    public void takeDamage(float damage){
        this.health -= damage;
    }
    public void setHealth(float health){this.health = health;}

//    public movingState getMovingState(){return this.spaceShipMoving;}
}
