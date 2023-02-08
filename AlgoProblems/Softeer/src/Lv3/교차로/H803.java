package Lv3.교차로;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class H803 {
    static int N;
    static Queue<Car> q;
    static Queue<Integer>[] roads;

    public static void main(String args[]) throws IOException {
        System.setIn(new FileInputStream("input/교차로_input"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);

        // 출력 형식은 n번째 차량이 몇초에 빠지는지를 나타냄
        int[] ans = new int[N];

        // 빠져나오지 못하는 차량은 -1이므로 -1로 초기화
        Arrays.fill(ans, -1);

        // 시작
        int time = 0;

        // 대기 차량이 없는 경우까지 수행
        while (!q.isEmpty() || !allEmpty()) {
            if(!q.isEmpty()) {
                // 교차로가 모두 비어 있고 교차로 진입 시기까지 시간이 남아 있다면 시간 업데이트
                if (allEmpty()) time = q.peek().t;

                // 현재 시간에 차량 교차로로 진입
                if (time == q.peek().t) {
                    Car tmp = q.poll();
                    roads[tmp.d].offer(tmp.i);
                    // 이때 q에 남아있는 차량이 현재 tmp차량과 같은 시간에 진입해야 한다면 모두 가져오기
                    while (!q.isEmpty() && tmp.t == q.peek().t) {
                        tmp = q.poll();
                        roads[tmp.d].offer(tmp.i);
                    }
                }
            }

            // 교착상태에 빠졌다면 종료
            if (allFull()) break;

            // 나갈 수 있는 교차로 확인 후 한번에 통과
            // 맞은편 같은 경우 동시에 빠져나갈 수 있음
            // 한 교차로씩 지나가면 입력과 꼬이는 경우가 발생할 수 있음???
            boolean[] chks = new boolean[4];
            for (int d = 0; d < 4; d++) {
                if (roads[d].isEmpty()) continue;
                if (emptyRight(d)) chks[d] = true;
            }

            // 지나갈 수 있는 교차로의 차량은 통과
            for (int d = 0; d < 4; d++) {
                if (chks[d]) ans[roads[d].poll()] = time;
            }

            // 시간 증가
            time++;
        }

        // 출력
        StringBuilder sb = new StringBuilder();

        for (int i : ans) {
            sb.append(i + "\n");
        }

        System.out.println(sb);

    }


    // 모든 교차로가 차 있어 교착상태에 빠지는 경우
    static boolean allFull() {
        if (!roads[0].isEmpty() && !roads[1].isEmpty() && !roads[2].isEmpty() && !roads[3].isEmpty()) return true;
        return false;
    }

    // 모든 교차로가 비어 있는 경우
    static boolean allEmpty() {
        if (roads[0].isEmpty() && roads[1].isEmpty() && roads[2].isEmpty() && roads[3].isEmpty()) return true;
        return false;
    }

    // 오른쪽 인덱스 획득
    static int rightIdx(int idx) {
        return idx - 1 < 0 ? 3 : idx - 1;
    }

    // 오른쪽이 비어 있는 경우 확인
    static boolean emptyRight(int idx) {
        int r = rightIdx(idx);
        if (roads[r].isEmpty()) return true;
        return false;
    }

    // 입력 처리
    static void init(BufferedReader br) throws IOException {
        // 차량 숫자 입력
        N = Integer.parseInt(br.readLine());

        // 전체차량 대기석
        q = new LinkedList<>();

        // 4방향 교차로 생성
        roads = new LinkedList[4];
        for (int d = 0; d < 4; d++) {
            roads[d] = new LinkedList<>();
        }

        // 전체차량이 대기하는 q에 모든 차량 입력
        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(" ");
            Car car = new Car(i, returnIdx(line[1]), Integer.parseInt(line[0]));
            q.offer(car);
        }
    }

    // 교차로 위치 숫자로 변환
    static int returnIdx(String s) {
        switch (s) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return -1;
        }
    }

    static class Car {
        int d, t, i;

        public Car(int i, int d, int t) {
            this.i = i;
            this.d = d;
            this.t = t;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "d=" + d +
                    ", t=" + t +
                    '}';
        }
    }
}
