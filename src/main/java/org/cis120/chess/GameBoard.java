package org.cis120.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

public class GameBoard extends JPanel {
    private Chess chessModel;
    private JLabel status;

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;

    public GameBoard(JLabel statusInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        chessModel = new Chess();
        status = statusInit;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // if in moving piece mode tries to move piece
                if (chessModel.getGameMode().equals(Mode.movePiece)) {
                    status.setText(chessModel.movePiece(p.x / 100, (800 - p.y) / 100));
                    chessModel.printBoard();
                } else {
                    status.setText(chessModel.select(p.x / 100, (800 - p.y) / 100));
                }

                repaint(); // repaints the game board
                updateStatus(); // updates the status JLabel
            }
        });
    }

    public void reset() {
        chessModel.reset();
        status.setText("White's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void undoSelection() {
        chessModel.undoSelection();
        status.setText("Select Again");
        repaint();
        requestFocusInWindow();
    }

    private void updateStatus() {
        int winner = chessModel.checkWinner();
        if (winner == 1) {
            status.setText("White wins!!");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            reset();
            status.setText("White wins!!");
        } else if (winner == 2) {
            status.setText("Black wins!!");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            reset();
            status.setText("Black wins!!");
        } else if (winner == 3) {
            status.setText("Stalemate");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            reset();
            status.setText("Stalemate");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Point selectedSquare = null;
        if (chessModel.getGameMode().equals(Mode.movePiece)) {
            selectedSquare = chessModel.getSelectedPiece();
        }
        // Draws board grid
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Color c1 = new Color(110, 66, 13);
                Color c2 = new Color(246, 194, 139);
                Color s = new Color(255, 118, 118);
                if (selectedSquare != null && row == selectedSquare.y && col == selectedSquare.x) {
                    g.setColor(s);
                } else if ((row % 2 == 0 && col % 2 == 0) || (row % 2 != 0 && col % 2 != 0)) {
                    g.setColor(c1);
                } else {
                    g.setColor(c2);
                }
                g.fillRect(col * 100, 700 - (row * 100), 100, 100);
            }
        }

        // draws pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = chessModel.getPieceInCell(row, col);
                if (p != null) {
                    p.paint(g);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    public void kingCastle() {
        status.setText(chessModel.kingCastle());
        repaint();
        requestFocusInWindow();
    }

    public void queenCastle() {
        status.setText(chessModel.queenCastle());
        repaint();
        requestFocusInWindow();
    }
}
