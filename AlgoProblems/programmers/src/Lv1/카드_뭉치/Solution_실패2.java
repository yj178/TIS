package Lv1.카드_뭉치;

public class Solution_실패2 {
    public static void main(String[] args) {
        Solution_실패2 problem = new Solution_실패2();
        System.out.println(problem.solution(new String[]{"a", "apple", "is"}, new String[]{"a", "apple"}, new String[]{"a", "apple", "is", "a", "apple"}));
    }

    public String solution(String[] cards1, String[] cards2, String[] goal) {
        int idx1 = 0;
        int idx2 = 0;
        for(String s : goal){
            int tmp1 = -1;
            for(int i = idx1; i < cards1.length; i++){
                if(!s.equals(cards1[i])) continue;
                tmp1 = i;
                break;
            }
            int tmp2 = -1;
            for(int i = idx2; i < cards2.length; i++){
                if(!s.equals(cards2[i])) continue;
                tmp2 = i;
                break;
            }

            if(tmp1 == -1 && tmp2 == -1) return "No";
            else if(tmp1 != -1 && tmp2 == -1) idx1 = tmp1;
            else if(tmp1 == -1 && tmp2 != -1) idx2 = tmp2;
            else {
                if(tmp1 <= tmp2) idx1 = tmp1;
                else idx2 = tmp2;
            }
        }

        return "Yes";
    }

}
