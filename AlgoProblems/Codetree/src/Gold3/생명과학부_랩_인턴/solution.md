# [Codetree] [Gold3 생명과학부_랩_인턴](https://www.codetree.ai/training-field/frequent-problems/biology-lab-intern/description?page=2&pageSize=20)

## 목표
> 인턴이 채취한 모든 곰팡이의 크기의 합을 구하시오.

## 문제 조건
### 기호 설명
* n : 격자 판의 행의 크기
* m : 격자 판의 열의 크기
* k : 초기곰팡이의 수
* x : 곰팡이의 초기 행의 위치
* y : 곰팡이의 초기 열의 위치
* s : 곰팡이가 1초 동안 움직이는 거리
* d : 곰팡이의 이동 방향
* b : 곰팡이의 크기
### 기호 범위
* $2 \leq n, m \leq 100$
* $0 \leq k \leq n*m$
* $1 \leq x \leq n$
* $1 \leq y \leq m$
* $0 \leq s \leq 1,000$
* $1 \leq d \leq 4$
* $1 \leq b \leq 10,000$
### 문제 설명
* 첫번째 열부터 탐색 시작
* 열의 위에서 아래로 탐색 진행
  * 제일 빨리 발견한 곰팡이 채취 -> 해당 칸은 빈칸
* 채취 종료 후 곰팡이 이동 시작
  * 입력으로 주어진 방향과 속력으로 이동
    * 격자판의 벽에 도달하면
      * 방향 반대
      * 속력 유지
      * 방향 변환시 시간 소요 없음
* 모든 곰팡이 이동 종료 후 
  * 한 칸에 곰팡이가 두 마리 이상 존재한다면 크기가 큰 곰팡이가 다른 곰팡이를 잡아 먹음
* 이 모든 과정은 1초가 걸리며, 오른쪽 열로 이동해서 반복함

## 추가로 고려해야 할 사항
* 방향은 1 : 위, 2 : 아래, 3 : 오른쪽, 4 : 왼쪽을 의미함 방향 반전이 존재하므로 입력 받은값을 적절하게 변환해야함
* 좌상의 꼭지점을 1,1로 잡았음


## 풀기 전 생각한 풀이 과정
오늘은 안하고 진행해봄

## 풀면서 추가로 고려한 사항
* 곰팡이의 이동에 대해 제대로 파악하지 못함
  * 격자를 넘어설 때, 방향만 바꾸는 상태가 움직임에 포함되는 것으로 오해함
* 큰 곰팡이가 먹어치운다는 의미를 간과함
  * 큰 곰팡이가 먹어치우기 때문에 크기 순으로 확인한다면 같은 위치에 곰팡이가 있는 경우 기록하지 않아도 됨
  * 지도를 HashSet[][]으로 만들 필요가 없음   

## 느낀 점
시작 시각 : 19:55   
종료 시각 : 21:10   
소요 시간 : 75분   
확실히 풀기 전에 풀이과정을 구체적으로 생각한 것과 안한건 코드의 퀄리티와 로직의 간단함에 큰 차이가 있네요...

