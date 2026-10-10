# Problem 2: Implement Lower Bound / Search Insert Position

### 🔗 Platform Practice Links:
- **Coding Ninjas (Studio):** [Implement Lower Bound](https://www.codingninjas.com/studio/problems/lower-bound_8165382)
- **LeetCode (Problem 35):** [Search Insert Position](https://leetcode.com/problems/search-insert-position/)
- **GeeksforGeeks (GFG):** [Floor in a Sorted Array](https://practice.geeksforgeeks.org/problems/floor-in-a-sorted-array-1587115620/1) \| [Search Insert Position of K](https://practice.geeksforgeeks.org/problems/search-insert-position-of-k-in-a-sorted-array/1)

---

## 1. Problem Statement
**What is the problem asking?**
You are given a sorted array of integers `nums` in non-decreasing order and an integer `target` (or `x`).
Find the **lower bound** of `target`.

- **Definition of Lower Bound:** The lower bound is the **smallest index `i`** such that `nums[i] >= target`.
- If no such element exists (i.e., every element in the array is strictly less than `target`), the answer is `n` (the length of the array).

> **Note:** In LeetCode 35 (*Search Insert Position*), the problem asks for the index where `target` should be inserted to maintain sorted order, which is the exact mathematical definition of **Lower Bound**!

**Constraints:**
- Must run in **`O(log n)`** time complexity.
- Array length: $1 \le n \le 10^5$.
- Elements and target: $-10^9 \le nums[i], target \le 10^9$.

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [1, 2, 4, 6, 8]`, `target = 5`
- **Output:** `3`
- **Why?** At index `3`, `nums[3] = 6`, which is the first element $\ge 5$.

**Example 2:**
- **Input:** `nums = [1, 2, 4, 6, 8]`, `target = 4`
- **Output:** `2`
- **Why?** At index `2`, `nums[2] = 4`, which satisfies `nums[i] >= 4`.

**Example 3 (Target greater than all elements):**
- **Input:** `nums = [1, 2, 4, 6, 8]`, `target = 10`
- **Output:** `5` (or `n`)
- **Why?** Every number in the array is $< 10$, so the insert position is at index `n = 5`.

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search (Find first occurrence / Boundary condition).
**Why does this pattern apply here?**
1. The array is strictly sorted.
2. We are searching for a transition point/boundary: all elements to the left are `< target`, and all elements from the lower bound to the end are `>= target`.
3. The required time complexity is `O(log n)`.

---

## 4. Brute-Force Approach
Linearly iterate through the array from left to right and return the first index where `nums[i] >= target`.
```java
public int lowerBoundLinear(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] >= target) {
            return i;
        }
    }
    return nums.length;
}
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`
- **Bottleneck:** Scans element by element instead of halving the search space.

---

## 5. Optimization and Intuition
Since the array is sorted, we can divide and conquer:
- Pick the middle element `nums[mid]`.
- If `nums[mid] >= target`:
  - `mid` is a valid candidate for the lower bound!
  - But there might be a smaller index to the left that also satisfies the condition.
  - So, save `ans = mid` and search the **left half** (`right = mid - 1`).
- If `nums[mid] < target`:
  - `mid` and everything to its left cannot be the answer.
  - Search the **right half** (`left = mid + 1`).

---

## 6. Algorithm
1. Initialize `left = 0`, `right = nums.length - 1`, and `ans = nums.length`.
2. Loop while `left <= right`:
   - Compute `mid = left + (right - left) / 2`.
   - If `nums[mid] >= target`:
     - Update `ans = mid`.
     - Move left: `right = mid - 1`.
   - Else (`nums[mid] < target`):
     - Move right: `left = mid + 1`.
3. Return `ans` (or simply return `left` at the end, since `left` naturally converges to the lower bound index).

---

## 7. Visual Dry Run
**Input:** `nums = [1, 3, 5, 6]`, `target = 5`

| Step | `left` | `right` | `mid` | `nums[mid]` | Condition (`>= 5`?) | Action | `ans` |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 3 | 1 | 3 | $3 \ge 5$ ❌ False | `left = mid + 1 = 2` | 4 |
| 2 | 2 | 3 | 2 | 5 | $5 \ge 5$ ✅ True | `ans = 2`, `right = mid - 1 = 1` | 2 |
| Loop ends (`left > right`) | | | | | | Return `ans = 2` | 2 |

---

## 8. Java Implementation

```java
public class Solution {
    public int lowerBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int ans = nums.length; // Default to n if no element >= target
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] >= target) {
                ans = mid;           // Potential answer found
                right = mid - 1;     // Look for an even smaller index on the left
            } else {
                left = mid + 1;      // Need larger values on the right
            }
        }
        
        return ans;
    }
}
```

*Note:* You can also directly return `left` because after the while loop terminates, `left` always points to the exact lower bound index!

---

## 9. Complexity Analysis
- **Time Complexity:** `O(log n)` — Halves the search range in each iteration.
- **Space Complexity:** `O(1)` — Only uses pointer variables.

---

## 10. Key Takeaways & Common Pitfalls
1. **Lower Bound vs Upper Bound:**
   - **Lower Bound:** Smallest index where `nums[i] >= target`.
   - **Upper Bound:** Smallest index where `nums[i] > target`.
2. **Default value:** If `target` is greater than all elements, the answer is `n` (array length), not `-1`.
3. **Template mastery:** This exact logic solves **LeetCode 35 (Search Insert Position)** and is the core building block for counting frequencies and finding boundaries in rotated/sorted arrays.

---

## 11. Similar Practice Problems
1. **[LeetCode 35 - Search Insert Position](https://leetcode.com/problems/search-insert-position/)**
2. **[LeetCode 34 - Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)**
3. **[GFG - Ceil The Floor](https://practice.geeksforgeeks.org/problems/ceil-the-floor2802/1)**
4. **[Coding Ninjas - Implement Upper Bound](https://www.codingninjas.com/studio/problems/implement-upper-bound_8165383)**
