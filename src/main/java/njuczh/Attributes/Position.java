package njuczh.Attributes;

import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class Position {
    private int x;
    private int y;
    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getI() {
        return y/72;
    }

    public int getJ() {
        return x/72;
    }

    public void setI(int i) {
        y = i*72;
    }

    public void setJ(int j) {
        x = j*72;
    }

    public String toString() {
        return ""+x+" "+y;
    }

    public boolean equalsTo(Position position) {
        if(position.x == this.x && position.y == this.y) {
            return true;
        }
        return false;
    }
}
