package com.example.niek.speelveld;

import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * Created by Niek on 15-8-2017.
 */

public class Player extends GameObject {
    private int score;
    private boolean up;
    private boolean playing;
    private long startTime;
    private int h = 0;
    private boolean jump;
    private int lives = 3;

    public Player(int h , int w){
        x = 25;
        y = GamePanel.HEIGHT - 103;

        height = h;
        width = w;

        score = 0;
        startTime = System.nanoTime();
    }
    public void setUp(boolean b){up = b;}

    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            score++;
            startTime = System.nanoTime();
        }
        if(up && h == 0){
            jump = true;
            up = false;

        }
        if (jump) {
            if (h < 120) {
                h = h + 7;
                y = y - 7;
            } else {
                jump = false;
            }
        } else {
            if (h > 0) {
                h = h - 7;
                y = y + 7;
            }
        }
    }

    public void draw(Canvas canvas){
        Paint myPaint = new Paint();
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(7);

        canvas.drawRect(getRectangle(), myPaint );
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public int getLives() {
        return lives;
    }

    public void setLives(boolean lives) {
        if(lives){
            this.lives++;
        }else{
            this.lives--;
        }
    }
}
