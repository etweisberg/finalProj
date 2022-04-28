package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pawn extends Piece {
    public Pawn(boolean white, MyPoint curr) {
        super(white, curr);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage img = null;
        try {
            if (this.getIsWhite()) {
                img = ImageIO.read(new File("files/img/w_pawn.png"));
            } else {
                img = ImageIO.read(new File("files/img/b_pawn.png"));
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

        if (white) {
            // moving forward
            Piece pieceInFront = board[curr.y + 1][curr.x];
            if (pieceInFront == null) {
                this.addPossibleMove(new MyPoint(curr.x, curr.y + 1));
            }

            // moving two forward
            if (curr.y == 1) {
                Piece pieceIn2Front = board[curr.y + 2][curr.x];
                if (pieceIn2Front == null) {
                    this.addPossibleMove(new MyPoint(curr.x, curr.y + 2));
                }
            }

            // finding diagonal takes
            if (curr.x < 7) {
                Piece diagonalRight = board[curr.y + 1][curr.x + 1];
                if (diagonalRight != null && !diagonalRight.getIsWhite()) {
                    this.addPossibleMove(new MyPoint(curr.x + 1, curr.y + 1));
                }
            }
            if (curr.x > 0) {
                Piece diagonalLeft = board[curr.y + 1][curr.x - 1];
                if (diagonalLeft != null && !diagonalLeft.getIsWhite()) {
                    this.addPossibleMove(new MyPoint(curr.x - 1, curr.y + 1));
                }
            }

            // en passant square
            if (curr.y == 4) {
                Piece left = board[curr.y][curr.x - 1];
                Piece right = board[curr.y][curr.x + 1];
                if (left instanceof Pawn) {
                    this.addPossibleMove(new MyPoint(curr.x - 1, curr.y + 1));
                }
                if (right instanceof Pawn) {
                    this.addPossibleMove(new MyPoint(curr.x + 1, curr.y + 1));
                }
            }
        } else {
            // moving forward
            Piece pieceInFront = board[curr.y - 1][curr.x];
            if (pieceInFront == null) {
                this.addPossibleMove(new MyPoint(curr.x, curr.y - 1));
            }

            // moving two forward
            if (curr.y == 6) {
                Piece pieceIn2Front = board[curr.y - 2][curr.x];
                if (pieceIn2Front == null) {
                    this.addPossibleMove(new MyPoint(curr.x, curr.y - 2));
                }
            }

            // finding diagonal takes
            if (curr.x < 7) {
                Piece diagonalRight = board[curr.y - 1][curr.x + 1];
                if (diagonalRight != null && diagonalRight.getIsWhite()) {
                    this.addPossibleMove(new MyPoint(curr.x + 1, curr.y - 1));
                }
            }
            if (curr.x > 0) {
                Piece diagonalLeft = board[curr.y - 1][curr.x - 1];
                if (diagonalLeft != null && diagonalLeft.getIsWhite()) {
                    this.addPossibleMove(new MyPoint(curr.x - 1, curr.y - 1));
                }
            }

            // en passant square
            if (curr.y == 3) {
                Piece left = board[curr.y][curr.x - 1];
                Piece right = board[curr.y][curr.x + 1];
                if (left instanceof Pawn) {
                    this.addPossibleMove(new MyPoint(curr.x - 1, curr.y - 1));
                }
                if (right instanceof Pawn) {
                    this.addPossibleMove(new MyPoint(curr.x + 1, curr.y - 1));
                }
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
        return (color + "_P" + alphabet[col] + row);
    }
}
