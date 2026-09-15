import java.util.ArrayList;
import java.util.List;

/**
 * Represents a movable boulder.
 *
 * Boulders may be horizontal or vertical and occupy one or more cells.
 */
public class Boulder {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private final boolean horizontal;
    private List<Cell> cells;

    public Boulder(List<Cell> cells, boolean horizontal) {
        if (cells == null || cells.isEmpty()) {
            throw new IllegalArgumentException("A boulder needs at least one cell.");
        }

        this.cells = new ArrayList<>(cells);
        this.horizontal = horizontal;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public List<Cell> getCells() {
        return new ArrayList<>(cells);
    }

    void setCells(List<Cell> cells) {
        this.cells = new ArrayList<>(cells);
    }

    void translate(int rowDelta, int columnDelta) {
        List<Cell> translated = new ArrayList<>();

        for (Cell cell : cells) {
            translated.add(
                new Cell(cell.row() + rowDelta, cell.column() + columnDelta)
            );
        }

        cells = translated;
    }
}
