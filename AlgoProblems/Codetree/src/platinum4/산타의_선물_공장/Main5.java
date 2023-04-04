package platinum4.산타의_선물_공장;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
문제 이름 : 산타의 선물 공장
문제 링크 : https://www.codetree.ai/training-field/frequent-problems/santa-gift-factory/description?page=3&pageSize=20
시작 시각 : 07:50
종료 시각 : 
소요 시각 : 

 */
class Main5 {
	static int Q, M, N;
	static boolean[] chk; // 벨트의 정상 유무 확인
	static HashMap<Long, Info> infos; // id, belt
	static long[] root;
	static long[] leaf;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("C:/Users/deter/OneDrive/바탕 화면/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		simul(br);
	}

	static void print(int q) {
		for (int i = 0; i < M; i++) {
			long now = root[i];
			while (now != -1L) {
				System.out.println(q + "번째 명령" + (i + 1) + "번째 벨트 : " + now + "-" + infos.get(now).w + "// 이전 "
						+ infos.get(now).h + "// 다음" + infos.get(now).t);
				now = infos.get(now).t;
			}
			System.out.println();
		}
	}

	static void command100(StringTokenizer st) {
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		chk = new boolean[M];
		root = new long[M];
		leaf = new long[M];
		infos = new HashMap<>();
		for (int i = 0; i < M; i++) {
			root[i] = -1;
			leaf[i] = -1;
		}
		long[] tmpId = new long[N + 2];
		tmpId[0] = -1L;
		tmpId[N + 1] = -1L;

		for (int i = 1; i <= N; i++) {
			tmpId[i] = Long.parseLong(st.nextToken());
		}

		for (int i = 1, j = 0, idx = -1; i <= N; i++, j++) {
			long id = tmpId[i];
			long w = Long.parseLong(st.nextToken());
			if (j % (N / M) == 0) {
				idx++;
				root[idx] = id;
			}
			if (j % (N / M) == 0) { // 시작하는 시점
				infos.put(id, new Info(-1, tmpId[i + 1], w, idx));
			} else if (j % (N / M) == N / M - 1) {
				infos.put(id, new Info(tmpId[i - 1], -1, w, idx));
				leaf[idx] = id;
			} else {
				infos.put(id, new Info(tmpId[i - 1], tmpId[i + 1], w, idx));
			}

		}

	}

	static Long command200(long w_max) {
		long sum = 0;
		// 모든 컨테이너 벨트에서
		for (int m = 0; m < M; m++) {
			// 비어있는 경우 생략
			if (root[m] == -1)
				continue;

			// 현재 정보 확인
			long id = root[m];
			Info now = infos.get(id);

			if (now.w <= w_max) {// w값이 w_max보다 작은 경우
				root[m] = now.t; // 다음 아이디가 head로 옮겨짐
				if (now.t != -1) // 요거 하나 빼먹음 ㅎㅎ;;;
					infos.get(now.t).h = -1;
				else {
					root[m] = -1;
					leaf[m] = -1;
				}

				sum += now.w; // 무게 누적 기록
				infos.remove(id);// 제거
			} else { // w값이 큰 경우
				if (now.t != -1) {
					// 맨 앞 갱신
					infos.get(now.t).h = -1;
					root[m] = now.t;

					// 맨 뒤 갱신
					now.h = leaf[m];
					now.t = -1;
					infos.get(leaf[m]).t = id;
					leaf[m] = id;
				}
			}
		}

		// 하차한 모든 물건의 무게 합을 리턴한다.
		return sum;
	}

	static long command300(long r_id) {
		// 산타가 제거하기를 원하는 물건의 고유번호 r_id가 주어진다.
		// 상자가 존재한다면 r_id 값을, 없다면 -1을 출력
		if (!infos.containsKey(r_id))
			return -1;

		// 해당 고유 번호에 해당하는 상자가 놓여있는 벨트가 있다면
		Info target = infos.get(r_id);
		long hId = target.h;
		long tId = target.t;

		if (hId == -1L && tId == -1L) { // 하나만 있는 경우
			root[target.b] = -1;
			leaf[target.b] = -1;
		} else if (tId == -1L) { // 맨 뒤인 경우
			leaf[target.b] = hId;
			infos.get(hId).t = -1;
		} else if (hId == -1L) { // 맨 앞인 경우
			root[target.b] = tId;
			infos.get(tId).h = -1;
		} else { // 중간인 경우
			infos.get(tId).h = hId;
			infos.get(hId).t = tId;
		}
		infos.remove(r_id);

		// 해당 벨트에서 상자를 제거하고, 뒤에 있는 상자들은 앞으로 한 칸씩 내려옴
		return r_id;
	}

