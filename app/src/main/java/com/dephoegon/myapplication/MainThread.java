package com.dephoegon.myapplication;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception ignored) {  }
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception ignored) { }
                }
            }
        }
    }

    public void setRunning(boolean isRunning) { running = isRunning; }
}
