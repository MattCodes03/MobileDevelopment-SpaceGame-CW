package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.spacegame.entities.Bomb;
import com.example.spacegame.entities.Bullet;
import com.example.spacegame.entities.Enemy;
import com.example.spacegame.entities.Ally;
import com.example.spacegame.entities.Healable;
import com.example.spacegame.entities.Projectile;
import com.example.spacegame.entities.SpaceShip;

import java.util.logging.Handler;

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

    PointF touchPoint;
    PointF playerPoint;

    Healable[] healables;
    private static int bombsDetonated = 0;
    private static int healsConsumed = 0;

    private int currentWave = 0;
    private int waveEnemies;
    private int waveAllies;

    // holds current active enemies object on screen
    private static Enemy[] currentEnemies;
    private static Ally[] currentAllies;


    public SpaceGameView(Context context, int x, int y) {
        super(context);
        this.context=context;
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

    public static Enemy[] getEnemies()
    {
        return currentEnemies;
    }
    public static Ally[] getAllies()
    {
        return currentAllies;
    }

    public boolean checkIsOnFreeSurface(RectF rect) {
        // TODO: return true if the rect is in free of the other objects area on screen and is surrounded by some additional free area
        return true;
    }

    private void initLevel()
    {
        spaceShip = new SpaceShip(context, this, screenX, screenY);
        bullet = new Bullet(context, spaceShip.getX(), spaceShip.getY(), 100, Projectile.ProjectileType.Bullet);
        startWave();
    }

    private void startWave()
    {
        spawnHealsAndBombs();
        generateNewEnemiesWave();
        generateNewAlliesWave();
    }

    private void spawnHealsAndBombs()
    {
        bombs = new Bomb[3];
        healables = new Healable[3];

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
    }

    private void generateNewEnemiesWave()
    {
        currentEnemies = new Enemy[3];
        this.waveEnemies=3;
        this.currentWave+=1;

        for(int i=0; i<3; i++){
            currentEnemies[i] = new Enemy(context,this,screenX,screenY);
        }
        for(Enemy enemy:currentEnemies){
            enemy.setStatus(true);
        }
    }

    private void generateNewAlliesWave()
    {
        currentAllies = new Ally[3];
        this.waveAllies=3;
        //this.currentWave = 1;

        for(int i=0; i<3; i++){
            currentAllies[i] = new Ally(context,this,screenX,screenY);
        }
        for(Ally Ally:currentAllies){
            Ally.setStatus(true);
        }
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

    public long getFps() {
        return fps;
    }

    private void update()
    {

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

        for(Enemy enemy : currentEnemies)
        {
            if(enemy.getHealth() <= 0 && enemy.getStatus())
            {
                enemy.setStatus(false);
                this.waveEnemies--;
                this.score+=10;
            }
        }

        if(this.currentWave == 8 && this.waveEnemies <= 0)
        {
            endGame("Victory");
        }

        if (this.waveEnemies==0 && this.currentWave < 8){
            startWave();
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

    private void endGame(String status)
    {
        /*
          @param Status - This determines whether the game ends as a victory or defeat, we want to pass this alongside the stats
          TODO: Using an Intent pass the game statistics to the End Game Screen, and end this activity while starting the EndScreenActivity.
            We want to pass this.score, bombsDetonated, healsConsumed and the parameter status
         */

        this.pause();
    }

    public double getAngle(PointF p1, PointF p2) {
        final double deltaY = (p2.y - p1.y);
        final double deltaX = (p2.x - p1.x);

        double result = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        result = spaceShip.checkAngle(result+180);

        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_MOVE:
                touchPoint = new PointF();
                touchPoint.x = motionEvent.getX();
                touchPoint.y = motionEvent.getY();

                playerPoint = new PointF();
                playerPoint.x = spaceShip.getRect().left;
                playerPoint.y = spaceShip.getRect().top;

                double newDirectionAngle = getAngle(touchPoint, playerPoint);
                spaceShip.setDirectionAngle(newDirectionAngle);
                spaceShip.setStatus(true);
                spaceShip.checkCollisions();

                Log.d("Angle", ""+newDirectionAngle);

                break;
            case MotionEvent.ACTION_UP:
                spaceShip.setStatus(false);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                bullet.fireBullet(fps, getAngle(touchPoint, playerPoint));
                break;
            default:
                break;
        }
        return true;
    }

    private void draw() {


        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26, 128, 182));
            // Draw Background
            bitmapback = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
            bitmapback = Bitmap.createScaledBitmap(bitmapback, (int) (screenX), (int) (screenY), false);
            canvas.drawBitmap(bitmapback, 0, 0, paint);

            // Draw Player
            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY(), paint);
            paint.setColor(Color.argb(150, 255, 255, 255));
            //canvas.drawRect(spaceShip.getCollisionRect(), paint);

            // Draw Bullet
            if (bullet.getStatus()) {
                paint.setColor(Color.argb(255, 255, 255, 0));
                canvas.drawBitmap(bullet.getBitmap(), bullet.getShootingX(), bullet.getShootingY(), paint);
            }

            // Draw Bombs
            for (Bomb bomb : bombs) {
                if (bomb.getStatus()) {
                    paint.setColor(Color.argb(255, 255, 0, 0));
                    canvas.drawRect(bomb.getRect(), paint);
                }

            }

            // Draw Healables
            for (Healable healable : healables) {
                if (healable.getStatus()) {
                    paint.setColor(Color.argb(255, 0, 255, 0));
                    canvas.drawRect(healable.getRect(), paint);
                }

            }

            // draw enemies
            for(Enemy enemy: currentEnemies )
            {
                if (enemy!=null && enemy.getStatus())
                {
                    canvas.drawBitmap(enemy.getBitmap(),enemy.getRect().left,enemy.getRect().top,this.paint);
                }
            }

            for(Ally ally: currentAllies )
            {
                if (ally!=null && ally.getStatus())
                {
                    canvas.drawBitmap(ally.getBitmap(),ally.getRect().left,ally.getRect().top,this.paint);
                }
            }

            paint.setColor(Color.argb(255, 240, 219, 31));
            paint.setTextSize(50);
            canvas.drawText("Score: " + score + "   Lives: " + lives + "    Wave: "+ currentWave, 10, 50, paint);

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
