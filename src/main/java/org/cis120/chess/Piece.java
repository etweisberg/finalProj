package org.cis120.chess;

import java.awt.*;
import java.util.TreeSet;

public abstract class Piece {
    private boolean isWhite;
    private MyPoint current;
    private TreeSet<MyPoint> possibleMoves;

    public Piece(boolean white, MyPoint curr) {
        this.isWhite = white;
        this.current = curr;
        this.possibleMoves = new TreeSet<MyPoint>();
    }

    // functions to be overriden
    public void paint(Graphics g) {
    }

    public void findMoves(Piece[][] board) {
    }

    public void getTaken() {
        this.current = null;
    }

    public MyPoint getCurrent() {
        return this.current;
    }

    public boolean getIsWhite() {
        return this.isWhite;
    }

    public void resetPossibleMoves() {
        this.possibleMoves = new TreeSet<>();
    }

    public void addPossibleMove(MyPoint p) {
        this.possibleMoves.add(p);
    }

    public void setCurrent(MyPoint p) {
        this.current = p;
    }

    public TreeSet<MyPoint> getPossibleMoves() {
        return this.possibleMoves;
    }

    public String stringOfSelf() {
        return null;
    }

    public void setHasMoved() {
    }

    public boolean getHasMoved() {
        return false;
    }
}
