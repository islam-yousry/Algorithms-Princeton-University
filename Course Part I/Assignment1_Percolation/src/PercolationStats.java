import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double mean;
    private double stddev;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(trials < 0)
            throw new IllegalArgumentException();
        int count = 0;
        this.trials =  trials;
        double [] x = new double[trials];
        while (count++ < trials) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row, col;
                do {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                } while (percolation.isOpen(row, col));
                percolation.open(row, col);
            }
            x[trials-1] = percolation.numberOfOpenSites()/n*n;

        }
        this.mean = StdStats.mean(x);
        this.stddev = StdStats.stddev(x);

    }

    // sample mean of percolation threshold
    public double mean(){
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean-1.96*stddev/(Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean+1.96*stddev/(Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args){

    }
}
