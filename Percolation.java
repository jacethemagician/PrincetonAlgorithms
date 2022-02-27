import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // width of the 2-D array
    private int width;
    // total number of elements of the 2-D array
    private int size;
    // number of open sites, 0 for closes, 1 for open
    private int numOpen;
    // array marking site opening situation
    private int[] openSites;
    // Union-Find array
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        width = n;
        size = n * n;
        numOpen = 0;
        // array to store whether a site is open or not
        openSites = new int[size];
        // Union-find grid of all sites including two virtual sites at top and bottom
        uf = new WeightedQuickUnionUF(size + 2);

    }

    // check for out of bound
    private void checkBound(int row, int col) {
        if (row <= 0 || col > width) {
            throw new java.lang.IllegalArgumentException("row index out of bound");
        }
        if (col <= 0 || col > width) {
            throw new java.lang.IllegalArgumentException("col index out of bound");
        }
    }

    // convert 2-D array indices to 1-D array index
    private int convertIndex(int row, int col) {
        return (row - 1) * width + col - 1;
    }

    // connect two sites
    private void connectTwoSites(int row, int col, int index) {
        if (isOpen(row, col)) {
            uf.union(convertIndex(row, col), index);
        }
    }

    // connect all four sites, excluding top and bottom virtual sites
    private void connectFourSites(int row, int col) {
        int index = convertIndex(row, col);
        if (col < width) connectTwoSites(row, col + 1, index);
        if (col > 1) connectTwoSites(row, col - 1, index);
        if (row < width) {
            connectTwoSites(row + 1, col, index);
        } else {
            // in the last row, connect with bottom virtual site
            uf.union(index, size + 1);
        }
        if (row > 1) {
            connectTwoSites(row - 1, col, index);
        } else {
            // in the first row, connect with top virtual site
            uf.union(index, size);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBound(row, col);
        int index = convertIndex(row, col);
        openSites[index] = 1;
        numOpen++;
        connectFourSites(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBound(row, col);
        int index = convertIndex(row, col);
        return (openSites[index] == 1);
    }

    // is the site (row, col) full? meaning, if it is connected with top virtual site
    public boolean isFull(int row, int col) {
        checkBound(row, col);
        return uf.find(size) == uf.find(convertIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(size) == uf.find(size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(3, 3);
        boolean c = perc.isFull(1, 1);
        boolean c1 = perc.uf.connected(perc.convertIndex(1, 1), perc.convertIndex(2, 1));
        boolean c2 = perc.percolates();
        StdOut.println(c);
        StdOut.println(c1);
        StdOut.println(c2);
    }
}
