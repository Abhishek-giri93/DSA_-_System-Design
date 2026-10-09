# Problem 1: Binary Search (LeetCode 704)

## 1. Problem Statement
**What is the problem asking?**
You are given an array of integers (whole numbers) that is strictly sorted in ascending order (smallest to largest). You are also given an integer `target`.
Your task is to find if the `target` exists in the array.
- If it exists, return its **index** (the position of the number in the array, starting from 0).
- If it does not exist, return `-1`.

**Important Terms:**
- **Sorted Array:** Elements are arranged in a specific order (e.g., `[-1, 0, 3, 5, 9, 12]`).
- **Index:** The 0-based position of an element in the array.

**Constraints:**
- You must write an algorithm with `O(log n)` runtime complexity.
- The array can have up to 10,000 elements.

## 2. Examples and Understanding

**Example 1:**
- **Input:** `nums = [-1, 0, 3, 5, 9, 12]`, `target = 9`
- **Output:** `4`
- **Why?** If we look at the array, the number `9` is sitting at index `4` (0-indexed). So we return `4`.

**Example 2:**
- **Input:** `nums = [-1, 0, 3, 5, 9, 12]`, `target = 2`
- **Output:** `-1`
- **Why?** The number `2` is nowhere to be found in the array. So we return `-1`.

**Visualizing it:**
```text
Index:    0    1    2    3    4    5
Array:  [-1,   0,   3,   5,   9,  12]
```
If we search for `9`, we find it at index `4`.

## 3. Pattern Recognition
**Main DSA Pattern:** Binary Search
**Why does this pattern apply here?**
There are two massive clues in the problem statement:
1. The array is **sorted**. 
2. The problem explicitly asks for an **`O(log n)`** solution.

## 4. Brute-Force Approach
**How it works:**
```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] == target) return i;
}
return -1;
```
**Complexity Analysis:**
- **Time Complexity:** `O(n)` 
- **Space Complexity:** `O(1)`
- **Bottleneck:** We are not taking advantage of the fact that the array is **sorted**.

## 5. Optimization and Intuition
**How to improve the Brute-Force?**
Think about finding the word "Mango" in a physical dictionary. You don't read from page 1. You open the dictionary to the middle. 
- If you land on the letter "M", you found it.
- If you land on "P", you know "Mango" must be in the **left half**. 
- If you land on "H", you know "Mango" must be in the **right half**.

We can apply this exact logic to our sorted array to cut the search space in half at every step.

## 6. Algorithm
1. Initialize two pointers: `left` at `0` and `right` at `nums.length - 1`.
2. Loop while `left <= right`.
3. Inside the loop, find the middle index: `mid = left + (right - left) / 2`.
4. Compare `nums[mid]` with the `target`:
   - If `nums[mid] == target`, you found it! Return `mid`.
   - If `nums[mid] < target`, target is to the right. Move `left` to `mid + 1`.
   - If `nums[mid] > target`, target is to the left. Move `right` to `mid - 1`.
5. If the loop finishes, return `-1`.

## 7. Visual Dry Run
**Input:** `nums = [-1, 0, 3, 5, 9, 12]`, `target = 9`

**Step 1:**
- `left = 0`, `right = 5` -> `mid = 2` (`nums[2] = 3`)
- `3 < 9`, so move `left` to `mid + 1` = 3.

**Step 2:**
- `left = 3`, `right = 5` -> `mid = 4` (`nums[4] = 9`)
- `9 == 9`! Return `4`.

## 8. Java Implementation
```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            // Avoids integer overflow
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            else if (nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
}
```

## 9. Complexity Analysis
- **Time Complexity:** `O(log n)` (Search space halves each time)
- **Space Complexity:** `O(1)` (Only 3 variables used)

## 10. Pattern Summary and Recognition
**Pattern Learned:** Binary Search (Halving the search space).
**Important Trick:** *Always* use `int mid = left + (right - left) / 2;` instead of `(left + right) / 2` to prevent Integer Overflow.

## 11. Similar Practice Problems
1. **Search Insert Position (LeetCode 35)** 
2. **First Bad Version (LeetCode 278)** 
3. **Find First and Last Position (LeetCode 34)** 
