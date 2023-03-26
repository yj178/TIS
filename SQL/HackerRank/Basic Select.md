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