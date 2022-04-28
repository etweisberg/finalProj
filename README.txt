=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array

  - Chess class has a 2D array for the board
  - In updateBoard() the 2D array is iterated through properly
  - The 2D array is the only variable storing the entire board's state, so it is not redundant
  - The array is encapsulated as it is a private field of the Chess class
  - A 2D array is the most appropriate data structure for representing a grid
    because it can be indexed by the row and the column to identify specific pieces, getting pieces by coordinates.
    This is particularly useful for finding moves because you can loop up and down a row, diagonally, side to side, etc.
  - Used my custom Piece type because the board is composed of Pieces. Uses the parent class
    because the board has different types of pieces.

  2. Complex Game Logic
  - All check features
  - Checkmate
  - Castling
  - En Passant
  - Pawn promotes to queen in back rank

  3. Inheritance and subtyping
  - Piece abstract class implemented by Rook, Queen, King, Pawn, Knight, Bishop subclasses
  - Abstract class because subclasses inherit the general structure of piece along with its getters and setters
  - Overrides for finding moves and returning a string of itself to be specific to the piece subtype
  - Dynamic dispatch when the findMoves function is called on a piece and it uses the subtype's custom method
  - Also made a class that is a subtype of Point and implements Comparable so that I can store points in a TreeSet

  4. Collections
  - TreeSet of possible moves is used for pieces
  - Iterate through possible moves with a for-each loop
  - Only one TreeSet to store these moves, so it is not redundant
  - Have to use a Collection because you don't know how many possible moves there are going to be before you find them
  - TreeSet is a private field for the Piece class, so it is encapsulated

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Piece is an abstract class representing the pieces on the board.
King, Knight, Queen, Rook, Bishop, and Pawn all extend the Piece class with custom methods reflecting their particular
movement patterns.
GameBoard instantiates the chess model and controls the model from mouse movements, while also repainting the board
after moves.
RunChess sets up the top-level frame and widgets for the GUI.
Chess is the model of the game containing all the information about the board and its pieces, as well as methods for
setting up the board and updating it based on the moves that are played.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

The biggest issue I had was checking whether certain variables were pointing to the same instance of an object or
aliases. This made it hard to know exactly what the state of the board was until I implemented the updateBoard function.
Now, I am able to keep the 2D array with the exact same pieces as represented by the private fields of the chess model,
most notably the kings, which I need to be able to call upon with their current position for check and checkmate.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I think my design has a good separation of functionality and is well encapsulated. If given the chance, I would try to
make it even less redundant by eliminating the way each Piece knows its current position and the board stores these,
or at least link them together.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  https://opengameart.org/content/chess-pieces-and-board-squares
  https://www.chess.com/article/view/the-4-move-checkmate
