package Lv2.점_찍기;

class P140107 {
    public static void main(String[] args) {
        P140107 problem = new P140107();

        int k = 2;
        int d = 4;
        System.out.println(problem.solution(k, d));

        k = 1;
        d = 5;
        System.out.println(problem.solution(k, d));

        k = 1;
        d = 3;
        System.out.println(problem.solution(k, d));

        k = 1;
        d = 6;
        System.out.println(problem.solution(k, d));

    }


    public long solution(int k, int d) {
        long answer = 0;
        for (long x = 0; x <= d; x += k) {
            long y = (long) Math.sqrt((long) Math.pow(d, 2) - (long) Math.pow(x, 2));
            answer += y / k + 1;
        }
        return answer;
    }

}
