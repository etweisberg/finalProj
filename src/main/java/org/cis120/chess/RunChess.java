package org.cis120.chess;

import org.cis120.chess.GameBoard;

import javax.swing.*;
import java.awt.*;

public class RunChess implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("Scuffed Chess");
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        final org.cis120.chess.GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton undoSelection = new JButton("Undo Selection");
        undoSelection.addActionListener(e -> board.undoSelection());
        control_panel.add(undoSelection);

        final JButton kcastle = new JButton("King Side Castle");
        kcastle.addActionListener(e -> board.kingCastle());
        control_panel.add(kcastle);

        final JButton qcastle = new JButton("Queen Side Castle");
        qcastle.addActionListener(e -> board.queenCastle());
        control_panel.add(qcastle);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        board.reset();
    }
}
