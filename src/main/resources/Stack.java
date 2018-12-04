package main.resources;

public class Stack {
    Pair outer, inner;

    public Stack (int outerX, int outerY, int innerX, int innerY) {
        this.outer = new Pair(outerX, outerY);
        this.inner = new Pair(innerX, innerY);
    }

    public boolean inSamePositionAs (Stack s) {
        return s.outer.inSamePositionAs(this.outer) || s.outer.inSamePositionAs(this.inner)
                || s.inner.inSamePositionAs(this.inner) || s.inner.inSamePositionAs(this.outer);
    }

    public int getEnergy (String sequence) {
        if (outer.isAuPair(sequence)) {
            if (inner.isAuPair(sequence)) {
                return 110;
            } else {
                return 210;
            }
        } else {
            if (inner.isAuPair(sequence)) {
                return 210;
            } else {
                return 240;
            }
        }
    }

    @Override
    public String toString() {
        return "(" + outer.x + " (" + inner.x + ", " + inner.y + ") " + outer.y + ")";
    }
}