import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    // the number of trials
    private int numTrial;
    // the percentage of opening sites over all sites for all trials
    private double[] trialsResult;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("either N or the number of trials must be <= 0");
        }
        trialsResult = new double[trials];
        numTrial = trials;

        for (int trial = 0; trial < numTrial; trial++) {
            Percolation percolation = new Percolation(n);
            // keep opening until percolates
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            double result = (double) percolation.numberOfOpenSites() / (n * n);
            trialsResult[trial] = result;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialsResult);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialsResult);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(numTrial));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(numTrial));
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridSize = 250;
        int numTrial = 200;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            numTrial = Integer.parseInt(args[1]);
        }

        PercolationStats res = new PercolationStats(gridSize, numTrial);
        StdOut.println("mean                    = " + res.mean());
        StdOut.println("stddev                  = " + res.stddev());
        StdOut.println("95% confidence interval = [" + res.confidenceLo() + ", " + res.confidenceHi() + "]");
    }
}
