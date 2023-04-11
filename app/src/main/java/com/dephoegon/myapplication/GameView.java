package com.dephoegon.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.dephoegon.myapplication.sprite.character;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final MainThread thread;
    private character characterSprite;

    //simmer
    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        characterSprite = new character(BitmapFactory.decodeResource(getResources(), R.drawable.hold), 100, 100, 10, 5);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean tryTryAgain = true;
        while (tryTryAgain) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException ignored) {  }
            tryTryAgain = false;
        }
    }

    public void update() {
        boolean alterSprite = new Random().nextInt(10)-8 > 0;
        if (alterSprite) {
            int value = new Random().nextInt(4)-2;
            boolean isLocation = new Random().nextInt(10)-5 < 0;
            boolean isX = new Random().nextInt(10)-5 < 0;
            int x = isX ? value : 0;
            int y = isX ? 0 : value;
            characterSprite.update(x, y, isLocation);
        } else { characterSprite.update(); }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas!=null) {
            characterSprite.draw(canvas);
        }
    }
}
