# Beeline Sliding Puzzle

A Java sliding-puzzle implementation focused on board state, movable boulders, collision checking, movement validation, and undoable moves.

## Features

- Represents a two-dimensional puzzle board
- Supports horizontal and vertical boulders
- Validates movement against board boundaries
- Prevents collisions between boulders
- Tracks successful moves
- Supports undoing the most recent move
- Separates board state from coordinate/grid utilities
- Uses object-oriented design to model puzzle state

## Technologies

- Java
- Object-oriented programming
- 2D arrays
- ArrayLists
- Records
- State management
- Movement validation

## Project Structure

```text
src/
├── Board.java
├── Boulder.java
├── Cell.java
└── GridUtil.java
```

## How It Works

`Board` owns the puzzle grid and controls whether boulders can move. Each `Boulder` maintains its occupied cells and orientation. Before a move is applied, the board checks every destination cell for boundaries and collisions.

Successful moves are stored in a history list, allowing the previous board state to be restored with `undo()`.

## Project Background

This project originated from a Java object-oriented programming assignment and was reorganized as a standalone portfolio implementation. The portfolio version focuses on the underlying board and movement logic rather than the original course-provided interface and testing infrastructure.
