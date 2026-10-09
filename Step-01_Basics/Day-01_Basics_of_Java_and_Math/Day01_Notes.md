# Day 1 — Step 1: Basics of Java & Math
**Date:** 09 October 2026
**Topic:** Java Fundamentals + Basic Math Problems
**Target:** 5 Problems (Things to Know + Math)

---

## What to Know in Java (Quick Recap)

### Data Types
| Type | Size | Range |
|------|------|-------|
| int | 4 bytes | -2^31 to 2^31-1 (~2.1 billion) |
| long | 8 bytes | -2^63 to 2^63-1 |
| double | 8 bytes | decimal values |
| char | 2 bytes | single character |
| boolean | 1 bit | true/false |
| String | object | sequence of chars |

### Key Java Concepts to Remember
- Use `long` when numbers might exceed int range
- Integer.MAX_VALUE = 2147483647
- String is immutable in Java
- Arrays are 0-indexed
- Use `StringBuilder` for string concatenation in loops (not +)

### Input in Java (Scanner)
```java
import java.util.*;
Scanner sc = new Scanner(System.in);
int n = sc.nextInt();
long l = sc.nextLong();
String s = sc.next();       // single word
String line = sc.nextLine(); // full line
```

---

## Problem 1 — Count Digits in a Number
**Platform Links:** [GFG](https://practice.geeksforgeeks.org/problems/count-digits5716/1) \| [Coding Ninjas](https://www.codingninjas.com/studio/problems/count-digits_8416387)
**Difficulty:** Easy

### Problem Statement
Given a number N, count the number of digits in it.

### Examples
```
Input:  N = 12345   → Output: 5
Input:  N = 7       → Output: 1
Input:  N = 100     → Output: 3
```

### Approach
**Method 1 — String Conversion (Brute Force):**
- Convert number to string, return its length
- Time: O(digits), Space: O(digits)

**Method 2 — Division (Optimal):**
- Divide by 10 repeatedly until number becomes 0
- Count each division
- Time: O(log10 N), Space: O(1)

**Method 3 — Math (Most Optimal):**
- `(int)(Math.log10(N)) + 1`
- Careful: doesn't work for N=0

### Java Solution
```java
public class CountDigits {
    
    // Method 1: String
    public static int countDigitsString(int n) {
        return String.valueOf(Math.abs(n)).length();
    }
    
    // Method 2: Loop (recommended)
    public static int countDigitsLoop(int n) {
        if (n == 0) return 1;
        n = Math.abs(n);
        int count = 0;
        while (n > 0) {
            count++;
            n /= 10;
        }
        return count;
    }
    
    // Method 3: Math log
    public static int countDigitsMath(int n) {
        if (n == 0) return 1;
        return (int)(Math.log10(Math.abs(n))) + 1;
    }
    
    public static void main(String[] args) {
        System.out.println(countDigitsLoop(12345)); // 5
        System.out.println(countDigitsLoop(0));     // 1
        System.out.println(countDigitsLoop(-999));  // 3
    }
}
```

### Complexity
| Approach | Time | Space |
|----------|------|-------|
| String | O(log N) | O(log N) |
| Loop | O(log N) | O(1) |
| Math | O(1) | O(1) |

---

## Problem 2 — Reverse a Number
**Platform Links:** [LeetCode #7](https://leetcode.com/problems/reverse-integer/) \| [GFG](https://practice.geeksforgeeks.org/problems/reverse-digit0316/1) \| [Coding Ninjas](https://www.codingninjas.com/studio/problems/reverse-of-a-number_624652)
**Difficulty:** Easy/Medium

### Problem Statement
Given a signed 32-bit integer x, return its digits reversed.
If the result overflows 32-bit integer range, return 0.

### Examples
```
Input:  123   → Output: 321
Input:  -123  → Output: -321
Input:  120   → Output: 21
Input:  0     → Output: 0
```

### Approach
- Extract last digit using mod: `digit = x % 10`
- Build reversed number: `rev = rev * 10 + digit`
- Remove last digit: `x /= 10`
- Check overflow before adding each digit

### Java Solution
```java
public class ReverseNumber {
    public int reverse(int x) {
        long rev = 0;
        while (x != 0) {
            int digit = x % 10;
            rev = rev * 10 + digit;
            x /= 10;
        }
        // Check overflow
        if (rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE) return 0;
        return (int) rev;
    }
    
    public static void main(String[] args) {
        ReverseNumber obj = new ReverseNumber();
        System.out.println(obj.reverse(123));   // 321
        System.out.println(obj.reverse(-123));  // -321
        System.out.println(obj.reverse(120));   // 21
    }
}
```

### Complexity
- Time: O(log N) — number of digits
- Space: O(1)

### Key Insight
Use `long` for `rev` to detect overflow before casting back to int.

---

## Problem 3 — Check Palindrome Number
**Platform Links:** [LeetCode #9](https://leetcode.com/problems/palindrome-number/) \| [GFG](https://practice.geeksforgeeks.org/problems/palindrome0746/1) \| [Coding Ninjas](https://www.codingninjas.com/studio/problems/palindrome-number_624662)
**Difficulty:** Easy

### Problem Statement
An integer is a palindrome when it reads the same forward and backward.
Negative numbers are NOT palindromes.

### Examples
```
Input: 121   → Output: true
Input: -121  → Output: false
Input: 10    → Output: false
```

### Approach
**Method 1 (Brute):** Convert to string, check if equals its reverse
**Method 2 (Optimal):** Reverse the number itself, compare with original
  - Early exit: if negative or (ends in 0 but not 0 itself) → false
  - Only reverse half the number to avoid overflow

### Java Solution
```java
public class PalindromeNumber {
    public boolean isPalindrome(int x) {
        // Negative or ends in 0 (except 0 itself)
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        
        int reversedHalf = 0;
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }
        // x == reversedHalf  (even digits: 1221)
        // x == reversedHalf/10  (odd digits: 12321, middle digit ignored)
        return x == reversedHalf || x == reversedHalf / 10;
    }
    
    public static void main(String[] args) {
        PalindromeNumber obj = new PalindromeNumber();
        System.out.println(obj.isPalindrome(121));   // true
        System.out.println(obj.isPalindrome(-121));  // false
        System.out.println(obj.isPalindrome(1221));  // true
        System.out.println(obj.isPalindrome(10));    // false
    }
}
```

### Complexity
- Time: O(log N)
- Space: O(1)

---

## Problem 4 — Armstrong Number
**Platform Links:** [GFG](https://practice.geeksforgeeks.org/problems/armstrong-numbers2727/1) \| [Coding Ninjas](https://www.codingninjas.com/studio/problems/check-armstrong_589)
**Difficulty:** Easy

### Problem Statement
A number is an Armstrong number if the sum of its digits raised to the power
of the number of digits equals the number itself.
Example: 153 = 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153

### Examples
```
Input: 153  → Output: true   (1^3 + 5^3 + 3^3 = 153)
Input: 370  → Output: true   (3^3 + 7^3 + 0^3 = 370)
Input: 123  → Output: false
```

### Java Solution
```java
public class ArmstrongNumber {
    public static boolean isArmstrong(int n) {
        int original = n;
        int digits = String.valueOf(n).length(); // count digits
        int sum = 0;
        
        while (n > 0) {
            int digit = n % 10;
            sum += (int) Math.pow(digit, digits);
            n /= 10;
        }
        return sum == original;
    }
    
    public static void main(String[] args) {
        System.out.println(isArmstrong(153));  // true
        System.out.println(isArmstrong(370));  // true
        System.out.println(isArmstrong(9474)); // true (4 digits: 9^4+4^4+7^4+4^4)
        System.out.println(isArmstrong(123));  // false
    }
}
```

### Complexity
- Time: O(log N)
- Space: O(1)

---

## Problem 5 — Print All Divisors of a Number
**Platform Links:** [GFG](https://practice.geeksforgeeks.org/problems/all-divisors-of-a-number/1) \| [Coding Ninjas](https://www.codingninjas.com/studio/problems/print-all-divisors-of-a-number_1164188)
**Difficulty:** Easy

### Problem Statement
Given a number N, print all its divisors in sorted order.

### Examples
```
Input: 36  → Output: 1 2 3 4 6 9 12 18 36
Input: 12  → Output: 1 2 3 4 6 12
```

### Approach
**Brute Force:** Loop from 1 to N → O(N)
**Optimal:** Loop from 1 to sqrt(N), collect pairs → O(sqrt N)
  - If i divides N → both i and N/i are divisors
  - Handle perfect squares (i == N/i) separately

### Java Solution
```java
import java.util.*;

public class PrintDivisors {
    public static void printDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i != n / i) {  // avoid duplicate for perfect square
                    divisors.add(n / i);
                }
            }
        }
        
        Collections.sort(divisors);
        System.out.println(divisors);
    }
    
    public static void main(String[] args) {
        printDivisors(36); // [1, 2, 3, 4, 6, 9, 12, 18, 36]
        printDivisors(12); // [1, 2, 3, 4, 6, 12]
    }
}
```

### Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute | O(N) | O(N) |
| Optimal | O(sqrt N) | O(N) |

---

## Daily Summary
| # | Problem | Difficulty | Key Concept | Status |
|---|---------|------------|-------------|--------|
| 1 | Count Digits | Easy | Math.log10 / division | ✅ |
| 2 | Reverse a Number | Medium | Mod + Division + Overflow check | ✅ |
| 3 | Palindrome Number | Easy | Reverse half | ✅ |
| 4 | Armstrong Number | Easy | Math.pow + digit count | ✅ |
| 5 | Print All Divisors | Easy | sqrt(N) trick | ✅ |

## Key Takeaways from Day 1
1. Always think about **overflow** — use `long` when int might overflow
2. The **sqrt(N) trick** reduces O(N) divisor finding to O(sqrt N)
3. For palindrome, reversing only **half** avoids overflow
4. `Math.abs()` for handling negatives, `Math.log10()` for digit count
5. Always handle **edge case N=0** separately

---
*Next: Day 2 → GCD/LCM, Prime Numbers, Sieve of Eratosthenes*
