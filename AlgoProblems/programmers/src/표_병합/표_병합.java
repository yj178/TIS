package 표_병합;

import java.util.*;

public class 표_병합 {
    public static void main(String[] args) {
        표_병합 problem = new 표_병합();
//        String[] commands = new String[]{"UPDATE 1 1 menu", "UPDATE 1 2 category", "UPDATE 2 1 bibimbap", "UPDATE 2 2 korean", "UPDATE 2 3 rice", "UPDATE 3 1 ramyeon", "UPDATE 3 2 korean", "UPDATE 3 3 noodle", "UPDATE 3 4 instant", "UPDATE 4 1 pasta", "UPDATE 4 2 italian", "UPDATE 4 3 noodle", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "UPDATE korean hansik", "UPDATE 1 3 group", "UNMERGE 1 4", "PRINT 1 3", "PRINT 1 4"};
        String[] commands = new String[]{"UPDATE 1 1 a", "UPDATE 1 2 b", "UPDATE 2 1 c", "UPDATE 2 2 d", "MERGE 1 1 1 2", "MERGE 2 2 2 1", "MERGE 2 1 1 1", "PRINT 1 1", "UNMERGE 2 2", "PRINT 1 1"};
        System.out.println(Arrays.toString(problem.solution(commands)));
    }

    static String[][] table;
    static int[][] heads;
    static HashMap<String, HashSet<Integer>> map;

    public String[] solution(String[] commands) {
        table = new String[51][51];
        heads = new int[51][51];
        for (int r = 1; r < 51; r++) {
            for (int c = 1; c < 51; c++) {
                heads[r][c] = (r - 1) * 50 + c;
            }
        }
        map = new HashMap<>();
        List<String> answer = new LinkedList<>();
        for (String command : commands) {
            String[] line = command.split(" ");
            switch (line[0]) {
                case "UPDATE":
                    if (line.length == 4) update(Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3]);
                    else update(line[1], line[2]);
                    break;
                case "MERGE":
                    merge(Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]));
                    break;
                case "UNMERGE":
                    unmerge(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    break;
                case "PRINT":
                    answer.add(print(Integer.parseInt(line[1]), Integer.parseInt(line[2])));
                    break;
            }
//            System.out.println();
//            System.out.println(command);
//            viewHead();
//            viewTable();
        }

        return answer.stream().toArray(String[]::new);
    }

    static int find(int r, int c) {
        if (heads[r][c] == (r - 1) * 50 + c) return heads[r][c];

        int head = heads[r][c];
        return heads[r][c] = find(head / 50 + 1, head % 50);
    }

    static boolean union(int r1, int c1, int r2, int c2) {
        int a = find(r1, c1);
        int b = find(r2, c2);
        if (a == b) return false;
        else {
            heads[r2][c2] = heads[r1][c1];
            return true;
        }
    }

    static void viewHead() {
        for (int r = 1; r < 3; r++) {
            for (int c = 1; c < 3; c++) {
                System.out.printf("%5d", heads[r][c]);
            }
            System.out.println();
        }
    }

    static void viewTable() {
        for (int r = 1; r < 3; r++) {
            for (int c = 1; c < 3; c++) {
                System.out.printf("%10s", table[r][c]);
            }
            System.out.println();
        }
    }

    // r, c 위치의 셀을 value로 업데이트
    static void update(int r, int c, String value) {
        int idx = find(r, c);
        int nr = idx / 50 + 1;
        int nc = idx % 50;
        HashSet<Integer> tmp = map.getOrDefault(table[nr][nc], new HashSet<>());
        if (table[nr][nc] == null) {
            tmp.add(idx);
        } else {
            tmp.remove(idx);
            tmp = map.getOrDefault(value, new HashSet<>());
            tmp.add(idx);
        }
        map.put(value, tmp);
        table[nr][nc] = value;
    }

    // v1 값을 가지고 있는 셀들을 모두 v2로 업데이트
    static void update(String v1, String v2) {
        HashSet<Integer> s1 = map.getOrDefault(v1, new HashSet<>());
        HashSet<Integer> s2 = map.getOrDefault(v2, new HashSet<>());

        for (int s : s1) {
            s2.add(s);
        }

        map.put(v1, new HashSet<>());
        map.put(v2, s2);
    }

    // r1,c1과 r2, c2 를 병합
    // 같은 위치인 경우 무시, 인접하지 않은 경우 사이 셀들은 무시
    // 두 셀 중 하나의 셀만 값을지고 있는 경우 해당 값으로 병합된 셀의 값을 가짐
    // 두셀 모두 값이 있는 경우 r1, c1값으로 통일
    // 이후 r1, c1, 혹은 r2,c2 어느 위치여도 병합된 셀로 접근
    static void merge(int r1, int c1, int r2, int c2) {
        if (r1 == r2 && c1 == c2) return;
        int idx1 = find(r1, c1);
        int idx2 = find(r2, c2);
        int nr1 = idx1/50+1;
        int nc1 = idx1%50;
        int nr2 = idx2 / 50 + 1;
        int nc2 = idx2 % 50;

        if (table[nr1][nc1] != null && table[nr2][nc2] != null) {
            update(nr2, nc2, null);
        } else if (table[nr1][nc1] == null && table[nr2][nc2] != null) {
            update(nr1, nc1, table[nr2][nc2]);
            update(nr2, nc2, null);
        }
        union(nr1, nc1, nr2, nc2);

    }

    // 해당 셀의 모든 병합 해제
    // 해당 셀이 포합하고 있던 모든 셀은 프로그램 실행 초기 상태?
    // 해제 전(합병된 상태?) 값이 있었다면 해당 값으로 복원 -> 이건 왜 적은 거지???
    static void unmerge(int r, int c) {
        int head = heads[r][c];
        String tmp = table[head / 50 + 1][head % 50];
        for (int rr = 1; rr < 51; rr++) {
            for (int cc = 1; cc < 51; cc++) {
                if (heads[rr][cc] == head) {
                    heads[rr][cc] = (rr - 1) * 50 + cc;
                    update(rr, cc, null);
                }
            }
        }
        update(r, c, tmp);
    }

    // 값이 비어 있다면 "EMPTY"를 있다면 해당 값을 출력
    static String print(int r, int c) {
        int idx = find(r, c);
        int nr = idx / 50 + 1;
        int nc = idx % 50;
        return table[nr][nc] == null ? "EMPTY" : table[nr][nc];
    }
}
