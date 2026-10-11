# Problem 5: Floor and Ceil in Sorted Array

### 🔗 Platform Practice Links:
- **GeeksforGeeks (GFG):** [Ceil The Floor](https://practice.geeksforgeeks.org/problems/ceil-the-floor2802/1)
- **Coding Ninjas (Studio):** [Ceiling in a Sorted Array](https://www.codingninjas.com/studio/problems/ceiling-in-a-sorted-array_1825401)
- **LeetCode (Problem 35):** [Search Insert Position](https://leetcode.com/problems/search-insert-position/) *(Equivalent to finding Ceil index)*

---

## 1. Problem Statement
**What is the problem asking?**
You are given a sorted array `nums` of `n` integers (in ascending order) and an integer `x`.
You need to find the **floor** and **ceil** of `x` in the array.

- **Floor of `x`:** The largest element in the array that is **smaller than or equal to `x`** (`nums[i] <= x`).
  - If no such element exists, the floor is `-1`.
- **Ceil of `x`:** The smallest element in the array that is **greater than or equal to `x`** (`nums[i] >= x`).
  - If no such element exists, the ceil is `-1`.

Return a pair or array containing `[floor, ceil]`.

**Constraints:**
- $1 \le n \le 10^5$
- $1 \le nums[i], x \le 10^9$
- The array `nums` is sorted in ascending order.
- Time Complexity requirement: **`O(log n)`**

---

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [3, 4, 4, 7, 8, 10]`, `x = 5`
- **Output:** `[4, 7]`
- **Why?**
  - Elements $\le 5$ are `[3, 4, 4]`. The largest among them is `4` $\rightarrow$ **Floor = 4**.
  - Elements $\ge 5$ are `[7, 8, 10]`. The smallest among them is `7` $\rightarrow$ **Ceil = 7**.

**Example 2:**
- **Input:** `nums = [3, 4, 4, 7, 8, 10]`, `x = 2`
- **Output:** `[-1, 3]`
- **Why?**
  - There is no element $\le 2$ $\rightarrow$ **Floor = -1**.
  - Elements $\ge 2$ are `[3, 4, 4, 7, 8, 10]`. The smallest is `3` $\rightarrow$ **Ceil = 3**.

**Example 3:**
- **Input:** `nums = [3, 4, 4, 7, 8, 10]`, `x = 8`
- **Output:** `[8, 8]`
- **Why?** `8` exists in the array. Since $8 \le 8$ and $8 \ge 8$, both Floor and Ceil are `8`.

---

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search (Lower Bound & Upper-Bounded Floor search).
**Why does this pattern apply here?**
1. The array is already **sorted**.
2. **Ceil:** This is the exact value at the **Lower Bound** index! Lower bound gives the smallest index where `nums[i] >= x`. If lower bound index $< n$, then `nums[lower_bound]` is the Ceil.
3. **Floor:** This is symmetric. We want the largest element $\le x$. Whenever `nums[mid] <= x`, `nums[mid]` is a candidate floor, and an even larger candidate could exist further to the right.

---

## 4. Brute-Force Approach
Linearly iterate through the array to track `floor = -1` and `ceil = -1`.
```java
public int[] getFloorAndCeilLinear(int[] nums, int x) {
    int floor = -1, ceil = -1;
    for (int num : nums) {
        if (num <= x) {
            floor = Math.max(floor, num);
        }
        if (num >= x) {
            if (ceil == -1 || num < ceil) {
                ceil = num;
            }
        }
    }
    return new int[]{floor, ceil};
}
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`
- **Bottleneck:** Scans every element sequentially, ignoring the sorted property.

---

## 5. Optimization and Intuition

Instead of scanning linearly, we can use two independent Binary Searches (or one unified search):

### 1. Finding Floor (`nums[i] <= x`, maximize):
- If `nums[mid] <= x`:
  - `nums[mid]` is a valid candidate for floor!
  - We want the *largest* such element, so search further right: `floor = nums[mid]`, `left = mid + 1`.
- If `nums[mid] > x`:
  - `nums[mid]` is too large, search left: `right = mid - 1`.

### 2. Finding Ceil (`nums[i] >= x`, minimize):
- If `nums[mid] >= x`:
  - `nums[mid]` is a valid candidate for ceil!
  - We want the *smallest* such element, so search further left: `ceil = nums[mid]`, `right = mid - 1`.
- If `nums[mid] < x`:
  - `nums[mid]` is too small, search right: `left = mid + 1`.

---

## 6. Algorithm
1. Initialize `floor = -1`, `ceil = -1`.
2. **Binary Search for Floor:**
   - `left = 0`, `right = n - 1`
   - While `left <= right`:
     - `mid = left + (right - left) / 2`
     - If `nums[mid] <= x`: `floor = nums[mid]`, `left = mid + 1`.
     - Else: `right = mid - 1`.
3. **Binary Search for Ceil:**
   - `left = 0`, `right = n - 1`
   - While `left <= right`:
     - `mid = left + (right - left) / 2`
     - If `nums[mid] >= x`: `ceil = nums[mid]`, `right = mid - 1`.
     - Else: `left = mid + 1`.
4. Return `new int[]{floor, ceil}`.

---

## 7. Visual Dry Run
**Input:** `nums = [3, 4, 4, 7, 8, 10]`, `x = 5`

### Finding Floor:
| Step | `left` | `right` | `mid` | `nums[mid]` | Condition (`<= 5`?) | Action | `floor` |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 5 | 2 | 4 | $4 \le 5$ ✅ | `floor = 4`, `left = 3` | 4 |
| 2 | 3 | 5 | 4 | 8 | $8 \le 5$ ❌ | `right = mid - 1 = 3` | 4 |
| 3 | 3 | 3 | 3 | 7 | $7 \le 5$ ❌ | `right = mid - 1 = 2` | 4 |
| End | 3 | 2 | — | — | `left > right` | Return `floor = 4` | **4** |

### Finding Ceil:
| Step | `left` | `right` | `mid` | `nums[mid]` | Condition (`>= 5`?) | Action | `ceil` |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 1 | 0 | 5 | 2 | 4 | $4 \ge 5$ ❌ | `left = mid + 1 = 3` | -1 |
| 2 | 3 | 5 | 4 | 8 | $8 \ge 5$ ✅ | `ceil = 8`, `right = 3` | 8 |
| 3 | 3 | 3 | 3 | 7 | $7 \ge 5$ ✅ | `ceil = 7`, `right = 2` | 7 |
| End | 3 | 2 | — | — | `left > right` | Return `ceil = 7` | **7** |

---

## 8. Java Implementation

```java
public class Solution {
    public static int[] getFloorAndCeil(int[] nums, int x) {
        int floor = findFloor(nums, x);
        int ceil = findCeil(nums, x);
        return new int[]{floor, ceil};
    }

    private static int findFloor(int[] nums, int x) {
        int left = 0, right = nums.length - 1;
        int floor = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] <= x) {
                floor = nums[mid];    // Potential floor found
                left = mid + 1;       // Look for a larger element <= x on the right
            } else {
                right = mid - 1;      // Too large, search left
            }
        }
        return floor;
    }

    private static int findCeil(int[] nums, int x) {
        int left = 0, right = nums.length - 1;
        int ceil = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] >= x) {
                ceil = nums[mid];     // Potential ceil found
                right = mid - 1;      // Look for a smaller element >= x on the left
            } else {
                left = mid + 1;       // Too small, search right
            }
        }
        return ceil;
    }
}
```

---

## 9. Complexity Analysis
- **Time Complexity:** `O(log n)` — Running two binary searches takes $O(\log n) + O(\log n) = O(\log n)$.
- **Space Complexity:** `O(1)` — Only pointer variables are stored.

---

## 10. Deep Insight: Comparison with Lower Bound / Upper Bound

| Concept | Mathematical Condition | Relation to Lower/Upper Bound |
|---|---|---|
| **Ceil** | Smallest element with `nums[i] >= x` | Exactly `nums[lower_bound(x)]` (if index $< n$) |
| **Floor** | Largest element with `nums[i] <= x` | Element just before upper bound: `nums[upper_bound(x) - 1]` (if index $> 0$) |

---

## 11. Similar Practice Problems
1. **[LeetCode 35 - Search Insert Position](https://leetcode.com/problems/search-insert-position/)**
2. **[Coding Ninjas - Implement Lower Bound](https://www.codingninjas.com/studio/problems/lower-bound_8165382)**
3. **[Coding Ninjas - Implement Upper Bound](https://www.codingninjas.com/studio/problems/implement-upper-bound_8165383)**
4. **[LeetCode 744 - Find Smallest Letter Greater Than Target](https://leetcode.com/problems/find-smallest-letter-greater-than-target/)**
