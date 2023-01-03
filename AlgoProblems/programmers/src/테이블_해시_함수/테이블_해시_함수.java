package 테이블_해시_함수;

import java.util.PriorityQueue;

/*
행은 튜플, 열은 컬럼
해시 함수는 col, row_begin, row_end 입력
내림차순 정렬 우선순위
1. col번째 값
2. 기본키

S_i : i번째 행의 튜플의 각 칼럼을 i로 나눈 후 나머지들을 모두 더한 값

row_begin <= i <= row_end인 모든 S_i를 누적하여 bitwise XOR 한 결과 반환


 */
class 테이블_해시_함수 {
    public static void main(String[] args) {
        테이블_해시_함수 problem = new 테이블_해시_함수();
        int[][] data = {{2, 2, 6}, {1, 5, 10}, {4, 2, 9}, {3, 8, 3}};
        int col = 2;
        int row_begin = 2;
        int row_end = 3;
        System.out.println(problem.solution(data, col, row_begin, row_end));
    }

    static PriorityQueue<Data> pq;
    static int len;

    public int solution(int[][] data, int col, int row_begin, int row_end) {
        pq = new PriorityQueue<>();
        for (int[] d : data) {
            pq.add(new Data(d, col));
        }
        len = pq.size();
        Integer ans = null;
        for (int idx = 1; idx <= row_end; idx++) {
            Data tmp = pq.poll();
            if (idx >= row_begin) {
                if (ans == null)
                    ans = tmp.calcRemainder(idx);
                else
                    ans ^= tmp.calcRemainder(idx);
            }
        }

        return ans;
    }

    static class Data implements Comparable<Data> {
        int[] d;
        int col;

        public Data(int[] d, int col) {
            this.d = d;
            this.col = col - 1;
        }

        public int calcRemainder(int i) {
            int sum = 0;
            for (int col : this.d) {
                sum += col % i;
            }
            return sum;
        }

        @Override
        public int compareTo(Data o) {
            return this.d[col] != o.d[col] ? Integer.compare(this.d[col], o.d[col]) : -Integer.compare(this.d[0], o.d[0]);
        }

    }
}
