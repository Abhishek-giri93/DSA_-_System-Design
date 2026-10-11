# Problem 9: Search in Rotated Sorted Array II (LeetCode 81)

### 🔗 Platform Practice Links:
- **LeetCode (Problem 81):** [Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)
- **GeeksforGeeks (GFG):** [Search in a Rotated Array 2](https://practice.geeksforgeeks.org/problems/search-in-rotated-array-2/1)
- **Coding Ninjas (Studio):** [Search In A Rotated Sorted Array II](https://www.codingninjas.com/studio/problems/search-in-a-rotated-sorted-array-ii_7449547)

---

## 1. Problem Statement
**What is the problem asking?**
There is an integer array `nums` sorted in non-decreasing order (not necessarily with distinct values).

Before being passed to your function, `nums` is rotated at an unknown pivot index `k` ($0 \le k < nums.length$) such that the resulting array is:
`[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`.

Given the array `nums` after rotation and an integer `target`, return `true` if `target` is in `nums`, or `false` if it is not in `nums`.

You must decrease the overall operation steps as much as possible.

**Constraints:**
- $1 \le nums.length \le 5000$
- $-10^4 \le nums[i], target \le 10^4$
- `nums` is guaranteed to be rotated at some pivot.
- **Array contains duplicate values.**

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [2, 5, 6, 0, 0, 1, 2]`, `target = 0`
- **Output:** `true`
- **Why?** Number `0` exists in `nums`.

**Example 2:**
- **Input:** `nums = [2, 5, 6, 0, 0, 1, 2]`, `target = 3`
- **Output:** `false`
- **Why?** Number `3` is not present in `nums`.

**Example 3 (The tricky duplicate scenario):**
- **Input:** `nums = [3, 1, 2, 3, 3, 3, 3]`, `target = 1`
- **Output:** `true`
- **Why?** `nums[left] == 3`, `nums[mid] == 3`, and `nums[right] == 3`. We cannot determine whether the left or right half is sorted!

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search with Duplicate Trimming / Edge-Case Resolution.
**Why does standard Binary Search (Problem 8) fail here?**
In Problem 8 (distinct elements), `nums[left] <= nums[mid]` strictly meant the left half was sorted.
However, with duplicates:
Suppose `nums = [3, 1, 2, 3, 3, 3, 3]`.
Here: `nums[left] = 3`, `nums[mid] = 3`, `nums[right] = 3`.
- Left half `[3, 1, 2, 3]` is **NOT** sorted!
- Right half `[3, 3, 3]` **IS** sorted!
Because `nums[left] == nums[mid] == nums[right]`, we cannot tell which side is sorted!

**The Golden Resolution:**
Whenever `nums[left] == nums[mid] && nums[mid] == nums[right]`:
Since `nums[mid] != target`, neither `left` nor `right` can be our target. We can safely shrink both bounds:
$$\text{left} = \text{left} + 1, \quad \text{right} = \text{right} - 1$$
This trims the duplicates and allows normal binary search to resume!

---

## 4. Brute-Force Approach
A linear search checking every element:
```java
public boolean searchLinear(int[] nums, int target) {
    for (int num : nums) {
        if (num == target) return true;
    }
    return false;
}
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`
- **Limitation:** Does not utilize the partial sorted properties of the array.

---

## 5. Optimization and Intuition

1. At any step, compute `mid = left + (right - left) / 2`.
2. If `nums[mid] == target`, return `true` immediately.
3. **Handle Duplicates Edge Case:**
   - If `nums[left] == nums[mid] && nums[mid] == nums[right]`:
     - Shrink both sides: `left++`, `right--`, and continue to next iteration.
4. **Otherwise, proceed as in Rotated Sorted Array I:**
   - **If Left Half is Sorted (`nums[left] <= nums[mid]`):**
     - If `nums[left] <= target && target < nums[mid]`: search left (`right = mid - 1`).
     - Else: search right (`left = mid + 1`).
   - **If Right Half is Sorted (`nums[mid] <= nums[right]`):**
     - If `nums[mid] < target && target <= nums[right]`: search right (`left = mid + 1`).
     - Else: search left (`right = mid - 1`).

---

## 6. Algorithm
1. Initialize `left = 0`, `right = nums.length - 1`.
2. Loop while `left <= right`:
   - Compute `mid = left + (right - left) / 2`.
   - If `nums[mid] == target`: return `true`.
   - If `nums[left] == nums[mid] && nums[mid] == nums[right]`:
     - `left++`
     - `right--`
     - `continue`
   - If left half is sorted (`nums[left] <= nums[mid]`):
     - If `nums[left] <= target && target < nums[mid]`: `right = mid - 1`.
     - Else: `left = mid + 1`.
   - Else (right half is sorted):
     - If `nums[mid] < target && target <= nums[right]`: `left = mid + 1`.
     - Else: `right = mid - 1`.
3. If loop terminates, return `false`.

---

## 7. Visual Dry Run
**Input:** `nums = [3, 1, 2, 3, 3, 3, 3]`, `target = 1`

| Step | `left` | `right` | `mid` | `nums[mid]` | Condition | Action |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 6 | 3 | 3 | `nums[0] == 3`, `nums[3] == 3`, `nums[6] == 3` | Trimming: `left = 1`, `right = 5` |
| 2 | 1 | 5 | 3 | 3 | `nums[1] = 1`, `nums[3] = 3` ($1 \le 3$, left sorted) | $1 \le 1 < 3$ ✅ `target` in left half $\rightarrow$ `right = mid - 1 = 2` |
| 3 | 1 | 2 | 1 | 1 | `nums[1] == 1 == target` ✅ | Target matched! Return `true` |

✅ Correct Output: `true`.

---

## 8. Java Implementation

```java
class Solution {
    public boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return true; // Target found
            }

            // Edge Case: When elements at left, mid, and right are all identical
            // We cannot identify which half is sorted, so trim the search space from both ends
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                left++;
                right--;
                continue;
            }

            // Check if the left half is sorted
            if (nums[left] <= nums[mid]) {
                // Check if target lies within the sorted left half
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1; // Search left
                } else {
                    left = mid + 1;  // Search right
                }
            } 
            // Otherwise, the right half must be sorted
            else {
                // Check if target lies within the sorted right half
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;  // Search right
                } else {
                    right = mid - 1; // Search left
                }
            }
        }

        return false; // Target not found
    }
}
```

---

## 9. Complexity Analysis
- **Time Complexity:**
  - **Average Case:** `O(log n)` — When duplicates are few or absent, the search space halves in every step.
  - **Worst Case:** `O(n / 2) = O(n)` — When almost all elements are duplicates (e.g., `[3, 3, 3, 3, 3, 1, 3]`), we shrink `left` and `right` by 1 at each step, degenerating to linear scan.
- **Space Complexity:** `O(1)` — Only pointer variables are stored.

---

## 10. Deep Insight: Problem 8 vs Problem 9 Comparison

| Feature | Problem 8 (LeetCode 33) | Problem 9 (LeetCode 81) |
|---|---|---|
| **Element Uniqueness** | Strictly Distinct values | Duplicates allowed |
| **Return Type** | Integer index (`int`, `-1` if not found) | Boolean (`boolean`, `true`/`false`) |
| **Ambiguity Issue** | Never ambiguous; one half is always strictly identifiable | Ambiguous when `nums[left] == nums[mid] == nums[right]` |
| **Worst-Case Time** | `O(log n)` guaranteed | `O(n)` when all elements are duplicates |
| **Resolution** | Standard partition check | Shrink `left++` and `right--` on ambiguity |

---

## 11. Similar Practice Problems
1. **[LeetCode 33 - Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)** *(Unique elements version)*
2. **[LeetCode 154 - Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/)** *(Minimum with duplicates)*
3. **[LeetCode 153 - Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)**
4. **[GFG - Search in a Rotated Array 2](https://practice.geeksforgeeks.org/problems/search-in-rotated-array-2/1)**
