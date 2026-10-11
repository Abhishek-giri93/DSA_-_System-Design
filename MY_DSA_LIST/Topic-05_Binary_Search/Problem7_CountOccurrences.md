# Problem 7: Count Occurrences in Sorted Array

### 🔗 Platform Practice Links:
- **GeeksforGeeks (GFG):** [Number of Occurrence](https://practice.geeksforgeeks.org/problems/number-of-occurrence2259/1)
- **Coding Ninjas (Studio):** [Occurrence of X in a Sorted Array](https://www.codingninjas.com/studio/problems/occurrence-of-x-in-a-sorted-array_630456)
- **LeetCode (Problem 34):** [Find First and Last Position](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) *(Direct parent problem)*

---

## 1. Problem Statement
**What is the problem asking?**
Given a sorted array of `n` integers and a target number `target`, calculate the **total count of occurrences** of `target` in the array.

- If `target` is present, return the number of times it appears.
- If `target` does not appear, return `0`.

You must write an algorithm with **`O(log n)`** runtime complexity.

**Constraints:**
- $1 \le n \le 10^5$
- $1 \le nums[i], target \le 10^9$
- Array is sorted in non-decreasing order.

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [1, 1, 2, 2, 2, 2, 3]`, `target = 2`
- **Output:** `4`
- **Why?** Number `2` appears at indices 2, 3, 4, 5. Total count = 4.

**Example 2:**
- **Input:** `nums = [1, 1, 2, 2, 2, 2, 3]`, `target = 4`
- **Output:** `0`
- **Why?** Number `4` does not appear in `nums`.

**Example 3:**
- **Input:** `nums = [8, 9, 10, 12, 12, 12]`, `target = 12`
- **Output:** `3`
- **Why?** First occurrence is at index 3, last occurrence is at index 5. Count = $5 - 3 + 1 = 3$.

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search / Lower & Upper Bound.
**Why does this pattern apply here?**
Because the array is sorted, all identical elements appear in a **contiguous block**.
If we can locate:
1. The **first occurrence** (`firstIndex`)
2. The **last occurrence** (`lastIndex`)

Then the total count is simply:
$$\text{Total Occurrences} = \text{lastIndex} - \text{firstIndex} + 1$$
If `firstIndex == -1` (meaning `target` is not found), the count is `0`.

Alternatively, using bounds:
$$\text{Total Occurrences} = \text{upper\_bound}(target) - \text{lower\_bound}(target)$$

---

## 4. Brute-Force Approach
A simple linear scan over the array:
```java
public int countOccurrencesLinear(int[] nums, int target) {
    int count = 0;
    for (int num : nums) {
        if (num == target) {
            count++;
        }
    }
    return count;
}
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`
- **Bottleneck:** In an array of $10^5$ elements, checking every element takes linear time. Does not exploit sorted order.

---

## 5. Optimization and Intuition

We can achieve **`O(log n)`** by using Binary Search:
1. Find `first`:
   - Search with `nums[mid] == target` $\rightarrow$ save index, move `right = mid - 1`.
2. If `first == -1`: target is absent, return `0` immediately!
3. Find `last`:
   - Search with `nums[mid] == target` $\rightarrow$ save index, move `left = mid + 1`.
4. Return `(last - first + 1)`.

---

## 6. Algorithm
1. Initialize `first = -1` and `last = -1`.
2. Binary search to find the earliest occurrence (`first`):
   - `left = 0`, `right = n - 1`
   - While `left <= right`:
     - `mid = left + (right - left) / 2`
     - If `nums[mid] == target`: `first = mid`, `right = mid - 1`.
     - Else if `nums[mid] < target`: `left = mid + 1`.
     - Else: `right = mid - 1`.
3. If `first == -1`, return `0`.
4. Binary search to find the latest occurrence (`last`):
   - `left = 0`, `right = n - 1`
   - While `left <= right`:
     - `mid = left + (right - left) / 2`
     - If `nums[mid] == target`: `last = mid`, `left = mid + 1`.
     - Else if `nums[mid] < target`: `left = mid + 1`.
     - Else: `right = mid - 1`.
5. Return `last - first + 1`.

---

## 7. Visual Dry Run
**Input:** `nums = [1, 1, 2, 2, 2, 2, 3]`, `target = 2`

- Indices: `0, 1, 2, 3, 4, 5, 6`
- Values: `[1, 1, 2, 2, 2, 2, 3]`

1. **Find First Occurrence:**
   - `left = 0`, `right = 6`, `mid = 3`, `nums[3] = 2` $\rightarrow$ `first = 3`, `right = 2`.
   - `left = 0`, `right = 2`, `mid = 1`, `nums[1] = 1 < 2` $\rightarrow$ `left = 2`.
   - `left = 2`, `right = 2`, `mid = 2`, `nums[2] = 2` $\rightarrow$ `first = 2`, `right = 1`.
   - Loop ends. **`first = 2`**.

2. **Find Last Occurrence:**
   - `left = 0`, `right = 6`, `mid = 3`, `nums[3] = 2` $\rightarrow$ `last = 3`, `left = 4`.
   - `left = 4`, `right = 6`, `mid = 5`, `nums[5] = 2` $\rightarrow$ `last = 5`, `left = 6`.
   - `left = 6`, `right = 6`, `mid = 6`, `nums[6] = 3 > 2` $\rightarrow$ `right = 5`.
   - Loop ends. **`last = 5`**.

3. **Formula:**
   $$\text{Count} = 5 - 2 + 1 = 4$$

✅ Correct Answer = `4`.

---

## 8. Java Implementation

```java
public class Solution {
    public int count(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int first = findFirst(nums, target);
        // If target is not present at all, count is 0
        if (first == -1) {
            return 0;
        }

        int last = findLast(nums, target);
        return last - first + 1;
    }

    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int firstIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                firstIndex = mid;
                right = mid - 1; // Look left for first position
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return firstIndex;
    }

    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int lastIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                lastIndex = mid;
                left = mid + 1; // Look right for last position
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return lastIndex;
    }
}
```

---

## 9. Complexity Analysis
- **Time Complexity:** `O(log n)` — Running two binary searches takes $O(\log n) + O(\log n) = O(\log n)$.
- **Space Complexity:** `O(1)` — Uses a few primitive variables.

---

## 10. Deep Insight: Using Lower and Upper Bound

If you already have utility functions for Lower Bound and Upper Bound:
```java
int lower = lowerBound(nums, target);
int upper = upperBound(nums, target);
return upper - lower;
```
- `lowerBound(x)`: First index where `nums[i] >= x`.
- `upperBound(x)`: First index where `nums[i] > x`.
- The number of elements equal to `x` is **always** `upperBound(x) - lowerBound(x)`.
- If `x` does not exist in the array, `lowerBound(x)` and `upperBound(x)` will both return the identical insertion index, making `upper - lower = 0` automatically!

---

## 11. Similar Practice Problems
1. **[LeetCode 34 - Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)**
2. **[Coding Ninjas - Implement Lower Bound](https://www.codingninjas.com/studio/problems/lower-bound_8165382)**
3. **[Coding Ninjas - Implement Upper Bound](https://www.codingninjas.com/studio/problems/implement-upper-bound_8165383)**
