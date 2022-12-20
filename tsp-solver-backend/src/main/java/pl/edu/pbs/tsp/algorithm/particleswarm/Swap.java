package pl.edu.pbs.tsp.algorithm.particleswarm;

public class Swap {
    int i;
    int j;

    public Swap(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getFirstIndex() {
        return this.i;
    }

    public int getSecondIndex() {
        return this.j;
    }
}