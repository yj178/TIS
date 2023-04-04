### [Type of Triangle](https://www.hackerrank.com/challenges/what-type-of-triangle/problem?isFullScreen=true)

```sql
SELECT 
    (CASE   WHEN A + B <= C || A + C <= B || B + C <= A THEN 'Not A Triangle'
            WHEN A = B AND A = C THEN 'Equilateral'
            WHEN A = B AND B != C || A != B AND B = C || A = C  AND A != B THEN 'Isosceles'
            ELSE 'Scalene'
    END) AS ''
FROM
    TRIANGLES 

```