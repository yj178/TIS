# TIS
Today I Solved

## git Convention
{YYYYMMDD}-{state}-{problems code}: 한줄평 #문제특성   
문제풀이 방법

ex) 20230131-solved-H138358: solving this problem using full binary tree and indexing #complete binary tree

This problem has the following conditions.
* H is greater than 1 and less than 10.
* K is greater than 1 and less than 10.
* R is greater than 1 and less than 1,000.

Therefore, three things can be inferred as follows. 
* The maximum number of leaf nodes is 1,024
* The maximum of all workloads is about 10,000,000
* Exploring all nodes according to the schedule is (sum of 2^n, from n = 0 to 10) * 10 = 20,470

So, I used the int type variable ans(= workload), and checked the result by iterating through all situation. 


### state 종류
* solving : 문제 푸는 중
* solved : 문제 풀이 완료
* fail : 문제 풀이 과정 중 실패한 경우
* fix : 풀이 및 readme 수정
* refactor : 코드/커밋 리펙토링 or 파일, 폴더 변경 및 수정
* docs : readme 업데이트

### problems code
site code + 문제 번호
#### site code
* SWEA : S
* Baekjoon : B
* Programmers : P
* CodeTree : C
* Softeer : H

