# Problem 8: Search in Rotated Sorted Array I (LeetCode 33)

### 🔗 Platform Practice Links:
- **LeetCode (Problem 33):** [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
- **GeeksforGeeks (GFG):** [Search in a Rotated Array](https://practice.geeksforgeeks.org/problems/search-in-a-rotated-array4618/1)
- **Coding Ninjas (Studio):** [Search In Rotated Sorted Array](https://www.codingninjas.com/studio/problems/search-in-rotated-sorted-array_1082554)

---

## 1. Problem Statement
**What is the problem asking?**
There is an integer array `nums` sorted in ascending order with **all distinct values**.

Prior to being passed to your function, `nums` is possibly rotated at an unknown pivot index `k` ($1 \le k < nums.length$) such that the resulting array is:
`[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`.

For example, `[0, 1, 2, 4, 5, 6, 7]` might be rotated at pivot index 3 and become `[4, 5, 6, 7, 0, 1, 2]`.

Given the array `nums` after the possible rotation and an integer `target`:
- If `target` is in `nums`, return its **index**.
- Otherwise, return `-1`.

You must achieve a runtime complexity of **`O(log n)`**.

**Constraints:**
- $1 \le nums.length \le 5000$
- $-10^4 \le nums[i], target \le 10^4$
- All values of `nums` are **unique / distinct**.
- `nums` is guaranteed to be rotated at some pivot.

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [4, 5, 6, 7, 0, 1, 2]`, `target = 0`
- **Output:** `4`
- **Why?** Number `0` is at index `4`.

**Example 2:**
- **Input:** `nums = [4, 5, 6, 7, 0, 1, 2]`, `target = 3`
- **Output:** `-1`
- **Why?** `3` is not present in `nums`.

**Example 3:**
- **Input:** `nums = [1]`, `target = 0`
- **Output:** `-1`
- **Why?** Array only has `1`, which does not match `0`.

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search in Rotated Sorted Array (Identifying Sorted Half).
**Why does this pattern apply here?**
Even though the overall array is rotated, whenever you pick the middle element `mid`, **at least one of the two halves (left or right) is guaranteed to be completely sorted**:
- If `nums[left] <= nums[mid]`: The **left half** (`[left ... mid]`) is sorted.
- Else: The **right half** (`[mid ... right]`) is sorted.

Once you know which half is sorted, you can easily check if the `target` falls inside that sorted range using simple boundary comparisons!

---

## 4. Brute-Force Approach
Scan linearly across the array:
```java
public int searchLinear(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) return i;
    }
    return -1;
}
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`
- **Bottleneck:** Fails the `O(log n)` requirement.

---

## 5. Optimization and Intuition

At any step with pointers `left` and `right`:
1. Calculate `mid = left + (right - left) / 2`.
2. If `nums[mid] == target`, return `mid` immediately!
3. **Step A: Check if the Left Half is sorted (`nums[left] <= nums[mid]`):**
   - If the left half is sorted, check if `target` lies within `nums[left]` and `nums[mid]`:
     - If `nums[left] <= target && target < nums[mid]`: target is definitely in the left half $\rightarrow$ `right = mid - 1`.
     - Else: target must be in the right half $\rightarrow$ `left = mid + 1`.
4. **Step B: Otherwise, the Right Half must be sorted (`nums[mid] < nums[right]`):**
   - Check if `target` lies within `nums[mid]` and `nums[right]`:
     - If `nums[mid] < target && target <= nums[right]`: target is definitely in the right half $\rightarrow$ `left = mid + 1`.
     - Else: target must be in the left half $\rightarrow$ `right = mid - 1`.

---

## 6. Algorithm
1. Initialize `left = 0`, `right = nums.length - 1`.
2. Loop while `left <= right`:
   - `mid = left + (right - left) / 2`
   - If `nums[mid] == target`: return `mid`.
   - If `nums[left] <= nums[mid]` (Left half is sorted):
     - If `nums[left] <= target && target < nums[mid]`:
       - `right = mid - 1`
     - Else:
       - `left = mid + 1`
   - Else (Right half is sorted):
     - If `nums[mid] < target && target <= nums[right]`:
       - `left = mid + 1`
     - Else:
       - `right = mid - 1`
3. If loop ends without returning, target is not present $\rightarrow$ return `-1`.

---

## 7. Visual Dry Run
**Input:** `nums = [4, 5, 6, 7, 0, 1, 2]`, `target = 0`

| Step | `left` | `right` | `mid` | `nums[mid]` | Sorted Half | In Sorted Range? | Action |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 6 | 3 | 7 | Left `[4, 5, 6, 7]` is sorted ($4 \le 7$) | Is $4 \le 0 < 7$? ❌ No | Move to right: `left = mid + 1 = 4` |
| 2 | 4 | 6 | 5 | 1 | Left `[0, 1]` is sorted ($0 \le 1$) | Is $0 \le 0 < 1$? ✅ Yes | Move to left: `right = mid - 1 = 4` |
| 3 | 4 | 4 | 4 | 0 | `nums[4] == 0 == target` ✅ | Match found! | **Return `mid = 4`** |

✅ Correct Output: `4`.

---

## 8. Java Implementation

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid; // Target found
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

        return -1; // Target not found
    }
}
```

---

## 9. Complexity Analysis
- **Time Complexity:** `O(log n)` — In each iteration, we eliminate half of the search space based on sorted partition checks.
- **Space Complexity:** `O(1)` — Only pointer variables (`left`, `right`, `mid`) are maintained.

---

## 10. Deep Insight: Why is one half always sorted?
A rotated sorted array is formed by cutting a sorted array into two sorted pieces and swapping them:
- `[0, 1, 2, 4, 5, 6, 7]` $\rightarrow$ `[4, 5, 6, 7]` and `[0, 1, 2]`.
When you pick any `mid`:
- If `mid` falls in the left piece: `nums[left] <= nums[mid]`, meaning everything from `left` to `mid` is monotonically increasing.
- If `mid` falls in the right piece: `nums[mid] <= nums[right]`, meaning everything from `mid` to `right` is monotonically increasing.
Therefore, at least one half is **always cleanly sorted**!

---

## 11. Similar Practice Problems
1. **[LeetCode 81 - Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)** *(With duplicate values)*
2. **[LeetCode 153 - Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)**
3. **[LeetCode 154 - Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/)**
4. **[GFG - Number of times array is rotated](https://practice.geeksforgeeks.org/problems/rotation4723/1)**
