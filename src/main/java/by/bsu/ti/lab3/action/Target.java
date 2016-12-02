package by.bsu.ti.lab3.action;

public class Target {
    private int alphabetSize;
    private int wordLength;

    public Target(int alphabetSize, int wordLength) {
        this.alphabetSize = alphabetSize;
        this.wordLength = wordLength;
    }

    public int getAlphabetSize() {
        return alphabetSize;
    }

    public int getWordLength() {
        return wordLength;
    }
}
