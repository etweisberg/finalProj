package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

public class Knight extends Piece {
    public Knight(boolean white, MyPoint curr) {
        super(white, curr);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage img = null;
        try {
            if (this.getIsWhite()) {
                img = ImageIO.read(new File("files/img/w_knight.png"));
            } else {
                img = ImageIO.read(new File("files/img/b_knight.png"));
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

        // all 8 possible L-shaped moves
        MyPoint curr = this.getCurrent();
        boolean white = this.getIsWhite();

        if (curr.y < 7 && curr.x > 1) {
            Piece m1 = board[curr.y + 1][curr.x - 2];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x - 2, curr.y + 1));
            }
        }
        if (curr.y < 7 && curr.x < 6) {
            Piece m1 = board[curr.y + 1][curr.x + 2];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x + 2, curr.y + 1));
            }
        }
        if (curr.y < 6 && curr.x > 0) {
            Piece m1 = board[curr.y + 2][curr.x - 1];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x - 1, curr.y + 2));
            }
        }
        if (curr.y < 6 && curr.x < 7) {
            Piece m1 = board[curr.y + 2][curr.x + 1];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x + 1, curr.y + 2));
            }
        }
        if (curr.y > 0 && curr.x > 1) {
            Piece m1 = board[curr.y - 1][curr.x - 2];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x - 2, curr.y - 1));
            }
        }
        if (curr.y > 0 && curr.x < 6) {
            Piece m1 = board[curr.y - 1][curr.x + 2];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x + 2, curr.y - 1));
            }
        }
        if (curr.y > 1 && curr.x > 0) {
            Piece m1 = board[curr.y - 2][curr.x - 1];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x - 1, curr.y - 2));
            }
        }
        if (curr.y > 1 && curr.x < 7) {
            Piece m1 = board[curr.y - 2][curr.x + 1];
            if (m1 == null || (m1 != null && white != m1.getIsWhite())) {
                this.addPossibleMove(new MyPoint(curr.x + 1, curr.y - 2));
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
        return (color + "_N" + alphabet[col] + row);
    }
}