package main.resources;

import java.util.ArrayList;
import java.util.List;

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

    public boolean isAuPair (String sequence) {
        List au = new ArrayList(2);
        au.add('a'); au.add('u');

        return au.contains(sequence.charAt(this.x)) ||  au.contains(sequence.charAt(this.y));
    }

    public boolean isCgPair (String sequence) {
        return !isAuPair(sequence);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}