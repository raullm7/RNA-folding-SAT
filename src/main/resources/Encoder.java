package main.resources;

import java.util.*;

public class Encoder {
    private String sequence;
    private List<String> clauses;
    private Map<Integer, Stack> stackMap;
    private int minDistance;

    public Encoder(String sequence) {
        this.sequence = sequence.toLowerCase();
        this.clauses = new ArrayList<>();
        this.stackMap = new HashMap<>();
        this.minDistance = getMinWindowSize(sequence.length());
    }

    public List<String> getClauses() {
        createStackMap();
        encode();
        printStackMap();
        return clauses;
    }

    private void createStackMap() {
        int numberOfVariables = 1, n = sequence.length();
        List au = new ArrayList(2);
        au.add('a'); au.add('u');

        List cg = new ArrayList(2);
        cg.add('c'); cg.add('g');

        for (int i = 0; i < n; i++) {
            for (int j = i + minDistance; j < n; j++) {
                char first = sequence.charAt(i);
                char second = sequence.charAt(j);
                char third = sequence.charAt(i + 1);
                char fourth = sequence.charAt(j - 1);

                boolean outerValid = first != second && (au.contains(first) ? au.contains(second) : cg.contains(second));
                boolean innerValid = third != fourth && (au.contains(third) ? au.contains(fourth) : cg.contains(fourth));

                if (j - i >= minDistance && outerValid && innerValid) {
                    stackMap.put(numberOfVariables++, new Stack(i, j, i + 1, j -1));
                }
            }
        }
    }

    private void encode() {
        for (int i = 1; i <= stackMap.size(); i++) {
            // Main clauses: Pairs which could form a folding in the sequence.
            clauses.add("" + i);

            for (int j = i + 1; j <= stackMap.size(); j++) {
                // Constraint 1: Every position i can at most pair with one position j.
                if (stackMap.get(i).inSamePositionAs(stackMap.get(j))) {
                    clauses.add("-" + i + " -" + j);
                }

                // Constraint 2: No pseudoknots.
                else if (stackMap.get(i).outer.formsPseudoknotWith(stackMap.get(j).outer)) {
                    clauses.add("-" + i + " -" + j);
                }
            }
        }
    }

    public int getWeightOfClause(String clause, String sequence) {
        int index = Integer.parseInt(clause);
        return stackMap.get(index).getEnergy(sequence);
    }

    public Map<Integer, Stack> getStackMap() {
        return stackMap;
    }

    private int getMinWindowSize (int sequenceLength) {
        if (sequenceLength <= 50)
            return 2;
        if (sequenceLength <= 120)
            return 3;
        if (sequenceLength <= 300)
            return 5;
        if (sequenceLength <= 500)
            return 8;
        if (sequenceLength <= 800)
            return 12;
        if (sequenceLength <= 1200)
            return 15;
        if (sequenceLength <= 2000)
            return 20;
        return 25;
    }

    public void printStackMap() {
        stackMap.entrySet()
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public void printStackMap(int i) {
        System.out.println(i + ": " + stackMap.get(i));
    }
}
