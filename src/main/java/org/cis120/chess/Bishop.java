package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bishop extends Piece {
    public Bishop(boolean white, MyPoint curr) {
        super(white, curr);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage img = null;
        try {
            if (this.getIsWhite()) {
                img = ImageIO.read(new File("files/img/w_bishop.png"));
            } else {
                img = ImageIO.read(new File("files/img/b_bishop.png"));
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

        // up-left
        int row = curr.y + 1;
        int col = curr.x - 1;
        while (row <= 7 && col >= 0) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                row++;
                col--;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                row = 8; // breaks out of loop because hit a piece
                col = -1;
            } else {
                row = 8;
                col = -1;
            }
        }

        // up-right
        row = curr.y + 1;
        col = curr.x + 1;
        while (row <= 7 && col <= 7) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                row++;
                col++;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                row = 8; // breaks out of loop because hit a piece
                col = 8;
            } else {
                row = 8;
                col = 8;
            }
        }

        // down-left
        row = curr.y - 1;
        col = curr.x - 1;
        while (row >= 0 && col >= 0) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                row--;
                col--;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                row = -1; // breaks out of loop because hit a piece
                col = -1;
            } else {
                row = -1;
                col = -1;
            }
        }

        // down-right
        row = curr.y - 1;
        col = curr.x + 1;
        while (row >= 0 && col <= 7) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                row--;
                col++;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                row = -1; // breaks out of loop because hit a piece
                col = 8;
            } else {
                row = -1;
                col = 8;
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
        return (color + "_B" + alphabet[col] + row);
    }
}
