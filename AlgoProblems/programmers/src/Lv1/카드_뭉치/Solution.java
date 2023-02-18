package Lv1.카드_뭉치;

public class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        System.out.println(problem.solution(new String[]{"a", "apple", "is"}, new String[]{"a", "apple"}, new String[]{"a", "apple", "is", "a", "apple"}));
    }

    public String solution(String[] cards1, String[] cards2, String[] goal) {
        int len1 = cards1.length;
        int len2 = cards2.length;
        int idx1 = 0;
        int idx2 = 0;

        for(String g : goal){
            if(idx1 < len1 && g.equals(cards1[idx1])) idx1++;
            else if(idx2 < len2 && g.equals(cards2[idx2])) idx2++;
            else return "No";
        }


        return "Yes";
    }
}
