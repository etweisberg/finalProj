package org.cis120.chess;

import java.awt.*;

public class MyPoint extends Point implements Comparable<MyPoint>{
    public MyPoint(int x, int y) {
        super(x,y);
    }

    @Override
    public int compareTo(MyPoint o) {
        if (x*x + y*y < o.x*o.x + o.y*o.y) {
            return -1;
        } else if (x*x + y*y > o.x*o.x + o.y*o.y) {
            return 1;
        }

        // In case of same distance to origin compare x and subsequent y positions.

        if (x > o.x) {
            return -1;
        } else if (x < o.x) {
            return 1;
        } else if (y < o.y) {
            return 1;
        } else if (y > o.y) {
            return -1;
        }
        return 0;
    }
}
