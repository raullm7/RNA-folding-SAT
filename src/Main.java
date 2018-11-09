import main.helpers.Helpers;
import main.resources.Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);

        Scanner sc = new Scanner(file);
        String sequence = sc.nextLine();

        Encoder encoder = new Encoder(sequence, 2);
        List<String> clauses = encoder.getClauses();

//        encoder.printVariablesMap();

        String outputFilePath = "outputs/output";
        Path outputFile = Paths.get(outputFilePath);

        String solverOutputFilePath = "outputs/solverOutput";

        List<String> formattedOutput = Helpers.getFormattedOutput(clauses, encoder.getVariablesMapSize());

        Files.write(outputFile, formattedOutput, Charset.forName("UTF-8"));

        String[] command = { "/Users/raul/open-wbo/open-wbo", outputFilePath, solverOutputFilePath };

        Process process = Runtime.getRuntime().exec(command);

        BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String processLine = lineReader.readLine();
        String solution = "";

        while (processLine != null) {
            System.out.println(processLine);
            if (processLine.charAt(0) == 'v') solution = processLine.substring(2);
            processLine = lineReader.readLine();
        }

        System.out.println("---------------------------------------");
        System.out.println("SOLUTION: ");

        String[] positiveEvaluations = solution.split(" ");
        Arrays.stream(positiveEvaluations).filter(s -> s.charAt(0) != '-').forEach(elem -> encoder.printVariablesMap(Integer.valueOf(elem)));
    }
}
