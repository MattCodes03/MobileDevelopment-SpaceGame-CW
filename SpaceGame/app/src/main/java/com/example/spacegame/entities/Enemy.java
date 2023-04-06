package com.example.spacegame.entities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import com.example.spacegame.R;
import com.example.spacegame.SpaceGameView;

public class Enemy extends Thread{
    int counter = 0;
    Bitmap bitmap;
    Bitmap scaledBitmap;

    float length;
    float height;
    boolean status = false; //true-visible and active on screen false-not
    RectF rect=new RectF();
    SpaceGameView spaceGameView;
    double directionAngle;
    double stepHorizontal;
    double stepVertical;
    double screenX;
    double screenY;
    double usableScreenX;
    double usableScreenY;
    double navigationBarHeight=0;

    public Enemy(Context context, SpaceGameView spaceGameView, int screenX, int screenY) {
        this.spaceGameView=spaceGameView;
        this.length = screenX / 10f;
        this.height = screenY / 10f;
        this.screenX=screenX;
        this.screenY=screenY;

        this.scaledBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        this.scaledBitmap = Bitmap.createScaledBitmap(this.scaledBitmap, (int) (length*0.4), (int) (height*0.4), false); //cross the left screen limit

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            this.navigationBarHeight=resources.getDimensionPixelSize(resourceId);
        }

        this.usableScreenX=this.screenX-this.scaledBitmap.getWidth();
        this.usableScreenY=this.screenY-this.scaledBitmap.getHeight()-this.navigationBarHeight;

        // choose free and sage starting location for the object
        do{
            double x = Math.random()*screenX;
            double y = Math.random()*screenY;

            this.directionAngle=Math.random()*360;

            this.rect.left = (int) x;
            this.rect.top = (int) y;
            this.rect.right = (int) (x + this.scaledBitmap.getWidth());
            this.rect.bottom = (int) (y + this.scaledBitmap.getHeight());

        } while (!this.spaceGameView.checkIsOnFreeSurface(this.rect));
        this.stepHorizontal=screenX/100f;
        this.stepVertical=screenY/100f;

        this.checkDirectionAngle();

        this.start();
        Log.d("enemy", "after thread...");
    }

    public void run() {
        while(!interrupted()){ // if interrupted - end of the thread
            this.counter++;
//            Log.d("enemy", "class enemy started from thread - " + this.counter);

            this.rect=generateMovement(this.directionAngle,this.rect,this.stepHorizontal,this.stepVertical);
            this.checkCollisions();

            try {
                Thread.sleep(50); //Thread.sleep(this.spaceGameView.getFps());
            } catch (InterruptedException e) {
                return; // end of thread
            }
        }
    }

    private void checkDirectionAngle(){
        this.directionAngle=this.checkAngle(this.directionAngle);
        Matrix matrix = new Matrix();
        matrix.postRotate((float)this.directionAngle);
        this.bitmap = Bitmap.createBitmap(this.scaledBitmap, 0, 0, this.scaledBitmap.getWidth(), this.scaledBitmap.getHeight(), matrix, true);
    }

    private double checkAngle(double angle){
        if (angle<0){
            angle+=360;
        }
        else if (angle>=360){
            angle=angle%360;
        }
        return angle;
    }

    public void checkCollisions(){
        if (this.rect.left<=0 || this.rect.left>=this.usableScreenX || this.rect.top<=0 || this.rect.top>=this.usableScreenY){
            double old=this.directionAngle;
            double oppositeDirection=this.checkAngle(this.directionAngle+180);
            this.directionAngle=oppositeDirection+(Math.random()*90-45);
//            long direction=Math.round(this.directionAngle);
//            if (direction>=90 && direction<=270){ //cross the left screen limit
//                this.directionAngle=Math.random()*180-90;
//            }
//            else if (direction>=0 && direction<=180) { //cross the top screen limit
//                this.directionAngle=Math.random()*180f+180;
//            }
//            else { //cross the right screen limit
//                this.directionAngle=Math.random()*180f+90;
//            }
            this.checkDirectionAngle();
            Log.d("enemy", "checkCollisions "+old+" -> "+this.directionAngle);
        }
    }

    public RectF generateMovement(double angleDirection, RectF currentRect, double stepHorizontal, double stepVertical){
        RectF destinationRect=new RectF();
        destinationRect.top=currentRect.top;
        destinationRect.left=currentRect.left;
        destinationRect.bottom=currentRect.bottom;
        destinationRect.right=currentRect.right;

        double co=(angleDirection%90)/90;
        if (angleDirection>=0 && angleDirection<90){
            double xmove=(1-co)*stepHorizontal;
            double ymove=co*stepVertical;
            destinationRect.left+=xmove;
            destinationRect.right+=xmove;
            destinationRect.top+=ymove;
            destinationRect.bottom+=ymove;
        }
        else if (angleDirection>=90 && angleDirection<180){
            double xmove=co*stepHorizontal;
            double ymove=(1-co)*stepVertical;
            destinationRect.left-=xmove;
            destinationRect.right-=xmove;
            destinationRect.top+=ymove;
            destinationRect.bottom+=ymove;
        }
        else if (angleDirection>=180 && angleDirection<270){
            double xmove=(1-co)*stepHorizontal;
            double ymove=co*stepVertical;
            destinationRect.left-=xmove;
            destinationRect.right-=xmove;
            destinationRect.top-=ymove;
            destinationRect.bottom-=ymove;
        }
        else { // angleDirection>=270 && angleDirection<360
            double xmove=co*stepHorizontal;
            double ymove=(1-co)*stepVertical;
            destinationRect.left+=xmove;
            destinationRect.right+=xmove;
            destinationRect.top-=ymove;
            destinationRect.bottom-=ymove;
        }
        return destinationRect;
    }
    public RectF getRect(){
        return this.rect;
    }

    public boolean getStatus(){
        return this.status;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setStatus(boolean activate) {
        this.status=activate;
        if (!this.status){
            this.interrupt();
        }
    }
}