	static int command400(long f_id) {
		// 물건을 확인하기 원하는 고유번호 f_id가 주어짐
		// 없다면 -1을 출력
		if (!infos.containsKey(f_id))
			return -1;
		// 이때, 해당 물건을 포함한 뒤에 있는 물건을 전부 벨트의 앞으로 가져온다.
		Info target = infos.get(f_id);
		int b = target.b;
		long exleaf = leaf[b];
		long exroot = root[b];
		if (target.t != -1L && target.h != -1L) {// 중간인 경우
			// 기존의 맨 앞은 기존의 맨뒤의 뒤로 간다.
			infos.get(exroot).h = exleaf;
			infos.get(exleaf).t = exroot;

			// 맨 뒤의 값 갱신
			leaf[b] = target.h;
			infos.get(target.h).t = -1;

			// 맨 앞을 갱신
			target.h = -1;
			root[b] = f_id;
		} else if (target.t == -1L) {// 꼬리인 경우
			infos.get(target.h).t = -1;
			leaf[b] = target.h;

			// 맨 앞을 갱신
			target.h = -1;
			target.t = root[b];
			infos.get(target.t).h = f_id;
			root[b] = f_id;
		}

		// 놓여있는 벨트가 있다면 해당 벨트의 번호를 출력
		return target.b + 1;
	}

	static int command500(int b_num) {
		b_num -= 1;
		// 고장이 발생한 벨트 번호 b_num이 주어진다
		// 만약 해당 벨트가 이미 망가져 있었다면 -1을 반환
		if (chk[b_num])
			return -1;

		// 고장이 발생하면 해당 벨트는 다시는 사용할 수 없게 되고
		chk[b_num] = true;
		// b_num 벨트의 바로 오른쪽 벨트부터 순서대로 보며 보며, m개를 넘어가는 경우 처음부터 확인한다
		for (int i = b_num; i < 2 * M; i++) {
			int idx = i % M;
			if (chk[idx])
				continue;
			// 고장나지 않은 벨트가 보이면 바로 해당 벨트의 뒤에 모든 물건을 추가한다
			infos.get(root[b_num]).h = leaf[idx];
			infos.get(leaf[idx]).t = root[b_num];
			leaf[idx] = leaf[b_num];
			long now = root[b_num];
			while (now != -1L) {
				Info tmp = infos.get(now);
				tmp.b = idx;
				now = tmp.t;
			}
			root[b_num] = -1;
			leaf[b_num] = -1;
			break;
		}

		// 지금 망가진다면 b_num을 출력한다.
		return b_num + 1;

	}

	static void simul(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		Q = Integer.parseInt(st.nextToken());
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());
			String outLine = "";
			switch (Integer.parseInt(st.nextToken())) {
			case 100: // 공장 설립
				command100(st);
				break;
			case 200: // 물건 하차
				outLine += command200(Long.parseLong(st.nextToken())) + "\n";
				break;
			case 300: // 물건 제거
				outLine += command300(Long.parseLong(st.nextToken())) + "\n";
				break;
			case 400: // 물건 확인
				outLine += command400(Long.parseLong(st.nextToken())) + "\n";
				break;
			case 500: // 벨트 고장
				outLine += command500(Integer.parseInt(st.nextToken())) + "\n";
				break;
			}

			bw.write(outLine);
//			if (q >= 3750) {
////				System.out.println((q) + " : " + outLine);
//				System.out.println(q + "번째 크기 : " + infos.size());
////				print(q);
//			}
		}

		bw.close();

	}

	static class Info {
		long h, t, w;
		int b;

		public Info(long h, long t, long w, int b) {
			super();
			this.h = h;
			this.t = t;
			this.w = w;
			this.b = b;
		}

	}
}
