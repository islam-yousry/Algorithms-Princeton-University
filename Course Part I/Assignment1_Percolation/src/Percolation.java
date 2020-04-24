import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int numOpenSites;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked // O(n*n).
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException();
        weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);
        grid = new boolean[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++)
                grid[i][j] = false;
        }
        this.numOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already // O(lgn).
    public void open(int row, int col){
        row--;col--;
        int n = grid.length;
        if(row < 0 || col < 0 || row > n-1 || col > n-1)
            throw new IllegalArgumentException();
        if(grid[row][col]) return;
        grid[row][col] = true;
        if (row > 0 && grid[row-1][col]) weightedQuickUnionUF.union(position(row,col),position(row-1,col));
        if (col > 0 && grid[row][col-1]) weightedQuickUnionUF.union(position(row,col),position(row,col-1));
        if (row < n-1 && grid[row+1][col]) weightedQuickUnionUF.union(position(row,col),position(row+1,col));
        if (col < n-1 && grid[row][col+1]) weightedQuickUnionUF.union(position(row,col),position(row,col+1));
        this.numOpenSites++;

        if(row == 0)
            weightedQuickUnionUF.union(col,n*n);
        else if(row == n-1)
            weightedQuickUnionUF.union(position(row,col),n*n+1);
    }

    private int position(int row, int col){ // O(1).
        int n = grid.length;
        return row*n+col;
    }

    // is the site (row, col) open? // O(1).
    public boolean isOpen(int row, int col){
        row--;col--;
        if(row < 0 || col < 0 || row > grid.length-1 || col > grid.length-1)
            throw new IllegalArgumentException();
        return grid[row][col];
    }

    // is the site (row, col) full? // O(lgn).
    public boolean isFull(int row, int col) {
        int n = grid.length;
        row--;
        col--;
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1)
            throw new IllegalArgumentException();
        return weightedQuickUnionUF.find(n*n) == weightedQuickUnionUF.find(position(row,col));
    }

        // returns the number of open sites // O(1).
    public int numberOfOpenSites(){
        return this.numOpenSites;
    }

    // does the system percolate? // O(lgn).
    public boolean percolates(){
        int n = grid.length;
        return weightedQuickUnionUF.find(n*n) == weightedQuickUnionUF.find(n*n+1); //O(lgn).
    }

    // test client (optional)
    public static void main(String[] args){

    }


}
