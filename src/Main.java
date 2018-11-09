import main.helpers.Helpers;
import main.resources.Encoder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
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

        ProcessBuilder pb = new ProcessBuilder("/Users/raul/open-wbo/open-wbo", outputFilePath, solverOutputFilePath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String processLine, solution = "";
        while ((processLine = lineReader.readLine()) != null) {
            System.out.println(processLine);
            if (processLine.charAt(0) == 'v') solution = processLine.substring(2);
        }
        process.waitFor();

        System.out.println("---------------------------------------");

        String[] positiveEvaluations = solution.split(" ");
        Arrays.stream(positiveEvaluations).filter(s -> s.charAt(0) != '-').forEach(elem -> encoder.printVariablesMap(Integer.valueOf(elem)));
    }
}
