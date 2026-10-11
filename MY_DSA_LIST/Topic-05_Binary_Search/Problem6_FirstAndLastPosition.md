# Problem 6: Find First and Last Position of Element in Sorted Array (LeetCode 34)

### 🔗 Platform Practice Links:
- **LeetCode (Problem 34):** [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
- **GeeksforGeeks (GFG):** [First and Last Occurrences of x](https://practice.geeksforgeeks.org/problems/first-and-last-occurrences-of-x3116/1)
- **Coding Ninjas (Studio):** [First and Last Position of an Element in Sorted Array](https://www.codingninjas.com/studio/problems/first-and-last-position-of-an-element-in-sorted-array_1082549)

---

## 1. Problem Statement
**What is the problem asking?**
Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value.

- If `target` is found in the array, return `[firstIndex, lastIndex]`.
- If `target` is not found, return `[-1, -1]`.

You must write an algorithm with **`O(log n)`** runtime complexity.

**Constraints:**
- $0 \le nums.length \le 10^5$
- $-10^9 \le nums[i] \le 10^9$
- `nums` is a non-decreasing array.
- $-10^9 \le target \le 10^9$

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [5, 7, 7, 8, 8, 10]`, `target = 8`
- **Output:** `[3, 4]`
- **Why?** `8` first appears at index `3` and last appears at index `4`.

**Example 2:**
- **Input:** `nums = [5, 7, 7, 8, 8, 10]`, `target = 6`
- **Output:** `[-1, -1]`
- **Why?** `6` is not present in `nums`.

**Example 3:**
- **Input:** `nums = []`, `target = 0`
- **Output:** `[-1, -1]`
- **Why?** The array is empty.

**Example 4 (Single element matching):**
- **Input:** `nums = [1]`, `target = 1`
- **Output:** `[0, 0]`
- **Why?** Index `0` is both the first and last position.

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search with Boundary Shrinking / Modified Binary Search.
**Why does this pattern apply here?**
1. The array is **sorted**.
2. When we find an occurrence `nums[mid] == target`, standard binary search stops immediately. However, here:
   - For the **first occurrence**, there could be another target to the left $\rightarrow$ update `first = mid` and continue searching left (`right = mid - 1`).
   - For the **last occurrence**, there could be another target to the right $\rightarrow$ update `last = mid` and continue searching right (`left = mid + 1`).

Alternatively, this can be solved using Lower Bound and Upper Bound:
- `firstPosition = lower_bound(target)` (if `nums[lower_bound] == target`, else `-1`)
- `lastPosition = upper_bound(target) - 1`

---

## 4. Brute-Force Approach
Perform a single linear scan from left to right:
```java
public int[] searchRangeLinear(int[] nums, int target) {
    int first = -1, last = -1;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) {
            if (first == -1) first = i;
            last = i;
        }
    }
    return new int[]{first, last};
}
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`
- **Bottleneck:** Fails the required `O(log n)` time constraint when $n = 10^5$.

---

## 5. Optimization and Intuition

We can run **two independent Binary Searches**:

### 1. First Occurrence:
- Standard binary search condition:
  - If `nums[mid] == target`: record `ans = mid`. Since we want the *first* occurrence, an earlier index could be to the left $\rightarrow$ set `right = mid - 1`.
  - If `nums[mid] < target`: target must be in the right half $\rightarrow$ `left = mid + 1`.
  - If `nums[mid] > target`: target must be in the left half $\rightarrow$ `right = mid - 1`.

### 2. Last Occurrence:
- If `nums[mid] == target`: record `ans = mid`. Since we want the *last* occurrence, a later index could be to the right $\rightarrow$ set `left = mid + 1`.
- If `nums[mid] < target`: `left = mid + 1`.
- If `nums[mid] > target`: `right = mid - 1`.

---

## 6. Algorithm
1. Check edge cases: if `nums == null || nums.length == 0`, return `[-1, -1]`.
2. Find `first = findFirst(nums, target)`.
3. If `first == -1` (target does not exist in array), return `[-1, -1]` immediately without running the second search!
4. Find `last = findLast(nums, target)`.
5. Return `[first, last]`.

---

## 7. Visual Dry Run
**Input:** `nums = [5, 7, 7, 8, 8, 10]`, `target = 8`

### Finding First Occurrence:
| Step | `left` | `right` | `mid` | `nums[mid]` | Match? | Action | `first` |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 5 | 2 | 7 | $7 < 8$ | `left = mid + 1 = 3` | -1 |
| 2 | 3 | 5 | 4 | 8 | $8 == 8$ ✅ | `first = 4`, `right = mid - 1 = 3` | 4 |
| 3 | 3 | 3 | 3 | 8 | $8 == 8$ ✅ | `first = 3`, `right = mid - 1 = 2` | 3 |
| End | 3 | 2 | — | — | `left > right` | Return `first = 3` | **3** |

### Finding Last Occurrence:
| Step | `left` | `right` | `mid` | `nums[mid]` | Match? | Action | `last` |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 5 | 2 | 7 | $7 < 8$ | `left = mid + 1 = 3` | -1 |
| 2 | 3 | 5 | 4 | 8 | $8 == 8$ ✅ | `last = 4`, `left = mid + 1 = 5` | 4 |
| 3 | 5 | 5 | 5 | 10 | $10 > 8$ | `right = mid - 1 = 4` | 4 |
| End | 5 | 4 | — | — | `left > right` | Return `last = 4` | **4** |

Result: `[3, 4]`.

---

## 8. Java Implementation

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }

        int first = findFirst(nums, target);
        // Optimization: If target is not present, skip the second binary search
        if (first == -1) {
            return new int[]{-1, -1};
        }
        
        int last = findLast(nums, target);
        return new int[]{first, last};
    }

    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int firstIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                firstIndex = mid;      // Candidate first occurrence found
                right = mid - 1;       // Continue searching left
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
                lastIndex = mid;       // Candidate last occurrence found
                left = mid + 1;        // Continue searching right
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
- **Time Complexity:** `O(log n)` — Each binary search takes $O(\log n)$ time. $2 \times O(\log n) = O(\log n)$.
- **Space Complexity:** `O(1)` — Only pointer variables are stored.

---

## 10. Deep Insight: Solving via Lower Bound & Upper Bound
We can also solve this problem elegantly in one helper function using **Lower Bound**:
1. `first = lowerBound(nums, target)`
   - If `first == nums.length` or `nums[first] != target`, target does not exist $\rightarrow$ return `[-1, -1]`.
2. `last = lowerBound(nums, target + 1) - 1`
   - Lower bound of `target + 1` finds the first element strictly $> target$, which is the Upper Bound of `target`.
   - The element just before it (`index - 1`) is the last occurrence of `target`!

```java
// Alternative concise approach:
int first = lowerBound(nums, target);
if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
int last = lowerBound(nums, target + 1) - 1;
return new int[]{first, last};
```

---

## 11. Similar Practice Problems
1. **[GFG - Number of Occurrence](https://practice.geeksforgeeks.org/problems/number-of-occurrence2259/1)** *(Count occurrences = last - first + 1)*
2. **[LeetCode 704 - Binary Search](https://leetcode.com/problems/binary-search/)**
3. **[LeetCode 278 - First Bad Version](https://leetcode.com/problems/first-bad-version/)**
4. **[LeetCode 852 - Peak Index in a Mountain Array](https://leetcode.com/problems/peak-index-in-a-mountain-array/)**
