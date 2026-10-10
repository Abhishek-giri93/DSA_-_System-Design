# Problem 3: Implement Upper Bound

### 🔗 Platform Practice Links:
- **Coding Ninjas (Studio):** [Implement Upper Bound](https://www.codingninjas.com/studio/problems/implement-upper-bound_8165383)
- **GeeksforGeeks (GFG):** [Ceil The Floor](https://practice.geeksforgeeks.org/problems/ceil-the-floor2802/1)
- **LeetCode (Problem 744):** [Find Smallest Letter Greater Than Target](https://leetcode.com/problems/find-smallest-letter-greater-than-target/) *(Character version of Upper Bound)*
- **LeetCode (Problem 34):** [Find First and Last Position](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) *(Uses Upper Bound to find right boundary)*

---

## 1. Problem Statement
**What is the problem asking?**
You are given a sorted array of integers `nums` in non-decreasing order and an integer `target` (or `x`).
Find the **upper bound** of `target`.

- **Definition of Upper Bound:** The upper bound is the **smallest index `i`** such that `nums[i] > target`.
- If no such element exists (i.e., all elements in the array are $\le target$), the answer is `n` (the length of the array).

> **Key Distinction:**
> - **Lower Bound:** Smallest index where `nums[i] >= target`
> - **Upper Bound:** Smallest index where `nums[i] > target` (strictly greater!)

**Constraints:**
- Must run in **`O(log n)`** time complexity.
- Array length: $1 \le n \le 10^5$.
- Elements and target: $-10^9 \le nums[i], target \le 10^9$.

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [2, 4, 6, 7, 9]`, `target = 6`
- **Output:** `3`
- **Why?** Elements are:
  - index `2`: `6` (not strictly greater than 6)
  - index `3`: `7` (strictly greater than 6) -> smallest index where `nums[i] > 6`.

**Example 2:**
- **Input:** `nums = [1, 2, 4, 4, 4, 6, 7]`, `target = 4`
- **Output:** `5`
- **Why?** Indices 2, 3, 4 have value `4`. The first element strictly greater than `4` is at index `5` (value `6`).

**Example 3 (Target greater than or equal to all elements):**
- **Input:** `nums = [2, 5, 8]`, `target = 10`
- **Output:** `3` (or `n`)
- **Why?** There is no element strictly greater than `10`, so we return `n = 3`.

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search (Boundary condition / Strict inequality).
**Why does this pattern apply here?**
1. The array is sorted in ascending order.
2. The condition `nums[i] > target` is monotonic (false, false, ..., false, true, true, ..., true).
3. We are finding the first `true` occurrence in `O(log n)` time.

---

## 4. Brute-Force Approach
Scan linearly from left to right and return the first index where `nums[i] > target`.
```java
public int upperBoundLinear(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > target) {
            return i;
        }
    }
    return nums.length;
}
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`
- **Bottleneck:** Scans every element instead of dividing the search space in half.

---

## 5. Optimization and Intuition
We use Binary Search:
- Calculate `mid = left + (right - left) / 2`.
- If `nums[mid] > target`:
  - `mid` is a valid candidate for upper bound.
  - An even smaller valid index might exist on the left.
  - Store `ans = mid` and search left: `right = mid - 1`.
- If `nums[mid] <= target`:
  - `mid` and all elements to its left are $\le target$.
  - They cannot be the answer.
  - Search right: `left = mid + 1`.

---

## 6. Algorithm
1. Initialize `left = 0`, `right = nums.length - 1`, and `ans = nums.length`.
2. Loop while `left <= right`:
   - Compute `mid = left + (right - left) / 2`.
   - If `nums[mid] > target`:
     - Update `ans = mid`.
     - Move left: `right = mid - 1`.
   - Else (`nums[mid] <= target`):
     - Move right: `left = mid + 1`.
3. Return `ans` (or `left`, since `left` naturally converges to the upper bound).

---

## 7. Visual Dry Run
**Input:** `nums = [2, 4, 6, 7, 9]`, `target = 6`

| Step | `left` | `right` | `mid` | `nums[mid]` | Condition (`> 6`?) | Action | `ans` |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 4 | 2 | 6 | $6 > 6$ ❌ False | `left = mid + 1 = 3` | 5 |
| 2 | 3 | 4 | 3 | 7 | $7 > 6$ ✅ True | `ans = 3`, `right = mid - 1 = 2` | 3 |
| Loop ends (`left > right`) | | | | | | Return `ans = 3` | 3 |

---

## 8. Java Implementation

```java
public class Solution {
    public int upperBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int ans = nums.length; // Default to n if no element > target
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > target) {
                ans = mid;           // Potential upper bound found
                right = mid - 1;     // Look for an earlier index to the left
            } else {
                left = mid + 1;      // nums[mid] <= target, search right
            }
        }
        
        return ans; // Or directly return left
    }
}
```

---

## 9. Complexity Analysis
- **Time Complexity:** `O(log n)` — Search space is halved each step.
- **Space Complexity:** `O(1)` — Only pointer variables used.

---

## 10. Key Takeaways: Lower Bound vs Upper Bound

| Concept | Condition | What it finds | C++ Equivalent |
|---|---|---|---|
| **Lower Bound** | `nums[mid] >= target` | Smallest index with value $\ge target$ | `std::lower_bound` |
| **Upper Bound** | `nums[mid] > target` | Smallest index with value $> target$ | `std::upper_bound` |

### Super Useful Application: Frequency of Element in Sorted Array
Using Lower Bound and Upper Bound together:
$$\text{Count}(target) = \text{upper\_bound}(target) - \text{lower\_bound}(target)$$

---

## 11. Similar Practice Problems
1. **[LeetCode 744 - Find Smallest Letter Greater Than Target](https://leetcode.com/problems/find-smallest-letter-greater-than-target/)**
2. **[LeetCode 34 - Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)**
3. **[GFG - Floor in a Sorted Array](https://practice.geeksforgeeks.org/problems/floor-in-a-sorted-array-1587115620/1)**
4. **[Coding Ninjas - Implement Lower Bound](https://www.codingninjas.com/studio/problems/lower-bound_8165382)**
