import java.util.*;

public class Day01_Solutions {

    // P1: Count Digits - O(log N) time, O(1) space
    public static int countDigits(int n) {
        if (n == 0) return 1;
        n = Math.abs(n);
        int count = 0;
        while (n > 0) { count++; n /= 10; }
        return count;
    }

    // P2: Reverse Number - LeetCode #7 - O(log N) time, O(1) space
    public static int reverseNumber(int x) {
        long rev = 0;
        while (x != 0) {
            int digit = x % 10;
            rev = rev * 10 + digit;
            x /= 10;
        }
        if (rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE) return 0;
        return (int) rev;
    }

    // P3: Palindrome Number - LeetCode #9 - O(log N) time, O(1) space
    public static boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int reversedHalf = 0;
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }
        return x == reversedHalf || x == reversedHalf / 10;
    }

    // P4: Armstrong Number - O(log N) time, O(1) space
    public static boolean isArmstrong(int n) {
        int original = n;
        int digits = String.valueOf(n).length();
        int sum = 0, temp = n;
        while (temp > 0) {
            int digit = temp % 10;
            sum += (int) Math.pow(digit, digits);
            temp /= 10;
        }
        return sum == original;
    }

    // P5: Print All Divisors - O(sqrt N) time, O(d) space
    public static List<Integer> printDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i != n / i) divisors.add(n / i);
            }
        }
        Collections.sort(divisors);
        return divisors;
    }

    public static void main(String[] args) {
        System.out.println("===== DAY 1 SOLUTIONS =====");
        System.out.println("P1 - Count Digits:");
        System.out.println("  12345 -> " + countDigits(12345));
        System.out.println("  0     -> " + countDigits(0));
        System.out.println("  -999  -> " + countDigits(-999));
        System.out.println("P2 - Reverse Number:");
        System.out.println("  123   -> " + reverseNumber(123));
        System.out.println("  -123  -> " + reverseNumber(-123));
        System.out.println("  120   -> " + reverseNumber(120));
        System.out.println("P3 - Is Palindrome:");
        System.out.println("  121   -> " + isPalindrome(121));
        System.out.println("  -121  -> " + isPalindrome(-121));
        System.out.println("  1221  -> " + isPalindrome(1221));
        System.out.println("  10    -> " + isPalindrome(10));
        System.out.println("P4 - Is Armstrong:");
        System.out.println("  153   -> " + isArmstrong(153));
        System.out.println("  370   -> " + isArmstrong(370));
        System.out.println("  9474  -> " + isArmstrong(9474));
        System.out.println("  123   -> " + isArmstrong(123));
        System.out.println("P5 - Print Divisors:");
        System.out.println("  36    -> " + printDivisors(36));
        System.out.println("  12    -> " + printDivisors(12));
    }
}
