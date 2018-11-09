package main.resources;

import java.util.*;

public class Encoder {
    private String sequence;
    private List<String> clauses;
    private Map<Integer, Pair> variablesMap;
    private int minDistance;

    public Encoder(String sequence, int minDistance) {
        this.sequence = sequence.toLowerCase();
        this.clauses = new ArrayList<>();
        this.variablesMap = new HashMap<>();
        this.minDistance = minDistance;
    }

    public List<String> getClauses() {
        createMap();
        encode();
        return clauses;
    }

    private void createMap() {
        int numberOfVariables = 1, n = sequence.length();
        List au = new ArrayList(2);
        au.add('a'); au.add('u');

        List cg = new ArrayList(2);
        cg.add('c'); cg.add('g');

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                char first = sequence.charAt(i);
                char second = sequence.charAt(j);
                boolean valid = au.contains(first) ? cg.contains(second) : au.contains(second);

                if (j - i >= minDistance && valid) {
                    variablesMap.put(numberOfVariables++, new Pair(i, j));
                }
            }
        }
    }

    private void encode() {
        for (int i = 1; i <= variablesMap.size(); i++) {
            // Main clauses: Pairs which could form a folding in the sequence.
            clauses.add("" + i);

            for (int j = i + 1; j <= variablesMap.size(); j++) {
                // Constraint 1: Every position i can at most pair with one position j.
                if (variablesMap.get(i).inSamePositionAs(variablesMap.get(j))) {
                    clauses.add("-" + i + " -" + j);
                }

                // Constraint 2: No pseudoknots.
                else if (variablesMap.get(i).formsPseudoknotWith(variablesMap.get(j))) {
                    clauses.add("-" + i + " -" + j);
                }
            }
        }
    }

    public int getVariablesMapSize() {
        return variablesMap.size();
    }

    public void printVariablesMap() {
        variablesMap.entrySet()
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
