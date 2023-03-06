package Lv1.카드_뭉치;

public class Solution_실패3 {
    public static void main(String[] args) {
        Solution_실패3 problem = new Solution_실패3();
        System.out.println(problem.solution(new String[]{"a", "apple", "is"}, new String[]{"a", "apple"}, new String[]{"a", "apple", "is", "a", "apple"}));
    }

    static int len, len1, len2;
    static String[] c1, c2;
    static String ans, goals;

    public String solution(String[] cards1, String[] cards2, String[] goal) {
        len = goal.length;
        len1 = cards1.length;
        len2 = cards2.length;
        goals = "";
        for (String g : goal) {
            goals += g;
        }
        c1 = cards1;
        c2 = cards2;
        ans = "No";
        dfs(0, 0, 0, "");
        return ans;
    }

    static void dfs(int dep, int idx1, int idx2, String tmp) {
        if (ans.equals("Yes")) return;
        if (dep > len) return;

        if (dep == len) {
            System.out.println(tmp);
            if (goals.equals(tmp)) ans = "Yes";
            return;
        }

        if (idx1 < len1) {
            dfs(dep + 1, idx1 + 1, idx2, tmp + c1[idx1]);
            dfs(dep , idx1 + 1, idx2, tmp);
        }

        if (idx2 < len2) {
            dfs(dep + 1, idx1, idx2 + 1, tmp + c2[idx2]);
            dfs(dep , idx1, idx2 + 1, tmp);
        }
    }


}
