package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.spacegame.entities.Bomb;
import com.example.spacegame.entities.Bullet;
import com.example.spacegame.entities.Healable;
import com.example.spacegame.entities.Projectile;
import com.example.spacegame.entities.SpaceShip;

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
    public static int screenX;
    public static int screenY;

    private int score = 0;

    private int lives = 3;

    private Bitmap bitmapback;

    static SpaceShip spaceShip;
    Bullet bullet;

    Bomb[] bombs;

    Healable[] healables;
    private static int bombsDetonated = 0;
    private static int healsConsumed = 0;

    private int currentWave = 1;
    private int waveEnemies;


    public SpaceGameView(Context context, int x, int y) {

        super(context);
        this.context = context;



        ourHolder = getHolder();
        paint = new Paint();
        screenX = x;
        screenY = y;

        initLevel();
    }

    public static void updateBombsDetonatedCount()
    {
        bombsDetonated++;
    }

    public static void updateHealsConsumedCount()
    {
        healsConsumed++;
    }


    private void initLevel()
    {
        spaceShip = new SpaceShip(context, screenX, screenY);
        bullet = new Bullet(context, spaceShip.getX(), spaceShip.getY(), 50, Projectile.ProjectileType.Bullet);

        bombs = new Bomb[3];
        healables = new Healable[3];

        waveEnemies = 3;

        for(int i = 0; i < 3; i++)
        {
            // Spawn Bombs
            int bombX = (int) ((Math.random() * (screenX)));
            int bombY = (int) ((Math.random() * (screenY)));
            bombs[i] = new Bomb(context, bombX, bombY, 100, Projectile.ProjectileType.Bomb);
            bombs[i].setActive();

            // Spawn Healables
            int healX = (int) ((Math.random() * (screenX)));
            int healY = (int) ((Math.random() * (screenY)));

            healables[i] = new Healable(context, healX, healY, 0, Projectile.ProjectileType.Heal);
            healables[i].setActive();
        }

        Toast wavePopUp = Toast.makeText(context, "Wave: "+Integer.toString(this.currentWave), Toast.LENGTH_SHORT);
        wavePopUp.show();
    }

    public static SpaceShip getPlayer()
    {
        return spaceShip;
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
        checkForCollision();

        // Check to see if player health is over 100, this will be true once a healable item has been consumed
        if(spaceShip.getHealth() > 100)
        {
            this.lives++;
            spaceShip.setHealth(100);
        }
        
        if(spaceShip.getHealth() <= 0 && this.lives > 0)
        {
            this.lives--;
            spaceShip.setHealth(100);
        }

        if(this.lives == 0)
        {
            endGame("Defeat");
        }

        if(this.currentWave == 8 && this.waveEnemies <= 0)
        {
            endGame("Victory");
        }

        for (Bomb bomb : bombs) {
            bomb.update(fps);
        }

        for(Healable healable: healables)
        {
            healable.update(fps);
        }

        if(bullet.getStatus())
        {
            bullet.update(fps);
        }
    }

    private void checkForCollision() {
        if (spaceShip.getX() > screenX - spaceShip.getLength()) {
            spaceShip.setX(screenX - spaceShip.getLength());
        }
        if (spaceShip.getX() < 0 + spaceShip.getLength()) {
            spaceShip.setX(0 + spaceShip.getLength());
        }

        if (spaceShip.getY() > screenY - spaceShip.getLength()) {
            spaceShip.setY(screenY - spaceShip.getLength());
        }
        if (spaceShip.getY() < 0 + spaceShip.getLength()) {
            spaceShip.setY(0 + spaceShip.getLength());
        }
    }

    private void endGame(String status)
    {
        /*
          @param Status - This determines whether the game ends as a victory or defeat, we want to pass this alongside the stats
          TODO: Using an Intent pass the game statistics to the End Game Screen, and end this activity while starting the EndScreenActivity.
            We want to pass this.score, bombsDetonated, healsConsumed and the parameter status
         */

        this.pause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                paused = false;
                if(motionEvent.getY() > this.screenY - this.screenY / 2f)
                {
                    if(motionEvent.getX() > this.screenX / 2f)
                    {
                        spaceShip.setMovingState(SpaceShip.movingState.RIGHT);
                    }else
                    {
                        spaceShip.setMovingState(SpaceShip.movingState.LEFT);

                    }
                }

                if(motionEvent.getY() < this.screenY - this.screenY / 2f)
                {
                    if(motionEvent.getX() < this.screenX / 2f)
                    {
                        spaceShip.setMovingState(SpaceShip.movingState.UP);
                    }else {
                        spaceShip.setMovingState(SpaceShip.movingState.DOWN);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                spaceShip.setMovingState(SpaceShip.movingState.STOPPED);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                bullet.fireBullet(fps, spaceShip.getMovingState());
                break;
            default:
                break;
        }
        return true;
    }

    private void draw(){

        if (ourHolder.getSurface().isValid()) {

            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Draw Background
            bitmapback = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
            bitmapback = Bitmap.createScaledBitmap(bitmapback, (int) (screenX), (int) (screenY),false);
            canvas.drawBitmap(bitmapback, 0, 0, paint);

            // Draw Player
            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY() , paint);

            // Draw Bullet
            if(bullet.getStatus())
            {
                paint.setColor(Color.argb(255, 255, 255, 0));
                canvas.drawBitmap(bullet.getBitmap(), bullet.getShootingX(), bullet.getShootingY() , paint);
            }

            // Draw Bombs
            for(Bomb bomb : bombs)
            {
                if(bomb.getStatus())
                {
                    paint.setColor(Color.argb(255, 255, 0, 0));
                    canvas.drawRect(bomb.getRect(), paint);
                }

            }

            // Draw Healables
            for(Healable healable : healables)
            {
                if(healable.getStatus())
                {
                    paint.setColor(Color.argb(255, 0, 255, 0));
                    canvas.drawRect(healable.getRect(), paint);
                }

            }


            paint.setColor(Color.argb(255,  240, 219, 31));
            paint.setTextSize(40);
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
