package main.helpers;

import main.resources.Encoder;
import main.resources.Stack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Helpers {
    public static List<String> getFormattedOutput(List<String> clauses, Encoder encoder, String sequence) {
        List<String> formattedOutput = new LinkedList<>();
        int hardClausesWeight = 1;

        for (String clause : clauses) {
            if (!clause.contains(" ")) {
                int weight = encoder.getWeightOfClause(clause, sequence);
                formattedOutput.add(weight + " " + clause + " 0");
                hardClausesWeight += weight;
            }
        }

        for (String clause : clauses) {
            if (clause.contains(" ")) { // This is a clause for a stack i,j, i + 1, j - 1
                formattedOutput.add(hardClausesWeight + " " + clause + " 0");
            }
        }

        String satHeader = "p wcnf " + encoder.getStackMap().size() + " " + clauses.size() + " " + hardClausesWeight;
        formattedOutput.add(0, satHeader);

        return formattedOutput;
    }
}
