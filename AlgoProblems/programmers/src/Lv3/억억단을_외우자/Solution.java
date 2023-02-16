package Lv3.억억단을_외우자;

import java.util.Arrays;
import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        System.out.println(Arrays.toString(problem.solution(8, new int[]{1,3,7})));
    }

    static int[] cnts;
    static HashMap<Integer, Integer> map;
    public int[] solution(int e, int[] starts) {
        map = new HashMap<>();
        cnts = new int[e+1];
        int min =Integer.MAX_VALUE;
        for(int i = 0; i < starts.length; i++){
            min = Math.min(min, starts[i]);
            map.put(starts[i], i);
        }

        for(int i = e; i >= 1; i--){
            int num = i;
            while(num <= e){
                cnts[num]++;

                num += i;
            }
        }

        int max = 0;
        int maxNum = 0;
        int[] answer = new int[starts.length];
        for(int i = e; i >= min; i--){
            if(max <= cnts[i]) {
                max = cnts[i];
                maxNum = i;
            }
            if(map.containsKey(i)) answer[map.get(i)] = maxNum;
        }


        // System.out.println(Arrays.toString(cnts));
        return answer;
    }
}
