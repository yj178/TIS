package Lv1.카드_뭉치;

import java.util.HashMap;

public class Solution_실패 {
    public static void main(String[] args) {
        Solution_실패 problem = new Solution_실패();
        System.out.println(problem.solution(new String[]{"a", "apple", "is"}, new String[]{"a", "apple"}, new String[]{"a", "apple", "is", "a", "apple"}));
    }

    public String solution(String[] cards1, String[] cards2, String[] goal) {
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();

        for (int i = 0; i < cards1.length; i++) {
            map1.put(cards1[i], i);
        }
        for (int i = 0; i < cards2.length; i++) {
            map2.put(cards2[i], i);
        }

        int idx1 = -1;
        int idx2 = -1;
        for (String s : goal) {
            if (!map1.isEmpty() && map1.containsKey(s) && idx1 < map1.get(s)) {
                idx1 = map1.get(s);
                map1.remove(s);
            } else if (!map2.isEmpty() && map2.containsKey(s) && idx2 < map2.get(s)) {
                idx2 = map2.get(s);
                map2.remove(s);
            } else {
                return "No";
            }
        }

        return "Yes";
    }
}
