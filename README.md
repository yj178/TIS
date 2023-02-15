# TIS Convention
Today I Solved repository 운영 방식

## 문제 풀이 절차
1. [이슈 생성](https://github.com/yj178/TIS/blob/main/.github/ISSUE_TEMPLATE/a-problem-i-will-solve-today-.md)
2. 이슈에서 branch 생성
3. 문제 읽고 풀이 작성 
4. 풀이 내용으로 코드 작성
5. 풀이 내용에서 고려하지 못한 점이나 그 외 특이사항 정리
6. pull request 생성 후 main branch에 병합

## commit Convention
{YYYYMMDD}-{state}-{문제 이름}-{#이슈번호}: 한줄평 

ex) 20230131-solved-업무처리-#10: 문제 통과 / 풀이 완료
  


### state 종류
* solving : 문제 푸는 중 (풀다가 다른 작업을 하게 되는 경우 임시로 저장)
* solved : 문제 풀이 완료
* refactor : TIS 내용 중 변경 사항이 발생한 경우
* docs : readme, solution과 같은 문서를 작성하는 경우
  ※solution.md 파일 외의 문서는 main branch에서 작성

## package Convention
* module - 문제풀이 사이트 명
  * level - 각 사이트의 문제 레벨
    * name - 문제 이름
      * 각 사이트에 요구하는 클래스 명으로 설정
      * solution.md : 문제 풀이 방법
