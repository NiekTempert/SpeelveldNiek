package com.example.niek.speelveld;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Niek on 25-7-2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private long obstacleStartTime;
    private long heartStartime;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Heart> hearts;
    private ArrayList<Obstacle> obstacles;
    private Context c;
    MediaPlayer mySound;
    Paint myPaint = new Paint();


    public GamePanel(Context context){
        super(context);

        c = context;

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter <1000){
            counter++;
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
            }
            catch(InterruptedException e){e.printStackTrace();}
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        player = new Player(100, 100);
        obstacles = new ArrayList<>();
        hearts = new ArrayList<>();
        obstacleStartTime = System.nanoTime();
        heartStartime = System.nanoTime();


        mySound = MediaPlayer.create(c, R.raw.backgroundmusic);
        mySound.setLooping(true);
        //mySound.start();

        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction()== MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()){
                player.setPlaying(true);
            }else{
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }
    public void update(){
        if(player.getPlaying()) {
            bg.update();
            player.update();

            //Add obstacles with timer
            long obstacleElapsed = (System.nanoTime()-obstacleStartTime)/1000000;
            long heartElapsed = (System.nanoTime()-heartStartime)/1000000;

            if(obstacleElapsed >(2000 - player.getScore()/4)){
                obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.rsz_spike1), WIDTH + 10, HEIGHT - 51, 14, 51, player.getScore()));

                //reset timer
                obstacleStartTime = System.nanoTime();
            }
            if(heartElapsed > (30000 - player.getScore()/4)){

                hearts.add(new Heart(BitmapFactory.decodeResource(getResources(),R.drawable.heart), WIDTH + 10, HEIGHT - 75, 70, 75, player.getScore()));

                //reset timer
                heartStartime = System.nanoTime();
            }
        }
        //Loop through every obstacle and check collision with player
        for(int i = 0; i<obstacles.size(); i++){
            obstacles.get(i).update();
            if (collision(obstacles.get(i), player)) {
                obstacles.remove(i);
                player.setLives(false);

                //Set sound and play
                mySound = MediaPlayer.create(c, R.raw.looselife);
                mySound.start();

                //If player has 0 lives start new activity
                if(player.getLives() == 0) {
                    player.setPlaying(false);

                    Intent intent = new Intent(c, Result.class);
                    intent.putExtra("SCORE", player.getScore());
                    c.startActivity(intent);
                }
                break;
            }
            //Remove obstacles that are out of the screen
            if (obstacles.get(i).getX() < -100) {
                obstacles.remove(i);
                break;
            }
        }
        //Loop through every heart and check collision with player
        for (int i = 0; i<hearts.size(); i++) {
            hearts.get(i).update();
            if(collision(hearts.get(i), player)){
                hearts.remove(i);
                player.setLives(true);

                //Set sound and play
                mySound = MediaPlayer.create(c, R.raw.life);
                mySound.start();
            }
            //Remove hearts that are out of the screen
            if(hearts.get(i).getX() <-100){
                hearts.remove(i);
                break;
            }
        }


    }

    public boolean collision(GameObject o, GameObject p){
        if(Rect.intersects(o.getRectangle(), p.getRectangle())){
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        //Get Width and Height from screen
        final float scaleFactorX = (float)getWidth()/WIDTH;
        final float scaleFactorY = (float)getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            //Draw objects on canvas
            bg.draw(canvas);
            player.draw(canvas);

            for(Obstacle o : obstacles ){
                o.draw(canvas);
            }

            for(Heart h : hearts) {
                h.draw(canvas);
            }

            myPaint.setTextSize(20);

            //Draw text on canvas
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            canvas.drawText("HIGHSCORE  " + player.getScore(), GamePanel.WIDTH - 200 , 25, myPaint);
            canvas.drawText("LIVES : " + player.getLives(), GamePanel.WIDTH - 200, 50, myPaint);
            canvas.restoreToCount(savedState);
        }
    }
}
