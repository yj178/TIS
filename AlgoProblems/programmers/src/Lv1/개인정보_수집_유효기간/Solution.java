package Lv1.개인정보_수집_유효기간;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class Solution {
    static void main(String[] args) {
        Solution problem = new Solution();
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};
        System.out.println(Arrays.toString(problem.solution(today, terms, privacies)));
    }

    static final int YEARTODAY = 336;
    static final int MONTODAY = 28;
    static int todayInt;
    static HashMap<String, Integer> map;

    public int[] solution(String today, String[] terms, String[] privacies) {
        todayInt = calcToday(today);
        map = new HashMap<>();
        for (String term : terms) {
            String[] tmp = term.split(" ");
            map.put(tmp[0], Integer.parseInt(tmp[1]));
        }

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < privacies.length; i++) {
            String[] tmp = privacies[i].split(" ");
            if(calcToday(tmp[0]) + map.get(tmp[1]) * MONTODAY <= todayInt) ans.add(i+1);
        }

        int[] answer = new int[ans.size()];
        for(int i = 0; i < ans.size(); i++){
            answer[i] = ans.get(i);
        }

        return answer;
        // return ans.stream().sorted().mapToInt(Integer::intValue).toArray();
    }

    public static int calcToday(String today) {
        String[] tmp = today.split("\\.");
        return YEARTODAY * Integer.parseInt(tmp[0]) + MONTODAY * Integer.parseInt(tmp[1]) + Integer.parseInt(tmp[2]);
    }
}
