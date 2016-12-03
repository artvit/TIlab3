package by.bsu.ti.lab3.util;

import by.bsu.ti.lab3.action.Source;
import by.bsu.ti.lab3.action.Target;
import by.bsu.ti.lab3.action.TunstallProcessor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static final String filename = "input.txt";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Path path = Paths.get(Main.class.getResource("/" + filename).toURI());
        List<String> strings = Files.readAllLines(path);
        Target target = new Target(Integer.parseInt(strings.get(0)), Integer.parseInt(strings.get(1)));
        int L = Integer.parseInt(strings.get(2));
        Source source = new Source(Integer.parseInt(strings.get(2)),
                strings.subList(3, 3 + L)
                        .stream()
                        .mapToDouble(Double::parseDouble)
                        .toArray());
        int iterations = TunstallProcessor.countIterations(source, target);
        Pair<Double, List<List<Integer>>> tunstallResults = TunstallProcessor.getWordsAndAvgLength(source, iterations);
        List<Pair<List<Integer>, List<Integer>>> pairs = TunstallProcessor.matchTargetWords(tunstallResults.getRight(), target);
        pairs.forEach(x -> System.out.printf("%20s-----------%s\n", x.getLeft(), x.getRight()));
        double K = tunstallResults.getLeft();
        System.out.println("K = " + K);
        double W = TunstallProcessor.countAvgSourceSymbolOnTargetSymbol(K, target);
        System.out.println("W = " + W);
//        TODO
        double lowerBoundW = TunstallProcessor.countAvgLowerBound();
        System.out.println("W lower bound = " + lowerBoundW);
    }
}
