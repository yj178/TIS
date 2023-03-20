package Lv0.옹알이_1;

public class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        System.out.println(problem.solution(new String[]{"aya", "yee", "u", "maa", "wyeoo"}) == 1);
        System.out.println(problem.solution(new String[]{"ayaye", "uuuma", "ye", "yemawoo", "ayaa"}) == 3);
    }

    public int solution(String[] babbling) {
        String[] words= new String[]{"aya", "ye", "woo", "ma"};
        int cnt = 0;
        for(String b : babbling){
            int len = 0;
            for(int i = 0; i < 4; i++){
                if(b.contains(words[i])) len += words[i].length();
            }
            if(b.length() == len) cnt++;
        }
        return cnt;
    }
    
}
