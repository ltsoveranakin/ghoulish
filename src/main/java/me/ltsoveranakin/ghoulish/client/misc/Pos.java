package me.ltsoveranakin.ghoulish.client.misc;

public class Pos {
    private final int x;
    private final int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pos add(int x) {
        return new Pos(this.x + x, y);
    }

    public Pos add(int x, int y) {
        return new Pos(this.x + x, this.y + y);
    }

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
