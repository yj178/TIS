# [Codetree] [Gold3 종전](https://www.codetree.ai/training-field/frequent-problems/war-finish/description?page=2&pageSize=20)

## 목표
>
## 문제 조건
### 기호 설명
* n : 격자의 크기
* m[r][c] : 격자 (r, c)의 위치에 존재하는 인구수
### 기호 범위
* $5 \leq n \leq 20$
* $1 \leq m[r][c] \leq 100$
### 문제 절차
* 격자의 맨 아래(지도에서 행이 N-1인 부분)에서 우상, 좌상, 좌하, 우하 방향으로 이동하여 순회
  * 해당 방향으로 최소 1칸은 움직여야 함
  * 격자 밖으로 나갈 수 없음
  * 상하좌우 꼭지점이 존재함
* 부족별 지역 차지
  * 1번 부족은 직사각형의 경계와 그 안에 있는 지역 차지
  * 2번 부족은 좌측 상단 경계의 윗부분을 차지함
    * 위쪽 꼭지점 경계부분 포함
    * 왼쪽 꼭지점 경계부분은 제외
  * 3번 부족은 우측 상단 경계의 윗부분에 해당하는 지역을 가짐
    * 오른쪽 꼭지점 부분도 포함
    * 위쪽 꼭지점 부분은 제외
  * 4번 부족은 좌측 하단 경계의 아래부분에 해당하는 지역을 차지함
    * 왼쪽 꼭지점 포함
    * 아래쪽 꼭지점 제외
  * 5번 부족은 우측 하단 경계의 아랫부분을 차지
    * 아랫쪽 꼭지점은 포함
    * 오른쪽 꼭지점은 제외

## 추가로 고려해야 할 사항
* 기울어진 사각형의 각 꼭지점은 격자의 모서리가 될 수 없다.
* 완탐을 하는 경우 
  * 3개의 꼭지점을 정하는 순간 기울어진 사각형은 결정됨
  * 첫 시작점이 가능한 위치는 $(N-2) * (N-1)$
  *  두번째 꼭지점이 정해지면 세번째 꼭지점의 위치도 제한됨
     * 세번째 꼭지점이 정해지면 네번째 꼭지점은 자동으로 지정
## 풀기 전 생각한 풀이 과정
* DFS를 활용하여 가능한 모든 경우에 대해 탐색 및 최대 최소 차이 갱신
* 1번 시작점이 가능한에 대해 전부 DFS 시작
  * 2번 꼭지점이 가능한 부위에 대해 다음 dep
    * 3번 꼭지점이 가능한 부위에 대해 dep
      * 4번 꼭지점은 자동으로 결정
        * 만약 4번 꼭지점이 격자를 벗어나면 종료
      * 기울어지 사각형의 2,3,4,5 부족 수를 구하고 1번은 전체에서 제외하는 방식으로 갱신
      * 최대 최소를 구하고 차이값을 갱신
* 결과를 확인한다
## 풀면서 추가로 고려한 사항

## 느낀 점
* 시작 시각 : 22:10
* 종료 시각 : 23:24
* 소요 시간 : 74분
경계 조건 작성 중 실수가 잦았습니다...