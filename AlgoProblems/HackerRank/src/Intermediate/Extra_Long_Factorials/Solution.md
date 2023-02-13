# Intermediate.Extra_Long_Factorials

20보다 큰 숫자의 팩토리얼 결과값은 java의 long의 범위를 벗어난다.   
이를 계산하기 위해서 BigInteger라는 클래스를 활용해야한다.   

## [BigInteger 메서드 자주 사용할만한 것들](https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html)
* BigInteger abs() : Returns a BigInteger whose value is the absolute value of this BigInteger.
* BigInteger add(BigIteger val) : Returns a BigInteger whose value is (this + val).
* BigInteger subtract(BigInteger val) : Returns a BigInteger whose value is (this - val).
* BigInteger divide(BigInteger val) : Returns a BigInteger whose value is (this / val).
* BigInteger multiply(BigInteger val) : Returns a BigInteger whose value is (this * val).
* BigInteger mod(BigInteger m) : Returns a BigInteger whose value is (this mod m).
* BigInteger remainder(BigInteger val) : Returns a BigInteger whose value is (this % val).
* BigInteger pow(int exponent) : Returns a BigInteger whose value is (thisexponent).
* int compareTo(BigInteger val) : Compares this BigInteger with the specified BigInteger.
* BigInteger max(BigInteger val) : Returns the maximum of this BigInteger and val.
* BigInteger min(BigInteger val) : Returns the minimum of this BigInteger and val.
* static BigInteger valueOf(long val) : Returns a BigInteger whose value is equal to that of the specified long.
* String toString() : Returns the decimal String representation of this BigInteger.
