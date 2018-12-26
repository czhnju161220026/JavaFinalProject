package njuczh.Attributes;

import njuczh.MyAnnotation.TODO;

@TODO(todo="指示子弹的飞行方向")
public enum BulletDirection {
    LEFT(-18,0),
    RIGHT(18,0),
    LEFT_DOWN(-15,9),
    RIGHT_DOWN(15,9),
    LEFT_UP(-15,-9),
    RIGHT_UP(15,-9);

    final private int deltaInX;
    final private int deltaInY;

    BulletDirection(int x, int y) {
        deltaInX = x;
        deltaInY = y;
    }

    public int getDeltaInX() {
        return deltaInX;
    }

    public int getDeltaInY() {
        return deltaInY;
    }
}
