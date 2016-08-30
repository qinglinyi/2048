package com.qinglinyi.a2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private GridLayout gameGl;
    private GameMap mGameMap;
    private TextView scoreTv;

    private int[] backColors = new int[]{
            R.color._2Back,
            R.color._4Back,
            R.color._8Back,
            R.color._16Back,
            R.color._32Back,
            R.color._64Back,
            R.color._128Back,
            R.color._256Back,
            R.color._512Back,
            R.color._1024Back,
            R.color._2048Back
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameMap = new GameMap();

        gameGl = (GridLayout) findViewById(R.id.gameGl);
        Button startBtn = (Button) findViewById(R.id.startBtn);
        scoreTv = (TextView) findViewById(R.id.scoreTv);

        if (startBtn != null) {
            startBtn.setOnClickListener(this);
        }

        if (gameGl != null) {
            gameGl.setEnabled(false);
            gameGl.setOnTouchListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        mGameMap.reset();
        gameGl.setEnabled(true);
        map2View();
        ((TextView) v).setText("RESTART");
    }

    private void map2View() {
        int[][] map = mGameMap.getMaps();
        for (int i = 0; i < GameMap.LENGTH; i++) {
            for (int j = 0; j < GameMap.LENGTH; j++) {
                TextView child = (TextView) gameGl.getChildAt(i * GameMap.LENGTH + j);
                if (map[i][j] == 0) {
                    child.setText("");
                    child.setBackgroundResource(R.color.normalCardBack);
                } else {
                    child.setText(String.valueOf(map[i][j]));
                    int log = (int) (Math.log(map[i][j]) / Math.log(2));
                    if (log >= backColors.length) {
                        child.setBackgroundResource(backColors[backColors.length - 1]);
                    } else {
                        child.setBackgroundResource(backColors[log]);
                    }

                    if (log == 1) {//2
                        child.setTextColor(getResources().getColor(R.color._2Font));
                    } else if (log == 2) {//4
                        child.setTextColor(getResources().getColor(R.color._4Font));
                    } else {
                        child.setTextColor(getResources().getColor(R.color.otherFont));
                    }
                }
            }
        }

        scoreTv.setText(mGameMap.getScore() + "");
    }

    private float mX, mY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mX = x;
                mY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float dx = x - mX;
                float dy = y - mY;
                boolean isGameOver;
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        isGameOver = mGameMap.slide(Direction.RIGHT);
                    } else {
                        isGameOver = mGameMap.slide(Direction.LEFT);
                    }
                } else {
                    if (dy > 0) {
                        isGameOver = mGameMap.slide(Direction.DOWN);
                    } else {
                        isGameOver = mGameMap.slide(Direction.UP);
                    }
                }
                map2View();
                if (isGameOver) {
                    gameOver();
                }
                break;
        }
        return false;
    }

    private void gameOver() {
        gameGl.setEnabled(false);
        alert(getString(R.string.game_over));
    }

    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        System.out.println(msg);
    }

}
