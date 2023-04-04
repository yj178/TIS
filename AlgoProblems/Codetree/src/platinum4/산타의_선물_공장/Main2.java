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

물건 확인 부분을 내 마음대로 구현함... ㅜㅠ

 */
class Main2 {
	static int Q, M, N;
	static boolean[] chk; // 벨트의 정상 유무 확인
	static HashMap<Long, Integer> map; // id, belt
	static HashMap<Long, Long> info; // id, weight
	static HashMap<Long, Long> head;
	static HashMap<Long, Long> tail;
	static long[] root;
	static long[] leaf;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("C:/Users/deter/OneDrive/바탕 화면/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		simul(br);
	}

	static void print() {
		for (int i = 0; i < M; i++) {
			System.out.println((i + 1) + "번째 벨트");
			long now = root[i];
			while (now != -1L) {
				System.out.println(now + "-" + info.get(now));
				now = tail.get(now);
			}
			System.out.println();
		}
	}

	static void command100(StringTokenizer st) {
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		info = new HashMap<>();
		map = new HashMap<>();
		chk = new boolean[M];
		head = new HashMap<>();
		tail = new HashMap<>();
		root = new long[M];
		leaf = new long[M];
		for (int i = 0; i < M; i++) {
			root[i] = -1;
			leaf[i] = -1;
		}
		long[] tmpId = new long[N + 2];
		tmpId[0] = -1L;
		tmpId[N + 1] = -1L;
		int idx = 0;
		for (int n = 1; n <= N; n++) {
			tmpId[n] = Long.parseLong(st.nextToken());
			map.put(tmpId[n], idx);
			if (n % (N / M) != 1) {
				head.put(tmpId[n], tmpId[n - 1]);
			} else {
				head.put(tmpId[n], -1L);
			}

			if (root[idx] == -1) {
				root[idx] = tmpId[n];
			}

			leaf[idx] = tmpId[n];
			if (n % (N / M) == 0) {
				idx++;

			}

		}

		for (int n = 1; n <= N; n++) {
			if (n % (N / M) != 0) {
				tail.put(tmpId[n], tmpId[n+1]);
			} else {
				tail.put(tmpId[n], -1L);
			}
		}

		for (int n = 1; n <= N; n++) {
			long w = Long.parseLong(st.nextToken());
			info.put(tmpId[n], w);
		}

//		for (int i = 30; i >= 0; i--) {
//			System.out.println(i + ", " + tmpId[i] + "," + info.get(tmpId[i]));
//		}

	}

	static Long command200(long w_max) {
		long sum = 0;

		for (int i = 0; i < M; i++) {
			if (root[i] == -1L)
				continue;

			// 물건하차

			// 현재 정보 확인
			long id = root[i];
			long w = info.get(id);
			long headId = head.get(id);
			long tailId = tail.get(id);

			// 현재 물건 빼면서 다음의 앞을 headId로
			head.put(tailId, -1L);
			root[i] = tailId;
			// w_max가 주어지고 컨테이너 벨트의 각각마다
			if (w <= w_max) {
				// w_max이하라면 물건 하차
				map.remove(id);
				sum += w;
			} else {
				// w_max를 초과한다면 해당 컨테이너 벨트의 맨 뒤에 넣기

				// 맨 마지막 다음에 현재 상품
				tail.put(leaf[i], id);
				// 현재 상품 앞에 이전 마지막을
				head.put(id, leaf[i]);
				// 현재 꼬리는 -1로
				tail.put(id, -1L);
				// 현재 leaf 갱신
				leaf[i] = id;
			}
		}

		// 하차한 모든 물건의 무게 합을 리턴한다.
		return sum;
	}

	static long command300(long r_id) {
		// 산타가 제거하기를 원하는 물건의 고유번호 r_id가 주어진다.
		// 상자가 존재한다면 r_id 값을, 없다면 -1을 출력
		if (!map.containsKey(r_id))
			return -1;
		// 해당 고유 번호에 해당하는 상자가 놓여있는 벨트가 있다면
		// 해당 벨트에서 상자를 제거하고, 뒤에 있는 상자들은 앞으로 한 칸씩 내려옴
		long headId = head.get(r_id);
		long tailId = tail.get(r_id);
		int b = map.get(r_id);
		if (headId != -1) {
			head.put(tailId, headId);
		} else {
			root[b] = tailId;
		}
		if (tailId != -1) {
			tail.put(headId, tailId);
		} else {
			leaf[b] = headId;
		}

		map.remove(r_id);
		return r_id;
	}

	static int command400(long f_id) {
		// 물건을 확인하기 원하는 고유번호 f_id가 주어짐
		// 없다면 -1을 출력
		if (!map.containsKey(f_id))
			return -1;
		// 놓여있는 벨트가 있다면 해당 벨트의 번호를 출력
		int b = map.get(f_id);
		// 이때, 해당 물건을 포함한 뒤에 있는 물건을 전부 벨트의 앞으로 가져온다.

		long exRoot = root[b];
		long exLeaf = leaf[b];
		long headId = head.get(f_id);

		// f_id 헤드가 꼬리가 된다.
		leaf[b] = headId;
		tail.put(headId, -1L);

		// f_id 의 꼬리와 이전의 루트가 연결된다.
		tail.put(exLeaf, exRoot);
		head.put(exRoot, exLeaf);

		// 현재 root 는 f_id가 된다.
		root[b] = f_id;
		head.put(f_id, -1L);

		return b + 1;
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
		int i = b_num;
		while (i < 2 * M) {
			i++;
			int idx = i % M;

			if (chk[idx])
				continue;

			// 고장나지 않은 벨트가 보이면 바로 해당 벨트의 뒤에 모든 물건을 추가한다
			long exLeaf = leaf[idx];
			long exHead = root[b_num];

			tail.put(exLeaf, exHead);
			head.put(exHead, exLeaf);

			leaf[idx] = leaf[b_num];

			root[b_num] = -1L;
			leaf[b_num] = -1L;
			long now = exHead;
			while (now != -1L) {
				map.put(now, idx);
				now = tail.get(now);
			}

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
//			print();
//			if (q < 30)
//				System.out.print(outLine);
			bw.write(outLine);
		}

		bw.close();

	}
}
