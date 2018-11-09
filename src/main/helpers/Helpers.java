package main.helpers;

import java.util.ArrayList;
import java.util.List;

public class Helpers {
    public static List<String> getFormattedOutput(List<String> clauses, int variablesMapSize) {
        List<String> formattedOutput = new ArrayList<>();
        int hardClausesWeight = variablesMapSize + 1;
        int softClausesWeight = 1;

        String satHeader = "p wcnf " + variablesMapSize + " " + clauses.size() + " " + hardClausesWeight;
        formattedOutput.add(satHeader);

        for (String clause : clauses) {
            if (!clause.contains(" ")) {
                formattedOutput.add(softClausesWeight + " " + clause + " 0");
            } else {  // This is a clause for a link i,j
                formattedOutput.add(hardClausesWeight + " " + clause + " 0");
            }
        }

        return formattedOutput;
    }
}
