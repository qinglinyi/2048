package com.qinglinyi.a2048;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class GameMapTest {

    private GameMap mMap;
    private static final int[][] EMPTY_MAP = new int[4][4];

    @Before
    public void setUp() throws Exception {
        mMap = new GameMap();
        mMap.clear();
    }

    @Test
    public void testClear() throws Exception {
        mMap.setMaps(new int[][]{{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}});
        mMap.clear();
        mapEqual(mMap.getMaps(), EMPTY_MAP);
    }

    @Test
    public void testEmptyRandomNum() throws Exception {
        testClear();
        int[][] map = mMap.getMaps();
        mMap.printMap();
        mMap.emptyRandomNum();
        mMap.printMap();
        int x = mMap.getLastRandomPoint().x;
        int y = mMap.getLastRandomPoint().y;
        mapEmptyExcept(map, EMPTY_MAP, x, y);
        Assert.assertTrue(map[x][y] == 2 || map[x][y] == 4);
    }


    private void mapEmptyExcept(int[][] map1, int[][] map2, int x, int y) {
        Assert.assertEquals(map1.length, map2.length);
        for (int i = 0; i < map2.length; i++) {
            Assert.assertEquals(map1[i].length, map2[i].length);
            for (int j = 0; j < map2[i].length; j++) {
                if (x == i && y == j) {
                    Assert.assertNotEquals(map1[i][j], map2[i][j]);
                } else {
                    Assert.assertEquals(map1[i][j], map2[i][j]);
                }
            }
        }
    }

    private void mapEqual(int[][] map1, int[][] map2) {
        mapEmptyExcept(map1, map2, -1, -1);
    }

    @Test
    public void testLeftRemoveBlank() throws Exception {
        mMap.setMaps(new int[][]{{0, 1, 2, 3}, {1, 0, 3, 4}, {2, 0, 0, 5}, {0, 0, 6, 0}});
        mMap.printMap();
        mMap.leftRemoveBlank();
        mMap.printMap();
        int[][] map = new int[][]{{1, 2, 3, 0}, {1, 3, 4, 0}, {2, 5, 0, 0}, {6, 0, 0, 0}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testLeft() throws Exception {
        mMap.setMaps(new int[][]{{0, 2, 2, 4}, {2, 0, 4, 4}, {2, 0, 0, 2}, {0, 2, 2, 2}});
        mMap.printMap();
        mMap.left();
        mMap.printMap();
        int[][] map = new int[][]{{4, 4, 0, 0}, {2, 8, 0, 0}, {4, 0, 0, 0}, {4, 2, 0, 0}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testRightRemoveBlank() throws Exception {
        mMap.setMaps(new int[][]{{0, 1, 2, 3}, {1, 0, 3, 4}, {2, 0, 0, 5}, {0, 0, 6, 0}});
        mMap.printMap();
        mMap.rightRemoveBlank();
        mMap.printMap();
        int[][] map = new int[][]{{0, 1, 2, 3}, {0, 1, 3, 4}, {0, 0, 2, 5}, {0, 0, 0, 6}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testRight() throws Exception {
        mMap.setMaps(new int[][]{{0, 2, 2, 4}, {2, 0, 4, 4}, {2, 0, 0, 2}, {0, 2, 2, 2}});
        mMap.printMap();
        mMap.right();
        mMap.printMap();
        int[][] map = new int[][]{{0, 0, 4, 4}, {0, 0, 2, 8}, {0, 0, 0, 4}, {0, 0, 2, 4}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testUpRemoveBlank() throws Exception {
        mMap.setMaps(new int[][]{
                {0, 1, 2, 3},
                {1, 0, 3, 4},
                {2, 0, 0, 5},
                {0, 0, 6, 0}});
        mMap.printMap();
        mMap.upRemoveBlank();
        mMap.printMap();
        int[][] map = new int[][]{
                {1, 1, 2, 3},
                {2, 0, 3, 4},
                {0, 0, 6, 5},
                {0, 0, 0, 0}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testUp() throws Exception {
        mMap.setMaps(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {2, 0, 0, 2},
                {0, 2, 2, 2}});
        mMap.printMap();
        mMap.up();
        mMap.printMap();
        int[][] map = new int[][]{
                {4, 4, 2, 8},
                {0, 0, 4, 4},
                {0, 0, 2, 0},
                {0, 0, 0, 0}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testDownRemoveBlank() throws Exception {
        mMap.setMaps(new int[][]{
                {0, 1, 2, 3},
                {1, 0, 3, 4},
                {2, 0, 0, 5},
                {0, 0, 6, 0}});
        mMap.printMap();
        mMap.downRemoveBlank();
        mMap.printMap();
        int[][] map = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 2, 3},
                {1, 0, 3, 4},
                {2, 1, 6, 5}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testDown() throws Exception {
        mMap.setMaps(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {2, 0, 0, 2},
                {0, 2, 2, 2}});
        mMap.printMap();
        mMap.down();
        mMap.printMap();
        int[][] map = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 4, 8},
                {4, 4, 2, 4}};
        mapEqual(mMap.getMaps(), map);
    }

    @Test
    public void testIsFull() throws Exception {

        mMap.setMaps(new int[][]{{0, 2, 2, 4}, {2, 0, 4, 4}, {2, 0, 0, 2}, {0, 2, 2, 2}});
        Assert.assertFalse(mMap.isFull());
        mMap.setMaps(new int[][]{{1, 2, 2, 4}, {2, 1, 4, 4}, {2, 1, 1, 2}, {1, 2, 2, 2}});
        Assert.assertTrue(mMap.isFull());

        mMap.reset();
        while (!mMap.isFull()) {
            mMap.slide(Direction.LEFT);
        }

        int[][] map = mMap.getMaps();
        for (int[] aMap : map) {
            for (int anAMap : aMap) {
                Assert.assertNotEquals(0, anAMap);
            }
        }

        mMap.printMap();
    }

    @Test
    public void testSlide() throws Exception {
        // left
        mMap.setMaps(new int[][]{{0, 2, 2, 4}, {2, 0, 4, 4}, {2, 0, 0, 2}, {0, 2, 2, 2}});
        boolean isEnd = mMap.slide(Direction.LEFT);
        int[][] map = new int[][]{{4, 4, 0, 0}, {2, 8, 0, 0}, {4, 0, 0, 0}, {4, 2, 0, 0}};
        mapEqualAfterAddRandomNum(isEnd, map);

        // right
        mMap.setMaps(new int[][]{{0, 2, 2, 4}, {2, 0, 4, 4}, {2, 0, 0, 2}, {0, 2, 2, 2}});
        isEnd = mMap.slide(Direction.RIGHT);
        map = new int[][]{{0, 0, 4, 4}, {0, 0, 2, 8}, {0, 0, 0, 4}, {0, 0, 2, 4}};
        mapEqualAfterAddRandomNum(isEnd, map);

        // up
        mMap.setMaps(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {2, 0, 0, 2},
                {0, 2, 2, 2}});
        isEnd = mMap.slide(Direction.UP);
        map = new int[][]{
                {4, 4, 2, 8},
                {0, 0, 4, 4},
                {0, 0, 2, 0},
                {0, 0, 0, 0}};
        mapEqualAfterAddRandomNum(isEnd, map);

        // down
        mMap.setMaps(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {2, 0, 0, 2},
                {0, 2, 2, 2}});
        isEnd = mMap.slide(Direction.DOWN);
        map = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 4, 8},
                {4, 4, 2, 4}};
        mapEqualAfterAddRandomNum(isEnd, map);


        Random random = new Random();
        isEnd = false;
        while (!mMap.isFull() || mMap.canAdd()) {
            isEnd = mMap.slide(random.nextInt(4) + 1);
        }
        mMap.printMap();
        Assert.assertTrue(mMap.isOver());
        Assert.assertTrue(isEnd);
    }

    @Test
    public void testGameOver() throws Exception {
        mMap.reset();

        Random random = new Random();

        while (!mMap.slide(random.nextInt(4) + 1)) {
            mMap.printMap();
        }

        Assert.assertTrue(mMap.isOver());
        mMap.printMap();
    }

    @Test
    public void testCanAdd() throws Exception {

        mMap.setMaps(new int[][]{{0, 2, 2, 4}, {2, 0, 4, 4}, {2, 0, 0, 2}, {0, 2, 2, 2}});
        Assert.assertTrue(mMap.canAdd());

        mMap.setMaps(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        Assert.assertFalse(mMap.canAdd());

        mMap.setMaps(new int[][]{{1, 2, 3, 4}, {1, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        Assert.assertTrue(mMap.canAdd());

        mMap.setMaps(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 4}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        Assert.assertTrue(mMap.canAdd());

        mMap.setMaps(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 10, 12}, {13, 14, 15, 16}});
        Assert.assertTrue(mMap.canAdd());

        mMap.setMaps(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 12}});
        Assert.assertTrue(mMap.canAdd());

    }

    @Test
    public void testScore() throws Exception {
        int oldScore = mMap.getScore();
        mMap.setMaps(new int[][]{{0, 2, 2, 4}, {2, 0, 4, 4}, {2, 0, 0, 2}, {0, 2, 2, 2}});
        mMap.slide(Direction.LEFT);
        Assert.assertEquals(oldScore + 20, mMap.getScore());
    }

    private void mapEqualAfterAddRandomNum(boolean isEnd, int[][] map) {
        if (!isEnd) {
            GameMap.Point point = mMap.getLastRandomPoint();
            map[point.x][point.y] = point.value;
        }
        mapEqual(mMap.getMaps(), map);
    }
}