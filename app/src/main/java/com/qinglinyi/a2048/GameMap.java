package com.qinglinyi.a2048;

import java.util.Random;

/**
 * 地图
 *
 * @author qinglinyi
 * @since 1.0.0
 */
public class GameMap {

    private int score;
    private int[][] mMaps;
    private Point lastRandomPoint;//最后一次随机生成数的位置和值，x,y,value

    public static final int LENGTH = 4;

    public void reset() {
        clear();
        emptyRandomNum();
        emptyRandomNum();
    }

    public void clear() {
        score = 0;
        mMaps = new int[LENGTH][LENGTH];
    }

    /**
     * 在空白处随机填充2或者4，2的比例比4大
     */
    public void emptyRandomNum() {

        Random random = new Random();
        int x = random.nextInt(LENGTH);
        int y = random.nextInt(LENGTH);
        int num = random.nextInt(20);

        while (mMaps[x][y] != 0) {
            x = random.nextInt(LENGTH);
            y = random.nextInt(LENGTH);
        }

        if (num == 0) {
            mMaps[x][y] = 4;
        } else {
            mMaps[x][y] = 2;
        }

        lastRandomPoint = new Point(x, y, mMaps[x][y]);
    }

    public boolean slide(int direction) {

        switch (direction) {
            case Direction.LEFT:
                left();
                break;
            case Direction.UP:
                up();
                break;
            case Direction.RIGHT:
                right();
                break;
            case Direction.DOWN:
                down();
                break;
        }

        if (!isFull()) {// 没满
            emptyRandomNum();
        }

        return isOver();
    }

    public boolean isOver() {return isFull() && !canAdd();}

    public void left() {
        leftRemoveBlank();
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH - 1; j++) {
                if (mMaps[i][j] == mMaps[i][j + 1] && mMaps[i][j] != 0) {
                    add(i, j, i, j + 1);
                    leftRemoveBlank();
                }
            }
        }
    }

    public void leftRemoveBlank() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 1; j < LENGTH; j++) {
                int k = j;
                while (k > 0 && mMaps[i][k] != 0 && mMaps[i][k - 1] == 0) {
                    swap(i, k, i, k - 1);
                    k--;
                }
            }
        }
    }

    public void up() {
        upRemoveBlank();
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH - 1; j++) {
                if (mMaps[j][i] == mMaps[j + 1][i] && mMaps[j][i] != 0) {
                    add(j, i, j + 1, i);
                    upRemoveBlank();
                }
            }
        }
    }

    public void upRemoveBlank() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 1; j < LENGTH; j++) {
                int k = j;
                while (k > 0 && mMaps[k][i] != 0 && mMaps[k - 1][i] == 0) {
                    swap(k, i, k - 1, i);
                    k--;
                }
            }
        }
    }

    public void down() {
        downRemoveBlank();
        for (int i = 0; i < LENGTH; i++) {
            for (int j = LENGTH - 1; j > 0; j--) {
                if (mMaps[j][i] == mMaps[j - 1][i] && mMaps[j][i] != 0) {
                    add(j, i, j - 1, i);
                    downRemoveBlank();
                }
            }
        }
    }

    public void downRemoveBlank() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = LENGTH - 1; j > 0; j--) {
                int k = j;
                while (k < LENGTH && mMaps[k - 1][i] != 0 && mMaps[k][i] == 0) {
                    swap(k - 1, i, k, i);
                    k++;
                }
            }
        }
    }

    public void right() {
        rightRemoveBlank();
        for (int i = 0; i < LENGTH; i++) {
            for (int j = LENGTH - 1; j > 0; j--) {
                if (mMaps[i][j] == mMaps[i][j - 1] && mMaps[i][j] != 0) {
                    add(i, j, i, j - 1);
                    rightRemoveBlank();
                }
            }
        }
    }

    public void rightRemoveBlank() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = LENGTH - 1; j > 0; j--) {
                int k = j;
                while (k < LENGTH && mMaps[i][k - 1] != 0 && mMaps[i][k] == 0) {
                    swap(i, k - 1, i, k);
                    k++;
                }
            }
        }
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int temp = mMaps[x1][y1];
        mMaps[x1][y1] = mMaps[x2][y2];
        mMaps[x2][y2] = temp;
    }

    private void add(int x1, int y1, int x2, int y2) {
        mMaps[x1][y1] = mMaps[x1][y1] + mMaps[x2][y2];
        score += mMaps[x1][y1];
        mMaps[x2][y2] = 0;
    }

    public boolean isFull() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (mMaps[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canAdd() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (j > 1) {
                    if (mMaps[i][j] == mMaps[i][j - 1]) {
                        return true;
                    }
                }
                if (j < LENGTH - 1) {
                    if (mMaps[i][j] == mMaps[i][j + 1]) {
                        return true;
                    }
                }
                if (i > 1) {
                    if (mMaps[i][j] == mMaps[i - 1][j]) {
                        return true;
                    }
                }
                if (i < LENGTH - 1) {
                    if (mMaps[i][j] == mMaps[i + 1][j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void printMap() {
        StringBuilder sb = new StringBuilder("[");
        for (int[] mMap : mMaps) {
            sb.append("\n\t[");
            for (int j = 0; j < mMap.length; j++) {
                if (j != 0) {
                    sb.append(",");
                }
                sb.append(mMap[j]);
            }
            sb.append("]");
        }
        sb.append("\n]");
        System.out.println(sb.toString());
    }

    public int[][] getMaps() {
        return mMaps;
    }

    public void setMaps(int[][] maps) {
        mMaps = maps;
    }

    public Point getLastRandomPoint() {
        return lastRandomPoint;
    }

    public int getScore() {
        return score;
    }

    public static class Point {

        public int x;
        public int y;
        public int value;

        public Point(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}
