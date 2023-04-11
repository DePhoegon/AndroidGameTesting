package com.dephoegon.myapplication.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import androidx.annotation.NonNull;

import java.util.Random;

public class character {
    private final Bitmap image;
    private int x,y,xVelocity,yVelocity = 0;
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public character(Bitmap bmp) { image = bmp; }
    public character(Bitmap bmp, int xSpot, int ySpot) {
        image = bmp;
        setPos(xSpot, ySpot);
    }
    public character(Bitmap bmp, int xSpot, int ySpot, int xSpeed, int ySpeed) {
        image = bmp;
        setPos(xSpot, ySpot);
        setSpeed(xSpeed, ySpeed);
    }

    public void setPos(int newX, int newY) {
        x = newX;
        y = newY;
    }
    public void setSpeed(int newX, int newY) {
        xVelocity = newX;
        yVelocity = newY;
    }
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
    public void update() { update(0, 0, 0 ,0); }
    public void update(int relativeX, int relativeY, boolean isLocation) {
        if (isLocation) { update(relativeX, relativeY, 0,0); }
        else { update(0, 0, relativeX, relativeY); }
    }
    public void update(int relativeX, int relativeY, int relativeSpeedX, int relativeSpeedY) {
        y = y+relativeY < 0 ? 0 : Math.min(y + relativeY, screenHeight - image.getHeight());
        x = x+relativeX < 0 ? 0 : Math.min(x + relativeY, screenWidth - image.getWidth());
        yVelocity = yVelocity < 0 ? yVelocity - relativeSpeedY : yVelocity + relativeSpeedY;
        xVelocity = xVelocity < 0 ? xVelocity - relativeSpeedX : xVelocity + relativeSpeedX;
        updater();
    }
    private void updater() {
        int zeroSpeedReplace = new Random().nextInt(6)-3;
        zeroSpeedReplace = zeroSpeedReplace < 1 ? zeroSpeedReplace-1 : zeroSpeedReplace;
        xVelocity = xVelocity > screenWidth /2 ? screenWidth / 4 : xVelocity == 0 ? zeroSpeedReplace : xVelocity;
        yVelocity = yVelocity > screenWidth /2 ? screenWidth / 4 : yVelocity == 0 ? zeroSpeedReplace : yVelocity;
       if (y<0 && x< 0) {
           x = screenHeight / 2;
           y = screenWidth / 2;
       } else {
           x += xVelocity;
           y += yVelocity;
           if ((x>screenWidth - image.getWidth()) || x<0) { xVelocity = xVelocity*-1; }
           if ((y>screenHeight - image.getHeight()) || y<0) { yVelocity = yVelocity*-1; }
       }
    }
}
