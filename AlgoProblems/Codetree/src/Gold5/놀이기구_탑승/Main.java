package Gold5.놀이기구_탑승;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;

class Main {
	static int N, T;
	static int[] index;
	static HashSet<Integer>[] infos;
	static int[][] map;
	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		System.out.println(simul());
	}

	static boolean chkOut(int r, int c) {
		return r < 0 || r >= N || c < 0 || c >= N ? true : false;
	}

	private static int simul() {
		for (int i = 0; i < T; i++) {
			// 자리 배치
			
			PriorityQueue<Pos> pq = new PriorityQueue<>();
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					if (map[r][c] != 0)
						continue;

					int eCnt = 0;
					int lCnt = 0;
					for (int d = 0; d < 4; d++) {
						int nr = r + dr[d];
						int nc = c + dc[d];
						if (chkOut(nr, nc))
							continue;
						if (map[nr][nc] == 0)
							eCnt++;
						if (chkLike(index[i], map[nr][nc]))
							lCnt++;
					}
					
					pq.add(new Pos(r, c, lCnt, eCnt));
				}
			}
			
			Pos pos = pq.poll();
			map[pos.r][pos.c] = index[i];

		}
		return calcScore();
	}

	static boolean chkLike(int i, int j) {
		if(j == 0) return false;
		return infos[i].contains(j);
	}

	static int cntToScore(int cnt) {
		switch (cnt) {
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 10;
		case 3:
			return 100;
		case 4:
			return 1000;

		}
		return -1;
	}

	private static int calcScore() {
		int sum = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				int cnt = 0;
				for (int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					if (chkOut(nr, nc))
						continue;
					if (chkLike(map[r][c], map[nr][nc]))
						cnt++;
				}
				sum += cntToScore(cnt);
			}
		}

		return sum;
	}

	private static void init(BufferedReader br) throws IOException {
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		int total = N * N;
		T = total;
		index = new int[total];
		infos = new HashSet[total + 1];

		for (int i = 0; i < total; i++) {
			String[] tmp = br.readLine().split(" ");
			int idx = Integer.parseInt(tmp[0]);
			index[i] = idx;
			infos[idx] = new HashSet<>();
			infos[idx].add(Integer.parseInt(tmp[1]));
			infos[idx].add(Integer.parseInt(tmp[2]));
			infos[idx].add(Integer.parseInt(tmp[3]));
			infos[idx].add(Integer.parseInt(tmp[4]));
		}

	}

	static class Pos implements Comparable<Pos> {
		int r, c, like, empty;

		public Pos(int r, int c, int like, int empty) {
			super();
			this.r = r;
			this.c = c;
			this.like = like;
			this.empty = empty;
		}

		@Override
		public int compareTo(Pos o) {
			// TODO Auto-generated method stub
			return this.like != o.like ? o.like - this.like
					: this.empty != o.empty ? o.empty - this.empty : this.r != o.r ? this.r - o.r : this.c - o.c;
		}

	}
}
