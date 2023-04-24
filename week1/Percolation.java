import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private final int n;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;
    private int numOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        this.grid = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n*n + 2); // 2 virtual nodes
        this.ufWithoutBottom = new WeightedQuickUnionUF(n*n + 1); // no virtual bottom node
        this.numOpenSites = 0;
        for (int i = 1; i <= n; i++) { // connect top row to virtual top node
            uf.union(0, getIndex(1, i));
            ufWithoutBottom.union(0, getIndex(1, i));
        }
        for (int i = 1; i <= n; i++) { // connect bottom row to virtual bottom node
            uf.union(n*n + 1, getIndex(n, i));
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numOpenSites++;

            int index = getIndex(row, col);

            if (row > 1 && isOpen(row - 1, col)) { // connect to open site above
                uf.union(index, getIndex(row - 1, col));
                ufWithoutBottom.union(index, getIndex(row - 1, col));
            }
            if (row < n && isOpen(row + 1, col)) { // connect to open site below
                uf.union(index, getIndex(row + 1, col));
                ufWithoutBottom.union(index, getIndex(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) { // connect to open site to the left
                uf.union(index, getIndex(row, col - 1));
                ufWithoutBottom.union(index, getIndex(row, col - 1));
            }
            if (col < n && isOpen(row, col + 1)) { // connect to open site to the right
                uf.union(index, getIndex(row, col + 1));
                ufWithoutBottom.union(index, getIndex(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int index = getIndex(row, col);
        return ufWithoutBottom.find(index) == ufWithoutBottom.find(0);
    }

    public int numberOfOpenSites() {
        return numOpenSites;
    }

    public boolean percolates() {
        if (n == 1) {
            return isOpen(1, 1);
        }
        if (n == 2) {
            return isOpen(1, 1) && isOpen(2, 1) || isOpen(1, 2) && isOpen(2, 2);
        }
        return uf.find(0) == uf.find(n*n + 1);
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row or column index out of bounds");
        }
    }
}