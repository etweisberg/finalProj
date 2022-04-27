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
  - Only one TreeSet to store these moves so it is not redundant
  - Have to use a Collection because you don't know how many possible moves there are going to be 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  https://opengameart.org/content/chess-pieces-and-board-squares
  https://www.chess.com/article/view/the-4-move-checkmate
