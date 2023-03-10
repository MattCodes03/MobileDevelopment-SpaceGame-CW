package com.example.spacegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Space;

import java.io.IOException;

public class SpaceGameView extends SurfaceView implements Runnable{

    private Context context;
    private Thread gameThread = null;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private boolean paused = true;
    private Canvas canvas;
    private Paint paint;
    private long fps;

    private long timeThisFrame;
    private int screenX;
    private int screenY;

    private int score = 0;

    private int lives = 3;

    SpaceShip spaceShip;

    GameManager gameManager;

    public SpaceGameView(Context context, int x, int y) {

        super(context);
        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();
        screenX = x;
        screenY = y;

        gameManager = new GameManager();

        initLevel();
    }


    private void initLevel()
    {
        spaceShip = new SpaceShip(context, screenX, screenY);
    }

    @Override
    public void run() {
        while (playing) {
            long startTime = System.currentTimeMillis();

            if(!paused){
                update();
            }
            draw();

            long timeThis = System.currentTimeMillis() - startTime;
            if (timeThis >= 1) {
                fps = 1000 / timeThis;
            }

        }

    }

    private void update()
    {
        spaceShip.update(fps);
        playerMovement();
    }

    @SuppressLint("ClickableViewAccessibility") // Will fix this warning
    private void playerMovement()
    {
        this.setOnTouchListener((view, motionEvent) -> {
            switch(motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    spaceShip.setMovingState(spaceShip.UP);
                    break;
                case MotionEvent.ACTION_UP:
                    spaceShip.setMovingState(spaceShip.STOPPED);
                    break;
                default:
                    break;
            }

            return false;
        });
    }

    private void draw(){

        if (ourHolder.getSurface().isValid()) {

            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), screenY / 2f, paint);

            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(60);
            canvas.drawText("Score: " + score + "   Lives: " +
                    lives, 10,50, paint);

            ourHolder.unlockCanvasAndPost(canvas);
            paused = false;
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }



}  // end class
