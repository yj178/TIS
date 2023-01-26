package 표_병합;

import java.util.*;

public class 표_병합 {
    public static void main(String[] args) {
        표_병합 problem = new 표_병합();
        String[] commands = new String[]{"UPDATE 1 1 menu", "UPDATE 1 2 category", "UPDATE 2 1 bibimbap", "UPDATE 2 2 korean", "UPDATE 2 3 rice", "UPDATE 3 1 ramyeon", "UPDATE 3 2 korean", "UPDATE 3 3 noodle", "UPDATE 3 4 instant", "UPDATE 4 1 pasta", "UPDATE 4 2 italian", "UPDATE 4 3 noodle", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "UPDATE korean hansik", "UPDATE 1 3 group", "UNMERGE 1 4", "PRINT 1 3", "PRINT 1 4"};
//        String[] commands = new String[]{"UPDATE 1 1 a", "UPDATE 1 2 b", "UPDATE 2 1 c", "UPDATE 2 2 d", "MERGE 1 1 1 2", "MERGE 2 2 2 1", "MERGE 2 1 1 1", "PRINT 1 1", "UNMERGE 2 2", "PRINT 1 1"};
//        String[] commands = new String[]{"PRINT 1 1"};

        System.out.println(Arrays.toString(problem.solution(commands)));
    }

    static String[][] table;
    static int[][] heads;

    public String[] solution(String[] commands) {
        table = new String[51][51];
        heads = new int[51][51];
        for (int r = 1; r < 51; r++) {
            for (int c = 1; c < 51; c++) {
                heads[r][c] = (r - 1) * 50 + c;
            }
        }

        List<String> answer = new ArrayList<>();
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
        }

        return answer.toArray(String[]::new);
    }

    static int find(int r, int c) {
        if (heads[r][c] == ((r - 1) * 50) + c) return heads[r][c];

        int head = heads[r][c];
        return heads[r][c] = find(head / 50 + 1, head % 50);
    }

    static void update(int r, int c, String value) {
        int head = find(r, c);
        int nr = head / 50 + 1;
        int nc = head % 50;

        table[nr][nc] = value;
    }

    static void update(String v1, String v2) {
        for (int r = 1; r < 51; r++) {
            for (int c = 1; c < 51; c++) {
                if (v1.equals(table[r][c])) table[r][c] = v2;
            }
        }
    }

    static void merge(int r1, int c1, int r2, int c2) {
        if (r1 == r2 && c1 == c2) return;
        int idx1 = find(r1, c1);
        int idx2 = find(r2, c2);
        if (idx1 == idx2) return;
        int nr1 = idx1 / 50 + 1;
        int nc1 = idx1 % 50;
        int nr2 = idx2 / 50 + 1;
        int nc2 = idx2 % 50;

        if (table[nr1][nc1] == null && table[nr2][nc2] != null) {
            update(nr1, nc1, table[nr2][nc2]);
        }

        for (int r = 1; r < 51; r++) {
            for (int c = 1; c < 51; c++) {
                if (heads[r][c] == idx1 || heads[r][c] == idx2) heads[r][c] = idx1;
            }
        }
        table[nr2][nc2] = null;

    }

    static void unmerge(int r, int c) {
        int head = find(r, c);
        String tmp = table[head / 50 + 1][head % 50];
        for (int rr = 1; rr < 51; rr++) {
            for (int cc = 1; cc < 51; cc++) {
                if (find(rr, cc) == head) {
                    heads[rr][cc] = (rr - 1) * 50 + cc;
                    update(rr, cc, null);
                }
            }
        }
        update(r, c, tmp);
    }

    static String print(int r, int c) {
        int head = find(r, c);
        return table[head / 50 + 1][head % 50] == null ? "EMPTY" : table[head / 50 + 1][head % 50];
    }

}
