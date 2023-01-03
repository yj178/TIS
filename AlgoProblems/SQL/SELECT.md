# 3월에 태어난 여성 회원 목록 출력하기
```roomsql
SELECT 
    DR_NAME, DR_ID, MCDP_CD, DATE_FORMAT(HIRE_YMD,"%Y-%m-%d") AS HIRE_YMD
FROM 
    DOCTOR
WHERE 
    MCDP_CD IN ("CS", "GS")
ORDER BY 
    HIRE_YMD DESC, DR_NAME
```
# 인기있는 아이스크림
```roomsql
SELECT 
    FLAVOR
FROM
    FIRST_HALF
ORDER BY
    TOTAL_ORDER DESC, SHIPMENT_ID
```