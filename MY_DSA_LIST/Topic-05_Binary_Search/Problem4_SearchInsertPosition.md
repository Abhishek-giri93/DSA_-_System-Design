# Problem 4: Search Insert Position (LeetCode 35)

### 🔗 Platform Practice Links:
- **LeetCode (Problem 35):** [Search Insert Position](https://leetcode.com/problems/search-insert-position/)
- **GeeksforGeeks (GFG):** [Search Insert Position of K in a Sorted Array](https://practice.geeksforgeeks.org/problems/search-insert-position-of-k-in-a-sorted-array/1)
- **Coding Ninjas (Studio):** [Search Insert Position](https://www.codingninjas.com/studio/problems/search-insert-position_981297)

---

## 1. Problem Statement
**What is the problem asking?**
Given a sorted array of distinct integers `nums` and a target value `target`:
- If the `target` is found in the array, return its **index**.
- If not found, return the **index where it would be if it were inserted in order**.

You must write an algorithm with **`O(log n)`** runtime complexity.

**Constraints:**
- $1 \le nums.length \le 10^4$
- $-10^4 \le nums[i], target \le 10^4$
- `nums` contains **distinct** values sorted in ascending order.

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [1, 3, 5, 6]`, `target = 5`
- **Output:** `2`
- **Why?** `5` exists in `nums` at index `2`.

**Example 2:**
- **Input:** `nums = [1, 3, 5, 6]`, `target = 2`
- **Output:** `1`
- **Why?** `2` does not exist in `nums`. If inserted to keep the array sorted (`[1, 2, 3, 5, 6]`), it would be placed at index `1`.

**Example 3:**
- **Input:** `nums = [1, 3, 5, 6]`, `target = 7`
- **Output:** `4`
- **Why?** `7` is greater than all elements. It would be appended at index `4` (the end of the array).

**Example 4:**
- **Input:** `nums = [1, 3, 5, 6]`, `target = 0`
- **Output:** `0`
- **Why?** `0` is smaller than all elements. It would be placed at the very beginning (index `0`).

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search / Lower Bound.
**Why does this pattern apply here?**
The question is asking: *"What is the first index `i` such that `nums[i] >= target`?"*
- If `nums[i] == target`, return `i`.
- If no element equals `target`, the first element that is greater than `target` must shift right to make room for `target`, so `target` takes that exact index `i`.
- If all elements are smaller than `target`, `target` is inserted at index `nums.length`.

This is the exact mathematical definition of **Lower Bound**!

---

## 4. Brute-Force Approach
Iterate through the array. The first element that is $\ge target$ gives the insert position.
```java
public int searchInsertLinear(int[] nums, int target) {
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
- **Bottleneck:** Does not achieve the required `O(log n)` runtime.

---

## 5. Optimization and Intuition
Because `nums` is sorted, we can perform a classic Binary Search:
- If `nums[mid] == target`, we found the target immediately $\rightarrow$ return `mid`.
- If `nums[mid] < target`, target must be in the right half $\rightarrow$ move `left = mid + 1`.
- If `nums[mid] > target`, `mid` is a potential insertion index, but there could be an earlier valid index $\rightarrow$ move `right = mid - 1`.

When the search ends (`left > right`), `left` will **always** point to the correct insertion index!

---

## 6. Algorithm
1. Initialize pointers `left = 0` and `right = nums.length - 1`.
2. Loop while `left <= right`:
   - Compute `mid = left + (right - left) / 2`.
   - If `nums[mid] == target`: return `mid`.
   - If `nums[mid] < target`: `left = mid + 1`.
   - If `nums[mid] > target`: `right = mid - 1`.
3. If not found in the loop, return `left`.

---

## 7. Visual Dry Run
**Input:** `nums = [1, 3, 5, 6]`, `target = 2`

| Step | `left` | `right` | `mid` | `nums[mid]` | Comparison with `target (2)` | Action |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 3 | 1 | 3 | $3 > 2$ | `right = mid - 1 = 0` |
| 2 | 0 | 0 | 0 | 1 | $1 < 2$ | `left = mid + 1 = 1` |
| **End** | 1 | 0 | — | — | `left > right` (Loop ends) | Return `left = 1` |

✅ Returned `1` (which is the correct insertion index).

---

## 8. Java Implementation

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid; // Target found
            } else if (nums[mid] < target) {
                left = mid + 1; // Target is in the right half
            } else {
                right = mid - 1; // Target is in the left half
            }
        }
        
        // When not found, left is the exact index where target should be inserted
        return left;
    }
}
```

---

## 9. Complexity Analysis
- **Time Complexity:** `O(log n)` — The search space is divided by 2 in each iteration.
- **Space Complexity:** `O(1)` — Only two pointers (`left` and `right`) and `mid` are used.

---

## 10. Deep Insight: Why does `left` always equal the insert index?
When the `while (left <= right)` loop terminates:
- All elements with index $< left$ are strictly $< target$.
- All elements with index $> right$ are strictly $> target$.
- Because the loop terminates when `left = right + 1`, `left` is the very first index where the element is $> target$.
- Therefore, inserting `target` at `left` shifts all larger elements to the right, maintaining sorted order!

---

## 11. Similar Practice Problems
1. **[LeetCode 704 - Binary Search](https://leetcode.com/problems/binary-search/)**
2. **[LeetCode 34 - Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)**
3. **[LeetCode 744 - Find Smallest Letter Greater Than Target](https://leetcode.com/problems/find-smallest-letter-greater-than-target/)**
4. **[Coding Ninjas - Implement Lower Bound](https://www.codingninjas.com/studio/problems/lower-bound_8165382)**
