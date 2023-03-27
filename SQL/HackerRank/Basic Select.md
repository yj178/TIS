# Basic Select

## [Revising the Select Query I](https://www.hackerrank.com/challenges/revising-the-select-query/problem?isFullScreen=true)

```sql
SELECT
    *
FROM
    CITY
WHERE
    COUNTRYCODE = "USA" AND POPULATION > 100000
```

### [Revising the Select Query II](https://www.hackerrank.com/challenges/revising-the-select-query-2/problem?isFullScreen=true)

```sql
SELECT
    NAME
FROM 
    CITY
WHERE
    POPULATION > 120000 
    AND COUNTRYCODE = 'USA'

```

## [Weather Observation Station 4](https://www.hackerrank.com/challenges/weather-observation-station-4/problem?isFullScreen=true)
```sql
SELECT 
    COUNT(*) - COUNT(DISTINCT(CITY))
FROM
    STATION
```
### [Weather Observation Station 3](https://www.hackerrank.com/challenges/weather-observation-station-3/problem?isFullScreen=true)

```sql
SELECT
    DISTINCT(CITY)
FROM 
    STATION
WHERE 
    ID % 2 = 0
```

### [Weather Observation Station 5](https://www.hackerrank.com/challenges/weather-observation-station-5/problem?isFullScreen=true&h_r=next-challenge&h_v=zen)

```sql
(
SELECT
    CITY, LENGTH(CITY)
FROM
    STATION
ORDER BY
    LENGTH(CITY), CITY
LIMIT 1
)
UNION
(
SELECT
    CITY, LENGTH(CITY)
FROM
    STATION
ORDER BY
    LENGTH(CITY) DESC, CITY
LIMIT 1
)
```

### [Weather Observation Station 6](https://www.hackerrank.com/challenges/weather-observation-station-6/problem?isFullScreen=true&h_r=next-challenge&h_v=zen&h_r=next-challenge&h_v=zen)

```sql
SELECT
    CITY
FROM
    STATION
WHERE
    CITY REGEXP "^[aeiou].+"
```

### [Weather Observation Station 7](https://www.hackerrank.com/challenges/weather-observation-station-7/problem?isFullScreen=true&h_r=next-challenge&h_v=zen&h_r=next-challenge&h_v=zen&h_r=next-challenge&h_v=zen)

```sql
SELECT
    DISTINCT(CITY)
FROM   
    STATION
WHERE  
    CITY REGEXP ".+[aeiou]$"
```