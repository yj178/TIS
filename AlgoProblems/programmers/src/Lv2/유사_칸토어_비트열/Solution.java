package Lv2.유사_칸토어_비트열;

/*
칸토어 집합 : 0,1 사이의 실수로 이루어진 집합, 각 구간을 3등분하여 가운데 구간을 반복적으로 제외하는 방식으로 만듬
n번째 비트수가 5^n임 n이 20인 경우 너무 문자열이 가능하더라도 탐색하는데 너무 긴 시간이 소요될 듯?
아무리 생각해도 당장은 완전탐색하는 방법만 떠오름...

1 // 1 : 1
11011 // 1 : 4, 0 : 1
11011 11011 00000 11011 11011 // 1 : 16, 0 : 9
11011 11011 00000 11011 11011 11011 11011 00000 11011 11011 00000 00000 00000 00000 00000 11011 11011 00000 11011 11011 11011 11011 00000 11011 11011
1     2     3     4     5     6     7     8     9     10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25
1 : 64, 0 : 45 + 16 = 61

패턴이 추가 될 뿐 기존 위치는 동일함
1의 개수는 4^n개

 */
class Solution {
    public static void main(String[] args) {
        Solution test = new Solution();
//        int n = 2;
//        int l = 4;
//        int r = 17;       // ans 8

//        int n = 4;
//        int l = 30;
//        int r = 118;         // ans 39

        int n = 5;
        int l = 1;
        int r = 125;         // ans 64
        System.out.println(test.solution(n, l, r));
    }


    public int solution(int n, long l, long r) {
        return (int) (cntOne(r) - cntOne(l - 1));
    }

    static long cntOne(long num) {
        if (num < 5) {
            return num < 3 ? num : num - 1;
        }
        int exp = 0;
        long divisor = 5;
        while (num > divisor) {
            divisor *= 5;
            exp++;
        }
        divisor /= 5;

        long quotient = num / divisor;
        long remainder = num % divisor;

        long oneCnt = calcResult(exp) * (quotient > 2 ? quotient - 1 : quotient);

        if (quotient == 2)
            return oneCnt;
        else
            return oneCnt + cntOne(remainder);
    }

    static long calcResult(int exp) {
        long num = 1;
        for (int idx = 0; idx < exp; idx++) {
            num *= 4L;
        }
        return num;
    }

}
