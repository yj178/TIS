package 표현_가능한_이진트리;

import java.util.Arrays;

public class 표현_가능한_이진트리 {
    public static void main(String[] args) {
        표현_가능한_이진트리 problems = new 표현_가능한_이진트리();
        long[] numbers = new long[]{7, 42, 5};
        System.out.println(Arrays.toString(problems.solution(numbers)));
    }

    static int len;

    public int[] solution(long[] numbers) {
        len = numbers.length;
        int[] answer = new int[len];
        for (int i = 0; i < len; i++) {
            StrInfo num = getNumber(numbers[i]);
            if (chk(0, num.l, num.s, '1')) answer[i] = 1;
            else answer[i] = 0;
        }

        return answer;
    }

    static public StrInfo getNumber(long number) {
        String num = Long.toBinaryString(number);
        long strLen = num.length();
        long l = 1;
        long s = 2;
        int dep = 1;
        while (l < strLen) {
            l += s;
            s *= 2;
            dep++;
        }
        String tmp = "";
        for(long i = 0; i < l - strLen; i++){
            tmp += "0";
        }

        return new StrInfo(dep, tmp.concat(num));
    }

    static public boolean chk(int dep, int max, String num, char head) {
        int m = num.length() / 2;
        char mid = num.charAt(m);

        if (head == '0' && mid == '1') return false;

        if (num.length() == 1) {
            if (mid == '1' && head == '0') return false;
            else return true;
        }

        boolean s = chk(dep + 1, max, num.substring(0, m), mid == '0' || head == '0' ? '0' : '1');
        if (!s) return false;
        boolean e = chk(dep + 1, max, num.substring(m + 1), mid == '0' || head == '0' ? '0' : '1');
        if (!e) return false;

        return true;
    }

    static class StrInfo {
        int l;
        String s;

        public StrInfo(int l, String s) {
            this.l = l;
            this.s = s;
        }
    }


}
