package Gold2.싸움땅;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 문제 이름 : 싸움땅
 * 링크 : https://www.codetree.ai/training-field/frequent-problems/battle-ground/description?page=3&pageSize=20
 * 시작시각 :14:34
 * 종료시각 :
 * 소요시간 :
 */
public class Main {
	static PriorityQueue<Integer>[][] map;
	static Info[] infos;
	static HashMap<Pos, Integer> pos;
	static int[] score;
	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };
	static int N, M, K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		simul();
		StringBuilder sb = new StringBuilder();
		for (int s : score) {
			sb.append(s + " ");
		}
		System.out.println(sb);
	}

	static boolean chkOut(int r, int c) {
		return r < 0 || r >= N || c < 0 || c >= N ? true : false;
	}

	static int oppDir(int d) {
		d += 2;
		if (d > 3)
			return d - 4;
		else
			return d;
	}

	static void move(int m) {
		Info me = infos[m];

		int nr = me.r + dr[me.d];
		int nc = me.c + dc[me.d];

		if (chkOut(nr, nc)) {
			// 다음 위치가 격자 밖인 경우
			me.d = oppDir(me.d);
			nr = me.r + dr[me.d];
			nc = me.c + dc[me.d];
			if (pos.containsKey(new Pos(nr, nc))) {
				// 사람이 있는 경우
				int o = pos.get(new Pos(nr, nc));

				if (me.compareTo(infos[o]) <= 0)
					fight(nr, nc, m, o);
				else
					fight(nr, nc, o, m);

			} else {
				// 사람이 없는 경우
				me.exMove();
				me.move(nr, nc, m);
				me.setGun();
			}

		} else {
			// 다음 위치가 격자 내부인 경우
			if (pos.containsKey(new Pos(nr, nc))) {
				// 사람이 있는 경우
				int o = pos.get(new Pos(nr, nc));
				// 사람이 있는 경우
				if (me.compareTo(infos[o]) <= 0)
					fight(nr, nc, m, o);
				else
					fight(nr, nc, o, m);

			} else {
				// 사람이 없는 경우
				me.exMove();
				me.move(nr, nc, m);
				me.setGun();
			}
		}
	}

	static int setDir(int d) {
		if (d < 0)
			return d + 4;
		if (d > 3)
			return d - 4;
		return d;
	}

	static void fight(int r, int c, int m1, int m2) {
		// 전투력 비교 m1이 무조건 전투력 높은 사람
		Info win = infos[m1];
		Info lose = infos[m2];
		int diffAp = win.getAP() - lose.getAP();
		win.exMove();
		lose.exMove();
		win.r = r;
		win.c = c;
		lose.r = r;
		lose.c = c;

		// 총 내려 놓고
		lose.dropGun();

		// 이동
		for (int dd = 0; dd < 4; dd++) {
			int nd = setDir(lose.d + dd);

			int nr = r + dr[nd];
			int nc = c + dc[nd];
			if (chkOut(nr, nc) || pos.containsKey(new Pos(nr, nc)))
				continue;
			lose.move(nr, nc, m2);
			lose.d = nd;
			break;
		}
		lose.setGun();

		// 이긴 사람
		win.move(r, c, m1);

		// 총 업뎃
		win.setGun();
//		System.out.println();
		// 점수 획득
//		System.out.println(m1 + " pos : " + win.r + ", " + win.c + " get " + diffAp + "/ " + win.d);
//		System.out.println(m2 + " pos : " + lose.r + ", " + lose.c+ "/ " + win.d);
		
		for(Map.Entry<Pos, Integer> e : pos.entrySet()) {
			System.out.println(e.getValue() + ":" + e.getKey());
		}
		score[m1] += diffAp;
	}

	static void simul() {
		for (int k = 0; k < K; k++) {
			for (int m = 0; m < M; m++) {
				// 이동
				move(m);
			}
		}
	}

	static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new PriorityQueue[N][N];
		score = new int[M];
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				map[r][c] = new PriorityQueue<>(Collections.reverseOrder());
				map[r][c].add(Integer.parseInt(st.nextToken()));
			}
		}

		infos = new Info[M];
		pos = new HashMap<>();
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			infos[m] = new Info(x - 1, y - 1, d, s);
			pos.put(new Pos(x - 1, y - 1), m);

		}
	}

	static class Pos {
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return this.r * 21 + c;
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			if (obj.getClass() != Pos.class) {
				return false;
			}

			Pos o = (Pos) obj;
			if (this.r == o.r && this.c == o.c)
				return true;
			return false;
		}

		@Override
		public String toString() {
			return "Pos [r=" + r + ", c=" + c + "]";
		}
		

	}

	static class Info implements Comparable<Info> {
		int r, c, d, s, g;

		public Info(int r, int c, int d, int s) {
			super();
			this.r = r;
			this.c = c;
			this.d = d;
			this.s = s;
		}

		public void setGun() {
			if (!map[this.r][this.c].isEmpty() && map[this.r][this.c].peek() > this.g) {
				if (this.g != 0)
					map[this.r][this.c].add(this.g);
				this.g = map[this.r][this.c].poll();
			}
		}

		public int getAP() {
			return this.g + this.s;
		}

		public void dropGun() {
			if (this.g != 0)
				map[this.r][this.c].add(this.g);
			this.g = 0;
		}

		public void exMove() {
			pos.remove(new Pos(this.r, this.c));
		}

		public void move(int r, int c, int m) {
//			if (this.r == r && this.c == c)
//				return;

			this.r = r;
			this.c = c;
			pos.put(new Pos(r, c), m);
		}

		@Override
		public int compareTo(Info o) {
			return this.getAP() != o.getAP() ? o.getAP() - this.getAP() : o.s - this.s;
		}

	}
}
