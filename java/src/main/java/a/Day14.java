package a;

import java.util.HashMap;
import java.util.Map;

public class Day14 {

    public static void main(String[] args) {
        System.out.println(1);
    }

    /**
     * Returns resulting polymer after applying pair insertion rules to p
     */
    public static String growPolymer(String p, Map<String, Character> rules, int cycles) {
        String newP = p;
        for (int i = 0; i < cycles; i++) {
            System.out.println("Starting cycle : " + i);
            StringBuilder x = new StringBuilder(newP.substring(0, 1));
            for (int j = 0; j < newP.length() - 1; j++) {
                String pair = newP.substring(j, j + 2);
                x.append(rules.get(pair)).append(pair.charAt(1));
            }
            newP = x.toString();
        }
        return newP;
    }

    static class Input {

        String template;

        Map<String, Character> rules;

        public Input(String template, Map<String, Character> rules) {
            this.template = template;
            this.rules = rules;
        }

        static Input parse(String s) {
            String tmpl = "";
            Map<String, Character> rules = new HashMap<>();

            for (String line : s.split("\n")) {
                if (line.contains(" -> ")) {
                    String[] split = line.split(" -> ");
                    rules.put(split[0], split[1].charAt(0));
                } else if (!line.isEmpty()) {
                    tmpl = line;
                }
            }

            return new Input(tmpl, rules);
        }

        @Override
        public String toString() {
            return "Input{" +
                    "template='" + template + '\'' +
                    ", rules=" + rules +
                    '}';
        }
    }

    static class Count {
        Map<Character, Integer> children = new HashMap<>();

        public Count(String s) {
            for (char c : s.toCharArray()) {
                children.put(c, children.getOrDefault(c, 0) + 1);
            }
        }

        int occurences(char c) {
            return children.get(c);
        }

        @Override
        public String toString() {
            return "Count{" +
                    "children=" + children +
                    '}';
        }

        int max() {
            return children.values().stream().max(Integer::compare).get();
        }

        int min() {
            return children.values().stream().min(Integer::compare).get();
        }
    }
}
