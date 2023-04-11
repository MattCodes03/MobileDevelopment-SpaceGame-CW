package com.example.spacegame;

import static java.lang.System.exit;

import android.content.Context;
import android.content.Intent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.spacegame.entities.AngleMovingObject;
import com.example.spacegame.entities.Bomb;
import com.example.spacegame.entities.Bullet;
import com.example.spacegame.entities.Enemy;
import com.example.spacegame.entities.Ally;
import com.example.spacegame.entities.Healable;
import com.example.spacegame.entities.Projectile;
import com.example.spacegame.entities.SpaceShip;
import com.example.spacegame.entities.ScreenObjTypeEnum;

import java.util.ArrayList;

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

    Bomb[] bombs;

    PointF touchPoint;
    PointF playerPoint;

    Healable[] healables;
    private static int bombsDetonated = 0;
    private static int healsConsumed = 0;

    private int currentWave = 0;
    private int waveEnemies;

    boolean allySpawned;

    // holds current active enemies object on screen
    //private static Enemy[] currentEnemies;
    private ArrayList<Enemy> currentEnemies=new ArrayList<>();

//    private static Ally[] currentAllies;
    private ArrayList<Ally> currentAllies=new ArrayList<>();

    private static long downTime;

    private ArrayList<Bullet> bulletArrayList=new ArrayList<>();

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

    public ArrayList<Enemy> getEnemies()
    {
        return this.currentEnemies;
    }
    public ArrayList<Ally> getAllies()
    {
        return this.currentAllies;
    }

    public boolean checkIsOnFreeSurface(RectF rect) {
        // TODO: return true if the rect is in free of the other objects area on screen and is surrounded by some additional free area
        return true;
    }

    private void initLevel()
    {
        spaceShip = new SpaceShip(context, this, screenX, screenY);
        allySpawned = false;
        this.score = 0;
        startWave();
    }

    private void startWave()
    {
        if(this.currentWave == 8)
        {
            endGame("Victory");
        }else {
            spawnHealsAndBombs();
            generateNewEnemiesWave();
        }
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
        if (this.currentEnemies.isEmpty()){
            this.waveEnemies=3;
            this.currentWave+=1;

            for(int i=0; i<3; i++){
                this.currentEnemies.add(new Enemy(context,this,screenX,screenY));
            }
            for(Enemy enemy:currentEnemies){
                enemy.setStatus(true);
            }
        }
    }

    private void generateNewAlliesWave()
    {
        if (this.currentAllies.isEmpty()){

            for(int i=0; i<1; i++){
                currentAllies.add(new Ally(context,this,screenX,screenY));
            }
            for(Ally Ally:currentAllies){
                Ally.setStatus(true);
            }
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

        synchronized (currentEnemies){
            for(Enemy enemy : currentEnemies)
            {
                if(enemy.getHealth() <= 0 && enemy.getStatus())
                {
                    enemy.setStatus(false);
                }
            }
        }

        for (Bomb bomb : bombs) {
            bomb.update(fps);
        }

        for(Healable healable: healables)
        {
            healable.update(fps);
        }

        // Spawn Player Ally once points reach 50
        if(this.score == 50 && !this.allySpawned)
        {
            generateNewAlliesWave();
        }

        if(this.score == 50 && !allySpawned)
        {
            allySpawned = true;
            generateNewAlliesWave();
        }
    }

    private void endGame(String status)
    {
        Intent intent = new Intent(context, EndScreenActivity.class);

        intent.putExtra("Status", status);
        intent.putExtra("Score", Integer.toString(this.score));
        intent.putExtra("Bombs", Integer.toString(bombsDetonated));
        intent.putExtra("Heals", Integer.toString(healsConsumed));

        context.startActivity(intent);
        exit(0);
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
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
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
                if (System.currentTimeMillis()-downTime<100){
                    this.fireMainShipBullet();
//                    bullet.fireBullet(fps, getAngle(touchPoint, playerPoint));
                }
                spaceShip.setStatus(false);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                this.fireMainShipBullet();
//                bullet.fireBullet(fps, getAngle(touchPoint, playerPoint));
                break;
            default:
                break;
        }
        return true;
    }

    private void fireMainShipBullet(){
        Bullet bullet=new Bullet(this.context,this,screenX,screenY,spaceShip.getX()+spaceShip.getRect().width()/2,spaceShip.getY()+spaceShip.getRect().height()/2,spaceShip.getDirectionAngle(),ScreenObjTypeEnum.MainShip);
        this.fireBullet(bullet);
    }

    public void fireBullet(Bullet bullet){
        this.bulletArrayList.add(bullet);
    }

    public void destroyUnregisterBullet(AngleMovingObject bullet){

        this.bulletArrayList.remove(bullet);
    }

    private boolean positionMatch(RectF rect1, RectF rect2){
        boolean ret=false;
        if ( (rect1.top>=rect2.top && rect1.top<= rect2.bottom && rect1.left>=rect2.left && rect1.left<=rect2.right)
            || (rect1.top>=rect2.top && rect1.top<=rect2.bottom && rect1.right>=rect2.left && rect1.left<=rect2.right)
                || (rect1.bottom>=rect2.top && rect1.bottom<=rect2.bottom && rect1.left>=rect2.left && rect1.left<=rect2.right)
                || (rect1.bottom>=rect2.top && rect1.bottom<=rect2.bottom && rect1.right>=rect2.left && rect1.right<=rect2.right)
        )
        {
            ret=true;
        }
        return ret;
    }

    public boolean checkBulletCollision(Bullet bullet){
        boolean ret=false;
        RectF rect=bullet.getRect();
        ScreenObjTypeEnum sourceType=bullet.getSourceType();

        // Enemy Bullet Collisions
        synchronized (this.currentEnemies){
            Enemy tmpEnemy=null;
            if (sourceType!=ScreenObjTypeEnum.Enemy) for(Enemy enemy: this.currentEnemies )
            {
                if (enemy!=null && enemy.getStatus())
                {
                    if (this.positionMatch(rect,enemy.getRect())){
                        ret=true;
                        System.out.println("enemy stroke");
                        tmpEnemy=enemy;
                    }
                }
            }
            if (tmpEnemy!=null) {
                System.out.println("remove enemy");
                this.currentEnemies.remove(tmpEnemy);
                System.out.println("kill enemy");
                tmpEnemy.kill();
                this.waveEnemies--;
                this.score+=10;
                System.out.println("check enemy isEmpty");
                if (this.currentEnemies.isEmpty()){ //this.currentWave==0
                    System.out.println("generate new enemies wave");
                    startWave();
                }
            }
        }

        // Ally Bullet Collisions
        synchronized (currentAllies){
            Ally tmpAlly=null;
            if (sourceType!=ScreenObjTypeEnum.Ally && sourceType != ScreenObjTypeEnum.MainShip) for(Ally ally: currentAllies )
            {
                if (ally!=null && ally.getStatus())
                {
                    if (this.positionMatch(rect,ally.getRect())){
                        ret=true;
                        System.out.println("ally stroke");
                        tmpAlly=ally;
                    }
                }
            }
            if (tmpAlly!=null) {
                this.currentAllies.remove(tmpAlly);
                tmpAlly.kill();
            }
        }

        // Player Bullet Collisions
        if (sourceType!=ScreenObjTypeEnum.MainShip && sourceType!=ScreenObjTypeEnum.Ally && this.positionMatch(rect,spaceShip.getRect())){
            ret=true;
            System.out.println("main ship stroke");
            spaceShip.takeDamage(100);
        }

        return ret;
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
            synchronized (currentEnemies){
                for(Enemy enemy: currentEnemies )
                {
                    if (enemy!=null && enemy.getStatus())
                    {
                        canvas.drawBitmap(enemy.getBitmap(),enemy.getRect().left,enemy.getRect().top,this.paint);
                    }
                }
            }

            synchronized (currentAllies){
                for(Ally ally: currentAllies )
                {
                    if (ally!=null && ally.getStatus())
                    {
                        canvas.drawBitmap(ally.getBitmap(),ally.getRect().left,ally.getRect().top,this.paint);
                    }
                }
             }

            synchronized (this.bulletArrayList){
                for(int i = 0; i < this.bulletArrayList.size(); i++){
                    if (bulletArrayList.get(i).getStatus()){
                        Bullet bullet = bulletArrayList.get(i);
                        canvas.drawBitmap(bullet.getBitmap(),bullet.getRect().left,bullet.getRect().top,this.paint);
                    }
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
