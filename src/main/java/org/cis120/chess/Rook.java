package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rook extends Piece {
    private boolean hasMoved;

    public Rook(boolean white, MyPoint curr) {
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
                img = ImageIO.read(new File("files/img/w_rook.png"));
            } else {
                img = ImageIO.read(new File("files/img/b_rook.png"));
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
        // forward
        int row = curr.y + 1;
        int col = curr.x;
        while (row <= 7) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                row++;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                row = 8;
            } else {
                row = 8;
            }
        }

        // backward
        row = curr.y - 1;
        col = curr.x;
        while (row >= 0) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                row--;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                row = -1;
            } else {
                row = -1;
            }
        }

        // left
        row = curr.y;
        col = curr.x - 1;
        while (col >= 0) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                col--;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                col = -1;
            } else {
                col = -1;
            }
        }

        // right
        row = curr.y;
        col = curr.x + 1;
        while (col <= 7) {
            Piece m = board[row][col];
            if (m == null) {
                this.addPossibleMove(new MyPoint(col, row));
                col++;
            } else if (white != m.getIsWhite()) {
                this.addPossibleMove(new MyPoint(col, row));
                col = 8;
            } else {
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
        return (color + "_R" + alphabet[col] + row);
    }
}
