package by.bsu.ti.lab3.action;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
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

    public static List<Pair<List<Integer>, List<Integer>>> matchTargetWords(List<List<Integer>> words, Target target) {
        List<List<Integer>> targetWords = generateTargetWords(target);
        List<Pair<List<Integer>, List<Integer>>> result = new ArrayList<>();
        return matchWords(words, targetWords);
    }

    public static List<Pair<List<Integer>, List<Integer>>> matchWords(List<List<Integer>> words, List<List<Integer>> targetWords) {
        List<Pair<List<Integer>, List<Integer>>> result = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            result.add(new MutablePair<>(words.get(i), targetWords.get(i)));
        }
        return result;
    }

    public static List<List<Integer>> generateTargetWords(Target target) {
        List<List<Integer>> targetWords = new ArrayList<>();
        for (int i = 0; i < (int) Math.pow(target.getAlphabetSize(), target.getWordLength()); i++) {
            List<Integer> word = new ArrayList<>();
            for (int j = 0; j < target.getWordLength(); j++) {
                int t = (int) Math.pow(target.getAlphabetSize(), j);
                word.add(i / t % target.getAlphabetSize());
            }
            Collections.reverse(word);
            targetWords.add(word);
        }
        return targetWords;
    }

    public static double countAvgSourceSymbolOnTargetSymbol(double avgSourceLength, Target target) {
        return target.getWordLength() / avgSourceLength;
    }

    public static double countAvgLowerBound(Source source, Target target) {
        return countEntropy(source) / (Math.log(target.getAlphabetSize()) / Math.log(2));
    }

    private static double countEntropy(Source source) {
        return -Arrays.stream(source.getProbabilities()).map(x -> x * Math.log(x) / Math.log(2)).sum();
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
