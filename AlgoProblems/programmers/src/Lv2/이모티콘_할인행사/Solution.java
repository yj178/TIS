package Lv2.이모티콘_할인행사;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        int[][] users = {{40, 10000}, {25, 10000}};
        int[] emoticons = {7000, 9000};
        Solution problem = new Solution();
        System.out.println(Arrays.toString(problem.solution(users, emoticons)));
    }

    static int[][] userInfo;
    static int[] emoInfo, salesInfo, salesState;
    static int len, ulen, tmpSub, tmpAmount;

    public int[] solution(int[][] users, int[] emoticons) {
        userInfo = users;
        ulen = userInfo.length;
        len = emoticons.length;
        emoInfo = emoticons;
        salesInfo = new int[]{10, 20, 30, 40};
        salesState = new int[len];
        dfs(0);
        int[] answer = new int[2];
        answer[0] = tmpSub;
        answer[1] = tmpAmount;
        return answer;
    }

    static void dfs(int dep) {
        if (dep == len) {
            purchase();
            return;
        }

        for (int i = 0; i < 4; i++) {
            salesState[dep] = salesInfo[i];
            dfs(dep + 1);
        }
    }

    static void purchase() {
        int sub = 0, sales = 0;
        for (int i = 0; i < ulen; i++) {
            int tmp = 0;
            for (int j = 0; j < len; j++) {
                if (salesState[j] >= userInfo[i][0]) tmp += emoInfo[j] * (100 - salesState[j]) / 100;
                if (tmp >= userInfo[i][1]) break;
            }
            if (tmp >= userInfo[i][1]) sub++;
            else sales += tmp;
        }
        if (tmpSub < sub) {
            tmpSub = sub;
            tmpAmount = sales;
        } else if (tmpSub == sub) {
            tmpAmount = tmpAmount < sales ? sales : tmpAmount;
        }
    }

}
