package Lv2.마법의_엘리베이터;

class Solution {
    public static void main(String[] args) {
        Solution test = new Solution();
        System.out.println(test.solution(16));
    }

    static int num, len, ans;
    static char[] nums;

    public int solution(int storey) {
        nums = ("0" + storey).toCharArray();
        len = nums.length - 1;
        num = storey;
        return dfs(1) + ans;
//        return ans + dfs(1); 참고로 이건 0 | 1만 나옴
    }

    static int dfs(int dep) {
        if (dep == len) {
            ans += calcCnt(eachNum(dep));
            return calcUp(eachNum(dep), eachNum(dep - 1));
        }

        int now = eachNum(dep) + dfs(dep + 1);
        ans += calcCnt(now % 10);
        return calcUp(now, eachNum(dep - 1));
    }

    static int calcUp(int num, int ex) {
        if (num < 5) return 0;
        else if (num > 5) return 1;
        else {
            if (ex < 5) return 0;
            else return 1;
        }
    }

    static int calcCnt(int num) {
        if (num <= 5) return num;
        else return 10 - num;
    }

    static int eachNum(int dep) {
        return nums[dep] - '0';
    }
}
