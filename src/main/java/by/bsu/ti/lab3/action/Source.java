package by.bsu.ti.lab3.action;

public class Source {
    private int size;
    private double[] probabilities;

    public Source(int size, double... probabilities) {
        this.size = size;
        this.probabilities = probabilities;
    }

    public Source(double... probabilities) {
        this.size = probabilities.length;
        this.probabilities = probabilities;
    }

    public int getSize() {
        return size;
    }

    public double[] getProbabilities() {
        return probabilities;
    }
}
