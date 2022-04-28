package org.cis120.chess;

import java.util.Arrays;
import java.util.TreeSet;

public class Chess {

    private Mode gameMode;
    private MyPoint selectedPiece;
    private Piece[][] board;
    private boolean whiteTurn;
    private Piece[] blackBackRank;
    private Piece[] whiteBackRank;
    private Piece[] blackPawns;
    private Piece[] whitePawns;
    private Piece whiteKing;
    private Piece blackKing;
    private Piece wRook1;
    private Piece wRook2;
    private Piece bRook1;
    private Piece bRook2;

    public Chess() {
        this.gameMode = Mode.pickPiece;
        this.selectedPiece = null;
        this.whiteTurn = true;
        setPieces();
        this.board = newBoard();
    }

    public void setPieces() {
        // Black Pieces
        Piece bKing = new King(false, new MyPoint(4, 7));
        Piece bQueen = new Queen(false, new MyPoint(3, 7));
        Piece bBish1 = new Bishop(false, new MyPoint(2, 7));
        Piece bBish2 = new Bishop(false, new MyPoint(5, 7));
        Piece bKnight1 = new Knight(false, new MyPoint(1, 7));
        Piece bKnight2 = new Knight(false, new MyPoint(6, 7));
        Piece bRook1 = new Rook(false, new MyPoint(0, 7));
        Piece bRook2 = new Rook(false, new MyPoint(7, 7));
        Piece[] bPawns = new Piece[8];
        for (int i = 0; i < 8; i++) {
            bPawns[i] = new Pawn(false, new MyPoint(i, 6));
        }

        // White Pieces
        Piece wKing = new King(true, new MyPoint(4, 0));
        Piece wQueen = new Queen(true, new MyPoint(3, 0));
        Piece wBish1 = new Bishop(true, new MyPoint(2, 0));
        Piece wBish2 = new Bishop(true, new MyPoint(5, 0));
        Piece wKnight1 = new Knight(true, new MyPoint(1, 0));
        Piece wKnight2 = new Knight(true, new MyPoint(6, 0));
        Piece wRook1 = new Rook(true, new MyPoint(0, 0));
        Piece wRook2 = new Rook(true, new MyPoint(7, 0));
        Piece[] wPawns = new Piece[8];
        for (int i = 0; i < 8; i++) {
            wPawns[i] = new Pawn(true, new MyPoint(i, 1));
        }

        this.blackKing = bKing;
        this.whiteKing = wKing;
        this.wRook1 = wRook1;
        this.wRook2 = wRook2;
        this.bRook1 = bRook1;
        this.bRook2 = bRook2;
        this.blackBackRank = new Piece[] { bRook1, bKnight1, bBish1, bQueen, bKing, bBish2,
            bKnight2, bRook2 };
        this.whiteBackRank = new Piece[] { wRook1, wKnight1, wBish1, wQueen, wKing, wBish2,
            wKnight2, wRook2 };
        this.blackPawns = bPawns;
        this.whitePawns = wPawns;
    }

    private Piece[][] newBoard() {
        Piece[] emptyRow = new Piece[8];
        Arrays.fill(emptyRow, null);

        // populating board
        Piece[][] board = {
            this.whiteBackRank,
            this.whitePawns,
            emptyRow,
            emptyRow,
            emptyRow,
            emptyRow,
            this.blackPawns,
            this.blackBackRank
        };

        return board;
    }

