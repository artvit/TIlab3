package by.bsu.ti.lab3.util;

import by.bsu.ti.lab3.action.Source;
import by.bsu.ti.lab3.action.Target;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static final String filename = "input.txt";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Path path = Paths.get(Main.class.getResource(filename).toURI());
        List<String> strings = Files.readAllLines(path);
        Target target = new Target(Integer.parseInt(strings.get(0)), Integer.parseInt(strings.get(1)));
        int L = Integer.parseInt(strings.get(2));
        Source source = new Source(Integer.parseInt(strings.get(2)),
                strings.subList(3, 3 + L)
                        .stream()
                        .mapToDouble(Double::parseDouble)
                        .toArray());

    }
}
