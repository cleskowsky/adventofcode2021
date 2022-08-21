package a;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {

    @Test
    void growPolymer() {
        Map<String, Character> rules = Map.of(
                "NN", 'C'
        );

        Map<String, String> testTable = Map.of(
                // Initial polymer, expected polymer after applying pair insertion rules
                "NN", "NCN",
                "NNN", "NCNCN"
        );
        testTable.forEach((initialPolymer, expected) -> {
            assertEquals(expected, Day14.growPolymer(initialPolymer, rules, 1).toString());
        });
    }


    @Test
    void parseInput() {
        String s = """
                NNCB
                                
                CH -> B
                HH -> N""";

        Day14.Input in = Day14.Input.parse(s);
        assertEquals("NNCB", in.template);
        assertEquals(2, in.rules.size());
        assertEquals('B', in.rules.get("CH"));
    }

    @Test
    void sample() {
        String s = """
                NNCB
                                
                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C""";
        Day14.Input in = Day14.Input.parse(s);
        String p = Day14.growPolymer(in.template, in.rules, 10);
        Day14.Count c = new Day14.Count(p);
        assertEquals(1588, c.max() - c.min());
    }

    @Test
    void partA() {
        String s = """
                HBHVVNPCNFPSVKBPPCBH
                                
                HV -> B
                KS -> F
                NH -> P
                OP -> K
                OV -> C
                HN -> O
                FF -> K
                CP -> O
                NV -> F
                VB -> C
                KC -> F
                CS -> H
                VC -> F
                HF -> V
                NK -> H
                CF -> O
                HH -> P
                FP -> O
                OH -> K
                NN -> C
                VK -> V
                FB -> F
                VP -> N
                FC -> P
                SV -> F
                NO -> C
                VN -> S
                CH -> N
                FN -> N
                FV -> P
                CN -> H
                PS -> S
                VF -> K
                BN -> S
                FK -> C
                BB -> H
                VO -> P
                KN -> N
                ON -> C
                BO -> S
                VS -> O
                PK -> C
                SK -> P
                KF -> K
                CK -> O
                PB -> H
                PF -> O
                KB -> V
                CC -> K
                OK -> B
                CV -> P
                PO -> O
                SH -> O
                NP -> F
                CO -> F
                SS -> P
                FO -> K
                NS -> O
                PN -> H
                PV -> V
                KP -> C
                BK -> B
                BP -> F
                NB -> C
                OF -> O
                OC -> O
                HO -> C
                SC -> K
                HC -> C
                HS -> B
                KH -> N
                FS -> N
                PH -> O
                PC -> V
                BS -> O
                KO -> F
                SP -> K
                OB -> O
                SF -> K
                KV -> F
                NC -> B
                SO -> C
                CB -> S
                VH -> V
                FH -> F
                SN -> V
                SB -> P
                PP -> B
                BF -> K
                HB -> O
                OO -> V
                HP -> H
                KK -> O
                BV -> K
                BH -> B
                HK -> H
                BC -> C
                VV -> S
                OS -> F
                NF -> B""";
        Day14.Input in = Day14.Input.parse(s);
        String p = Day14.growPolymer(in.template, in.rules, 10);
        Day14.Count c = new Day14.Count(p);
        System.out.println(c.max() - c.min());
    }

    @Test
    void counter() {
        String s= "ABBC";
        Day14.Count c = new Day14.Count(s);
        assertEquals(1, c.occurences('A'));
        assertEquals(2, c.occurences('B'));
    }
}