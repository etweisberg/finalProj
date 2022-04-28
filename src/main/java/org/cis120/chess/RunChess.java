package org.cis120.chess;

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

        final JFrame instructions = new JFrame("Instructions");
        instructions.setPreferredSize(new Dimension(400, 400));
        final JPanel instr_panel = new JPanel();
        instr_panel.setPreferredSize(new Dimension(400, 400));
        final JLabel instr = new JLabel(
                "" +
                        "<html>" +
                        "<h1 style=\"text-align: center;\">Chess</h1>" +
                        "<ul>" +
                        "<li>Move pieces by clicking on a piece to select it</li>" +
                        "<li>It will be highlighted red</li>" +
                        "<li>Click on the position you want to move to</li>" +
                        "<li>Use undo selection button if you want to pick a new piece</li>" +
                        "<li>The board will reset and tell you who won if someone wins!</li>" +
                        "</ul>" +
                        "</html>"
        );
        instr.setPreferredSize(new Dimension(400, 400));
        instr_panel.add(instr);
        instructions.add(instr_panel, BorderLayout.CENTER);
        instructions.pack();
        instructions.setVisible(true);
    }
}
