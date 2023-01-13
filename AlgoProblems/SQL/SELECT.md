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

# 조건에 맞는 도서 리스트 출력하기
```roomsql
-- 코드를 입력하세요
SELECT
    BOOK_ID, DATE_FORMAT(PUBLISHED_DATE, "%Y-%m-%d") AS PUBLISHED_DATE
FROM
    BOOK
WHERE
    YEAR(PUBLISHED_DATE) = "2021"
    AND CATEGORY = '인문'
ORDER BY
    PUBLISHED_DATE
    
```

# 과일로 만든 아이스크림 고르기
```roomsql
SELECT
    FH.FLAVOR
FROM
    FIRST_HALF AS FH
    LEFT JOIN ICECREAM_INFO AS II ON FH.FLAVOR = II.FLAVOR
WHERE
    FH.TOTAL_ORDER > 3000 AND II.INGREDIENT_TYPE = "fruit_based"
ORDER BY
    FH.TOTAL_ORDER DESC 
```

# 인기있는 아이스크림

```roomsql
-- 코드를 입력하세요
SELECT
    FLAVOR
FROM
    FIRST_HALF 
ORDER BY
    TOTAL_ORDER DESC, SHIPMENT_ID
```

# 평균 일일 대여 요금 구하기
```roomsql
-- 코드를 입력하세요
SELECT
    ROUND(AVG(DAILY_FEE),0) AS AVERAGE_FEE 
FROM
    CAR_RENTAL_COMPANY_CAR 
WHERE
    CAR_TYPE = 'SUV'
```

# 강원도에 위치한 생산공장 목록 출력하기
```roomsql
-- 코드를 입력하세요
SELECT
    FACTORY_ID, FACTORY_NAME, ADDRESS
FROM
    FOOD_FACTORY 
WHERE
    ADDRESS LIKE '%강원도%'
ORDER BY
    FACTORY_ID
```