    public String movePiece(int x, int y) {
        // if x,y is in the selectedPieces possibleMoves go there else say invalid move
        MyPoint currentPos = this.selectedPiece;
        Piece selected = getPieceInCell(currentPos.x, currentPos.y);
        MyPoint movePos = new MyPoint(x, y);
        selected.findMoves(this.board);
        TreeSet<MyPoint> possibleMoves = selected.getPossibleMoves();
        System.out.println(possibleMoves);

        // checking if there is a piece to be taken
        Piece lostPiece = getPieceInCell(movePos.x, movePos.y);

        // en passant take
        if (selected instanceof Pawn && movePos.x != currentPos.x && lostPiece == null) {
            if (this.whiteTurn) {
                lostPiece = getPieceInCell(movePos.x, movePos.y - 1);
            } else {
                lostPiece = getPieceInCell(movePos.x, movePos.y + 1);
            }
        }

        if (possibleMoves.contains(movePos)) {
            selected.setCurrent(movePos);
            if (lostPiece != null) { // if there was a piece to be taken get taken
                lostPiece.getTaken();
            }
            updateBoard();
            // if the move will put you in check don't allow it to be executed, otherwise
            // update the board
            if (!inCheck()) {
                this.gameMode = Mode.pickPiece;
                this.whiteTurn = !this.whiteTurn;
                if (selected instanceof Rook || selected instanceof King) {
                    selected.setHasMoved();
                }
                if (selected instanceof Pawn) {
                    Piece queen = queenPromotion(selected);
                    if (queen != null) {
                        selected.getTaken();
                        updateBoard();
                        this.board[movePos.y][movePos.x] = queen;
                    }
                }
                return "Move executed | White turn: " + this.whiteTurn;
            } else {
                selected.setCurrent(currentPos);
                this.board[currentPos.y][currentPos.x] = selected;
                if (lostPiece != null) { // if there was a piece to be taken get taken
                    lostPiece.setCurrent(movePos);
                    this.board[movePos.y][movePos.x] = lostPiece;
                }
                updateBoard();
                this.gameMode = Mode.movePiece;
                return "This move puts/keeps you in check!";
            }
        } else {
            this.gameMode = Mode.movePiece;
            return "Invalid move";
        }
    }

    private Piece queenPromotion(Piece p) {
        MyPoint curr = p.getCurrent();
        if (curr != null) {
            if (curr.y == 7 && p.getIsWhite()) {
                p.getTaken();
                Piece newQueen = new Queen(true, curr);
                return newQueen;
            } else if (curr.y == 0 && !p.getIsWhite()) {
                p.getTaken();
                Piece newQueen = new Queen(false, curr);
                return newQueen;
            }
        }
        return null;
    }

