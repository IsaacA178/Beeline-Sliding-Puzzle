import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state and rules of a Beeline sliding-puzzle board.
 *
 * A board contains boulders that occupy one or more grid cells. Boulders
 * can be grabbed and moved along their orientation when the destination
 * cells are clear. Successful moves are recorded so they can be undone.
 */
public class Board {
    private final int rows;
    private final int columns;
    private final Boulder[][] grid;
    private final List<Move> moveHistory;

    public Board(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive.");
        }

        this.rows = rows;
        this.columns = columns;
        this.grid = new Boulder[rows][columns];
        this.moveHistory = new ArrayList<>();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * Places a boulder on the board.
     */
    public void placeBoulder(Boulder boulder) {
        for (Cell cell : boulder.getCells()) {
            validateCell(cell.row(), cell.column());

            if (grid[cell.row()][cell.column()] != null) {
                throw new IllegalArgumentException("Boulder overlaps an occupied cell.");
            }
        }

        for (Cell cell : boulder.getCells()) {
            grid[cell.row()][cell.column()] = boulder;
        }
    }

    /**
     * Attempts to move a boulder by one cell in its orientation.
     *
     * @return true when the move is legal and was applied
     */
    public boolean move(Boulder boulder, int direction) {
        if (boulder == null || !contains(boulder)) {
            return false;
        }

        int rowDelta = 0;
        int columnDelta = 0;

        if (boulder.isHorizontal()) {
            if (direction == Boulder.LEFT) {
                columnDelta = -1;
            } else if (direction == Boulder.RIGHT) {
                columnDelta = 1;
            } else {
                return false;
            }
        } else {
            if (direction == Boulder.UP) {
                rowDelta = -1;
            } else if (direction == Boulder.DOWN) {
                rowDelta = 1;
            } else {
                return false;
            }
        }

        if (!canMove(boulder, rowDelta, columnDelta)) {
            return false;
        }

        List<Cell> oldCells = new ArrayList<>(boulder.getCells());

        clearBoulder(boulder);
        boulder.translate(rowDelta, columnDelta);
        placeBoulder(boulder);

        moveHistory.add(new Move(boulder, rowDelta, columnDelta, oldCells));
        return true;
    }

    /**
     * Checks whether every cell occupied by a boulder can be translated
     * by the requested amount.
     */
    public boolean canMove(Boulder boulder, int rowDelta, int columnDelta) {
        if (boulder == null || !contains(boulder)) {
            return false;
        }

        for (Cell cell : boulder.getCells()) {
            int newRow = cell.row() + rowDelta;
            int newColumn = cell.column() + columnDelta;

            if (newRow < 0 || newRow >= rows ||
                newColumn < 0 || newColumn >= columns) {
                return false;
            }

            Boulder occupant = grid[newRow][newColumn];
            if (occupant != null && occupant != boulder) {
                return false;
            }
        }

        return true;
    }

    /**
     * Undoes the most recent successful move.
     */
    public boolean undo() {
        if (moveHistory.isEmpty()) {
            return false;
        }

        Move move = moveHistory.remove(moveHistory.size() - 1);
        clearBoulder(move.boulder());

        move.boulder().setCells(move.oldCells());
        placeBoulder(move.boulder());

        return true;
    }

    /**
     * Returns whether the board contains the supplied boulder.
     */
    public boolean contains(Boulder boulder) {
        for (Cell cell : boulder.getCells()) {
            if (!isInBounds(cell)) {
                return false;
            }
            if (grid[cell.row()][cell.column()] != boulder) {
                return false;
            }
        }
        return true;
    }

    private void clearBoulder(Boulder boulder) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (grid[row][column] == boulder) {
                    grid[row][column] = null;
                }
            }
        }
    }

    private boolean isInBounds(Cell cell) {
        return cell.row() >= 0 && cell.row() < rows &&
               cell.column() >= 0 && cell.column() < columns;
    }

    private void validateCell(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Cell is outside the board.");
        }
    }

    private record Move(
        Boulder boulder,
        int rowDelta,
        int columnDelta,
        List<Cell> oldCells
    ) {}
}
