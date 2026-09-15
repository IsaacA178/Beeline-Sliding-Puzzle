import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for working with textual Beeline board layouts.
 */
public final class GridUtil {
    private GridUtil() {}

    /**
     * Finds connected horizontal or vertical runs represented by a character.
     * This utility is intentionally small so board parsing remains separate
     * from board state and movement rules.
     */
    public static List<Cell> findCells(char[][] grid, char target) {
        List<Cell> result = new ArrayList<>();

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] == target) {
                    result.add(new Cell(row, column));
                }
            }
        }

        return result;
    }
}