    private void updateBoard() {
        Piece[][] newBoard = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = this.board[row][col];
                if (p != null) {
                    MyPoint curr = p.getCurrent();
                    if (curr != null) {
                        newBoard[curr.y][curr.x] = p;
                    }
                }
            }
        }
        this.board = newBoard;
    }

    public void reset() {
        this.gameMode = Mode.pickPiece;
        this.selectedPiece = null;
        this.whiteTurn = true;
        setPieces();
        this.board = newBoard();
    }

    public String select(int x, int y) {
        Piece selected = getPieceInCell(x, y);

        // if you click on an empty space
        if (selected == null) {
            this.gameMode = Mode.pickPiece;
            return "Invalid Selection";
        }

        // if piece is the right color or not
        if (selected.getIsWhite() == this.whiteTurn) {
            this.selectedPiece = new MyPoint(x, y);
            this.gameMode = Mode.movePiece;
            return "Piece Selected: "
                    + getPieceInCell(this.selectedPiece.x, this.selectedPiece.y).stringOfSelf();
        } else {
            this.gameMode = Mode.pickPiece;
            return "Invalid Selection";
        }
    }

    public Mode getGameMode() {
        return this.gameMode;
    }

    public boolean inCheck() {
        // if king can be taken then in check otherwise not
        if (this.whiteTurn) {
            MyPoint kingPos = this.whiteKing.getCurrent();
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Piece p = getPieceInCell(col, row);
                    if (p != null && !p.getIsWhite()) {
                        p.findMoves(this.board);
                        TreeSet<MyPoint> moves = p.getPossibleMoves();
                        if (moves.contains(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            MyPoint kingPos = this.blackKing.getCurrent();
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Piece p = getPieceInCell(col, row);
                    if (p != null && p.getIsWhite()) {
                        p.findMoves(this.board);
                        TreeSet<MyPoint> moves = p.getPossibleMoves();
                        if (moves.contains(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int checkWinner() {
        if (inCheck()) {
            boolean stillCheck = checkMoves();
            if (stillCheck) {
                if (this.whiteTurn) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                return -1;
            }
        } else {
            boolean stillCheck = checkMoves();
            if (stillCheck) {
                return 3;
            } else {
                return -1;
            }
        }
    }

    public boolean checkMoves() {
        boolean stillCheck = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = getPieceInCell(col, row);
                if (p != null && this.whiteTurn == p.getIsWhite()) {
                    p.findMoves(this.board);
                    TreeSet<MyPoint> moves = p.getPossibleMoves();
                    for (MyPoint m : moves) {
                        MyPoint currentPos = p.getCurrent();
                        Piece lost = getPieceInCell(m.x, m.y);
                        p.setCurrent(m);
                        if (lost != null) {
                            lost.getTaken();
                        }
                        updateBoard();
                        if (!inCheck()) {
                            stillCheck = false;
                        }
                        p.setCurrent(currentPos);
                        this.board[currentPos.y][currentPos.x] = p;
                        if (lost != null) { // if there was a piece to be taken get taken
                            lost.setCurrent(m);
                            this.board[m.y][m.x] = lost;
                        }
                        updateBoard();
                    }
                }
            }
        }
        return stillCheck;
    }

    public Piece getPieceInCell(int col, int row) {
        return this.board[row][col];
    }

    public void printBoard() {
        String[][] output = new String[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (this.board[row][col] != null) {
                    output[row][col] = this.board[row][col].stringOfSelf();
                }
            }
        }
        System.out.println("_________________\n");
        System.out.println(
                Arrays.deepToString(output).replace("], ", "]\n").replace("[[", "[")
                        .replace("]]", "]")
        );
        System.out.println("_________________\n");
    }

    public void undoSelection() {
        this.selectedPiece = null;
        this.gameMode = Mode.pickPiece;
    }

    public MyPoint getSelectedPiece() {
        return this.selectedPiece;
    }

    public String kingCastle() {
        if (this.whiteTurn && !this.whiteKing.getHasMoved() && !this.wRook2.getHasMoved()) {
            Piece p1 = getPieceInCell(5, 0);
            Piece p2 = getPieceInCell(6, 0);
            if (p1 == null && p2 == null) {
                this.whiteKing.setCurrent(new MyPoint(6, 0));
                this.wRook2.setCurrent(new MyPoint(5, 0));
                updateBoard();
                this.gameMode = Mode.pickPiece;
                this.selectedPiece = null;
                this.whiteTurn = !this.whiteTurn;
                return "White King Side Castle Executed";
            } else {
                return "Castle Not Allowed";
            }
        } else if (!this.whiteTurn && !this.blackKing.getHasMoved() && !this.bRook2.getHasMoved()) {
            Piece p1 = getPieceInCell(5, 7);
            Piece p2 = getPieceInCell(6, 7);
            if (p1 == null && p2 == null) {
                this.blackKing.setCurrent(new MyPoint(6, 7));
                this.bRook2.setCurrent(new MyPoint(5, 7));
                updateBoard();
                this.gameMode = Mode.pickPiece;
                this.selectedPiece = null;
                this.whiteTurn = !this.whiteTurn;
                return "Black King Side Castle Executed";
            } else {
                return "Castle Not Allowed";
            }
        } else {
            return "Castle Not Allowed";
        }
    }

    public String queenCastle() {
        if (this.whiteTurn && !this.whiteKing.getHasMoved() && !this.wRook1.getHasMoved()) {
            Piece p1 = getPieceInCell(1, 0);
            Piece p2 = getPieceInCell(2, 0);
            Piece p3 = getPieceInCell(3, 0);
            if (p1 == null && p2 == null && p3 == null) {
                this.whiteKing.setCurrent(new MyPoint(2, 0));
                this.wRook1.setCurrent(new MyPoint(3, 0));
                updateBoard();
                this.gameMode = Mode.pickPiece;
                this.selectedPiece = null;
                this.whiteTurn = !this.whiteTurn;
                return "White Queen Side Castle Executed";
            } else {
                return "Castle Not Allowed";
            }
        } else if (!this.whiteTurn && !this.blackKing.getHasMoved() && !this.bRook1.getHasMoved()) {
            Piece p1 = getPieceInCell(1, 7);
            Piece p2 = getPieceInCell(2, 7);
            Piece p3 = getPieceInCell(3, 7);
            if (p1 == null && p2 == null && p3 == null) {
                this.blackKing.setCurrent(new MyPoint(2, 7));
                this.bRook1.setCurrent(new MyPoint(3, 7));
                updateBoard();
                this.gameMode = Mode.pickPiece;
                this.selectedPiece = null;
                this.whiteTurn = !this.whiteTurn;
                return "White Queen Side Castle Executed";
            } else {
                return "Castle Not Allowed";
            }
        } else {
            return "Castle Not Allowed";
        }
    }
}
