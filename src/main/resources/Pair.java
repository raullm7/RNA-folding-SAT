package main.resources;

public class Pair {
    int x;
    int y;

    public Pair (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean inSamePositionAs(Pair p) {
        return this.x == p.x || this.x == p.y || this.y == p.x || this.y == p.y;
    }

    public boolean formsPseudoknotWith(Pair p) {
        return this.y > p.x && this.y < p.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}