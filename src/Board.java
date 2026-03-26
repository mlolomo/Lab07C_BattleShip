import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int SIZE = 10;

    private Cell[][]  grid;
    private Ship[]    ships;
    private Random    rand;

    private static final int[] SHIP_SIZES = {5, 4, 3, 3, 2};

    public Board() {
        rand  = new Random();
        grid  = new Cell[SIZE][SIZE];
        ships = new Ship[SHIP_SIZES.length];
        initGrid();
        createShips();
        placeShips();
    }

    private void initGrid() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                grid[r][c] = new Cell();
    }

    private void createShips() {
        for (int i = 0; i < SHIP_SIZES.length; i++)
            ships[i] = new Ship(SHIP_SIZES[i]);
    }

    public void placeShips() {
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                if (horizontal) {
                    int row = rand.nextInt(SIZE);
                    List<Integer> validCols = new ArrayList<>();
                    for (int c = 0; c <= SIZE - ship.getSize(); c++) {
                        if (canPlace(row, c, ship.getSize(), true))
                            validCols.add(c);
                    }
                    if (!validCols.isEmpty()) {
                        int col = validCols.get(rand.nextInt(validCols.size()));
                        for (int i = 0; i < ship.getSize(); i++) {
                            grid[row][col + i].setShip(ship);
                        }
                        placed = true;
                    }
                } else {
                    int col = rand.nextInt(SIZE);
                    List<Integer> validRows = new ArrayList<>();
                    for (int r = 0; r <= SIZE - ship.getSize(); r++) {
                        if (canPlace(r, col, ship.getSize(), false))
                            validRows.add(r);
                    }
                    if (!validRows.isEmpty()) {
                        int row = validRows.get(rand.nextInt(validRows.size()));
                        for (int i = 0; i < ship.getSize(); i++) {
                            grid[row + i][col].setShip(ship);
                        }
                        placed = true;
                    }
                }
            }
        }
    }

    private boolean canPlace(int row, int col, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row       : row + i;
            int c = horizontal ? col + i   : col;
            if (r >= SIZE || c >= SIZE || grid[r][c].hasShip())
                return false;
        }
        return true;
    }

    public boolean fireAt(int row, int col) {
        Cell cell = grid[row][col];
        if (cell.hasShip()) {
            cell.getShip().registerHit();
            cell.setState(Cell.HIT);
            return true;
        } else {
            cell.setState(Cell.MISS);
            return false;
        }
    }

    public Ship getShipAt(int row, int col) {
        return grid[row][col].getShip();
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public Ship[] getShips() { return ships; }

    public boolean allShipsSunk() {
        for (Ship s : ships)
            if (!s.isSunk()) return false;
        return true;
    }

    public void printShipLocations() {
        System.out.println("=== SHIP LOCATIONS (row, col) ===");
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c].hasShip()) {
                    System.out.print("[" + r + "," + c + "] ");
                }
            }
        }
        System.out.println("\n=================================");
    }

    public void reset() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                grid[r][c].reset();
        for (Ship s : ships) s.reset();
        placeShips();
    }
}