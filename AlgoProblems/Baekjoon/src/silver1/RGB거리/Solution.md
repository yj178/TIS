# [baekjoon] [silver1.RGB거리](https://www.acmicpc.net/problem/1149)

## 목표
> 집을 조건에 맞춰 칠하는 경우 중 비용이 최소인 경우 얼마인지 구하여라

## 문제 조건
* 인접한 집은 같은 색으로 칠하지 않는다.
* $2 \leq N \leq 1,000$


## 추가로 고려해야 할 사항
완전 탐색의 경우 $3^1000$을 계산해야 하므로 시간 초과가 발생합니다.
백트래킹 가지치기를 고려해도 경우의 수가 매우 많으므로 DP를 활용하여 문제를 해결해야 합니다.   

## 풀기 전 생각한 풀이 과정
i번째 집을 각 색깔별로 칠하는데 최소 비용을 고려해야 하므로 매 순간 이전에 최소 비용인 집에 현재 집을 칠하면서 발생하는 비용을 고려하면 됩니다.   
ex) i번째 집을 빨간색으로 칠할 때, i-1번째 초록색, 파란색으로 칠한 경우 중 최소 비용에 대해서만 고려하면 됩니다.    

## 풀면서 추가로 고려한 사항
없습니다.   

## 느낀 점
없습니다.   