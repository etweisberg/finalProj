package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class King extends Piece {
    private boolean hasMoved;

    public King(boolean white, MyPoint curr) {
        super(white, curr);
        this.hasMoved = false;
    }

    @Override
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    @Override
    public void setHasMoved() {
        this.hasMoved = true;
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage img = null;
        try {
            if (this.getIsWhite()) {
                img = ImageIO.read(new File("files/img/w_king.png"));
            } else {
                img = ImageIO.read(new File("files/img/b_king.png"));
            }
        } catch (IOException e) {
        }
        MyPoint curr = this.getCurrent();
        if (curr != null) {
            g.drawImage(img, (curr.x * 100) + 25, 725 - (curr.y * 100), 50, 50, null);
        }
    }

    @Override
    public void findMoves(Piece[][] board) {
        this.resetPossibleMoves();

        MyPoint curr = this.getCurrent();
        boolean white = this.getIsWhite();

        // squares in front

        if (curr.y < 7) {
            Piece m = board[curr.y + 1][curr.x];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x, curr.y + 1));
            }
        }
        if (curr.y < 7 && curr.x > 0) {
            Piece m = board[curr.y + 1][curr.x - 1];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x - 1, curr.y + 1));
            }
        }
        if (curr.y < 7 && curr.x < 7) {
            Piece m = board[curr.y + 1][curr.x + 1];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x + 1, curr.y + 1));
            }
        }

        // squares behind
        if (curr.y > 0) {
            Piece m = board[curr.y - 1][curr.x];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x, curr.y - 1));
            }
        }
        if (curr.y > 0 && curr.x > 0) {
            Piece m = board[curr.y - 1][curr.x - 1];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x - 1, curr.y - 1));
            }
        }
        if (curr.y > 0 && curr.x < 7) {
            Piece m = board[curr.y - 1][curr.x + 1];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x + 1, curr.y - 1));
            }
        }

        // squares to sides
        if (curr.x < 7) {
            Piece m = board[curr.y][curr.x + 1];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x + 1, curr.y));
            }
        }
        if (curr.x > 0) {
            Piece m = board[curr.y][curr.x - 1];
            if (m == null || white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(curr.x - 1, curr.y));
            }
        }
    }

    @Override
    public String stringOfSelf() {
        int row = this.getCurrent().y;
        int col = this.getCurrent().x;
        String[] alphabet = { "a", "b", "c", "d", "e", "f", "g", "h" };
        String color = null;
        if (this.getIsWhite()) {
            color = "w";
        } else {
            color = "b";
        }
        return (color + "_K" + alphabet[col] + row);
    }
}
