package platinum5.산타의_선물_공장2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * 시작 시간 22:13
 * root, leaf
 * head, tail
 * 1개인 경우를 고려하지 않아 이틀 동안 고민함
 */

// 각 벨트의 맨 앞 선물의 번호
// 각 벨트의 마지막 선물의 번호
// n번째 선물의 위치 정보 <- 이건 타고 가서 찾을 수 밖에...
// 특성 선물이 어느 벨트에 위치해 있는지 파악할 수 있는 정보
public class Main {
	static int N, M;
	static int[] pos, head, tail, root, leaf, cnt;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("AlgoProblems/Codetree/input/산타2.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int Q = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());

			switch (st.nextToken()) {
				case "100":
					command100(st);
					break;
				case "200":
					sb.append(
							"" + command200(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "300":
					sb.append(
							"" + command300(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "400":
					sb.append(
							"" + command400(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "500":
					sb.append("" + command500(Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "600":
					sb.append("" + command600(Integer.parseInt(st.nextToken())) + "\n");
					break;
			}
			// if (q > 10)
			// continue;
			// System.out.println(q + "번째 명령 결과");
			// for (int n = 1; n <= N; n++) {
			// 	int now = root[n];
			// 	int c = 0;
			// 	while (now != 0) {
			// 		System.out.print(now + " ");
			// 		now = tail[now];
			// 		if (c++ > 10)
			// 			break;
			// 	}
			// 	System.out.println(" / cnt : " + cnt[n] + ", root : " + root[n] + ", leaf : " + leaf[n]);

			// }

			// for (int m = 1; m <= M; m++) {
			// 	System.out.println(m + "번 -> head : " + head[m] + ", tail : " + tail[m]);
			// }
		}
		// System.out.println("ans----------------------");
		System.out.println(sb);
	}

	static void command100(StringTokenizer st) {
		// 공장 설립
		// 각 벨트의 정보와 선물의 정보를 조회할 수 있는 기능들을 추가하여 새로운 공장을 만들고자 합니다.
		// n개의 벨트, m개의 물건
		// m개의 선물의 위치가 공백을 사이에 두고 주어짐
		// 선물의 번호는 오름차순으로 벨트에 쌓임
		// 별도의 출력 없음

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		pos = new int[M + 1];
		head = new int[M + 1];
		tail = new int[M + 1];

		root = new int[N + 1];
		leaf = new int[N + 1];
		cnt = new int[N + 1];

		for (int m = 1; m <= M; m++) {
			pos[m] = Integer.parseInt(st.nextToken());
			if (cnt[pos[m]] == 0) {
				root[pos[m]] = m;
				leaf[pos[m]] = m;

				cnt[pos[m]] = 1;
			} else {
				tail[leaf[pos[m]]] = m;

				head[m] = leaf[pos[m]];

				leaf[pos[m]] = m;
				cnt[pos[m]]++;
			}

		}
	}

	static void inFirst(int b_num, int idx) {
		// System.out.println("inFirst : " + b_num + ", " + idx);
		if (cnt[b_num] == 0) {
			leaf[b_num] = idx;
		} else {
			head[root[b_num]] = idx;
			tail[idx] = root[b_num];
		}

		root[b_num] = idx;

		cnt[b_num]++;

	}

	static int outFirst(int b_num) {
		// 0개 있으면 -1 리턴
		if (cnt[b_num] == 0)
			return -1;

		// 해당 위치의 맨 앞 인덱스 번호
		int idx = root[b_num];

		// 만약 1개만 있다면
		if (cnt[b_num] == 1) {
			// 해당 벨트의 맨 앞, 뒤 초기화
			root[b_num] = 0;
			leaf[b_num] = 0;
		} else {
			// 맨 앞에서 2번째가 맨 앞으로 올 수 있도록 수정
			int tIdx = tail[idx];
			head[tIdx] = 0;
			root[b_num] = tIdx;
		}
		head[idx] = 0;
		tail[idx] = 0;
		cnt[b_num]--;

		return idx;
	}

	static void moveAll(int m_src, int m_dst) {
		// 옮길게 없으면 종료
		if (cnt[m_src] == 0)
			return;

		// src의 맨 뒤를 dst의 맨 앞과 연결 시킨다.
		int srcLeaf = leaf[m_src];
		int dstRoot = root[m_dst];

		tail[srcLeaf] = dstRoot;
		head[dstRoot] = srcLeaf;

		// 만약 dst에 아무것도 없다면 추가로 해줘야 하는 작업들
		if (cnt[m_dst] == 0) {
			head[0] = 0;
			leaf[m_dst] = leaf[m_src];
		}

		root[m_dst] = root[m_src];
		cnt[m_dst] += cnt[m_src];

		root[m_src] = 0;
		leaf[m_src] = 0;
		cnt[m_src] = 0;
	}

	static int command200(int m_src, int m_dst) {
		moveAll(m_src, m_dst);
		return cnt[m_dst];
	}

	static int command300(int m_src, int m_dst) {
		int src_idx = outFirst(m_src);
		int dst_idx = outFirst(m_dst);
		// System.out.println("src : " + src_idx);
		// System.out.println("dst : " + dst_idx);
		if (src_idx != -1)
			inFirst(m_dst, src_idx);
		if (dst_idx != -1)
			inFirst(m_src, dst_idx);
		return cnt[m_dst];
	}

	static int command400(int m_src, int m_dst) {

		Stack<Integer> tmp = new Stack<>();
		int n = cnt[m_src] / 2;
		for (int i = 0; i < n; i++) {
			tmp.add(outFirst(m_src));
		}

		while (!tmp.isEmpty()) {
			inFirst(m_dst, tmp.pop());
		}

		// 옮긴 뒤 m_dst 번째 벨트에 있는 선물들의 개수를 출력함
		return cnt[m_dst];

	}

	static int command500(int p_num) {

		int a = head[p_num] == 0 ? -1 : head[p_num];
		int b = tail[p_num] == 0 ? -1 : tail[p_num];

		return a + 2 * b;

	}

	static int command600(int b_num) {

		int c = cnt[b_num];
		int a = root[b_num] == 0 ? -1 : root[b_num];
		int b = leaf[b_num] == 0 ? -1 : leaf[b_num];

		return a + 2 * b + 3 * c;

	}

}
