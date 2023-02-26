package Lv1.삼총사;

public class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        System.out.println(problem.solution(new int[]{-2,3,0,2,-5}));
    }
    static int len, ans;
    static int[] n;

    public int solution(int[] number) {
        n = number;
        len = number.length;
        dfs(0, 0, 0);
        return ans;
    }

    static void dfs(int dep, int sum, int idx){
        if(dep == 3) {
            if(sum == 0) ans++;
        }

        for(int i = idx; i < len; i++){
            dfs(dep + 1, sum + n[i], i + 1);
        }
    }
}
