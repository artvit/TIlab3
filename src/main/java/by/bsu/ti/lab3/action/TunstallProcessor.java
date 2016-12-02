package by.bsu.ti.lab3.action;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public final class TunstallProcessor {
    private TunstallProcessor() {}

    public static Pair<Double, List<List<Integer>>> getWordsAndAvgLength(Source source, int iterations) {
        PriorityQueue<TreeItem> queue = new PriorityQueue<>();
        double sumK = 1.0;
        addChildrenToQueue(new TreeItem(1.0, new ArrayList<>()), source, queue);
        for (int i = 0; i < iterations; i++) {
            TreeItem maxProbabilityItem = queue.poll();
            sumK += maxProbabilityItem.getProbability();
            addChildrenToQueue(maxProbabilityItem, source, queue);
        }
        return new MutablePair<>(sumK, queue.stream().map(TreeItem::getWord).collect(Collectors.toList()));
    }

    public static int countIterations(Source source, Target target) {
        return (int) Math.floor((Math.pow(target.getAlphabetSize(), target.getWordLength()) - source.getSize())
                                 / (source.getSize() - 1));
    }


    private static void addChildrenToQueue(TreeItem parent, Source source, PriorityQueue<TreeItem> queue) {
        for (int i = 0; i < source.getSize(); i++) {
            List<Integer> newWord = new ArrayList<>(parent.getWord());
            newWord.add(i);
            double newProbability = parent.getProbability() * source.getProbabilities()[i];
            queue.add(new TreeItem(newProbability, newWord));
        }
    }

    private static class TreeItem implements Comparable<TreeItem>{
        private double probability;
        private List<Integer> word;

        public TreeItem(double probability, List<Integer> word) {
            this.probability = probability;
            this.word = word;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }

        public List<Integer> getWord() {
            return word;
        }

        public void setWord(List<Integer> word) {
            this.word = word;
        }

        @Override
        public int compareTo(TreeItem o) {
            return Double.compare(o.getProbability(), this.getProbability());
        }
    }
}
