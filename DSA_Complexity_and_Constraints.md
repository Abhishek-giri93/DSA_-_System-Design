# DSA Notes: Time Complexity, Space Complexity, and Constraints

> **Goal:** Understand what complexity means, why it matters, how to
> estimate it from code, and how to use constraints to choose an
> algorithm.
>
> Language: Simple English, with short Hinglish explanations for
> difficult terms. Examples use Java.

## Learning roadmap

1.  What complexity means and why it matters
2.  Time complexity
3.  Space complexity
4.  Big-O, Big-Theta, and Big-Omega
5.  Common complexity classes
6.  How to calculate complexity from Java code
7.  Best, average, and worst cases
8.  Auxiliary space vs total space
9.  What constraints mean
10. How constraints guide algorithm choice
11. Numeric ranges, overflow, and Java data types
12. Time limits, memory limits, and practical caveats
13. Worked examples
14. A checklist and practice questions

------------------------------------------------------------------------

# Part 1 --- Complexity: the big picture

## 1. What is an algorithm?

An **algorithm** is a finite sequence of steps used to solve a problem.

Example: To find `30` in an array, check the elements one by one until
`30` is found or the array ends.

``` java
int[] arr = {10, 20, 30, 40, 50};
int target = 30;

for (int i = 0; i < arr.length; i++) {
    if (arr[i] == target) {
        System.out.println("Found");
        break;
    }
}
```

## 2. What is input size `n`?

`n` usually represents the size of the input. For an array, it is
commonly:

``` java
int n = arr.length;
```

If an array has 5 elements, `n = 5`. If it has 100,000 elements,
`n = 100000`.

A problem can have more than one input size, such as `n` elements in one
array and `m` elements in another.

## 3. What is complexity?

**Complexity describes how the resources an algorithm uses grow as the
input grows.**

The two main resources are:

-   **Time complexity:** how the amount of work grows.
-   **Space complexity:** how the memory requirement grows.

We usually describe growth in terms of input size, not exact seconds or
exact bytes.

### Why not measure only seconds?

Actual running time depends on the computer, programming language,
compiler, background tasks, and implementation. Complexity gives us a
more general way to compare approaches.

For example, an `O(n)` algorithm usually scales better than an `O(n²)`
algorithm when `n` becomes large, even though constant factors can
affect small inputs.

------------------------------------------------------------------------

# Part 2 --- Time complexity

## 4. What is time complexity?

**Time complexity** describes how the number of basic operations grows
as input size `n` grows.

It does **not** directly promise an exact number of seconds.

### Example A: Constant time --- `O(1)`

``` java
int[] arr = {10, 20, 30, 40, 50};
System.out.println(arr[2]);
```

Accessing a known array index takes constant time. It does not require
checking every element.

-   `n = 5`: one indexed access
-   `n = 100000`: still one indexed access

This is `O(1)`.

### Example B: Linear time --- `O(n)`

``` java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```

The loop runs approximately `n` times.

-   `n = 5`: 5 iterations
-   `n = 1000`: 1,000 iterations
-   `n = 100000`: 100,000 iterations

This is `O(n)`.

### Example C: Quadratic time --- `O(n²)`

``` java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        // constant-time work
    }
}
```

For each of the `n` outer-loop iterations, the inner loop runs `n`
times. Total work is approximately `n × n = n²`.

-   `n = 5`: 25 iterations
-   `n = 1000`: 1,000,000 iterations
-   `n = 100000`: 10,000,000,000 iterations

This is usually too much for a typical programming contest time limit
when `n = 100000`.

### Example D: Logarithmic time --- `O(log n)`

Binary Search works on a **sorted** array. It compares with the middle
element and discards half of the remaining search range each step.

For `n = 16`, the search range shrinks roughly like:

`16 → 8 → 4 → 2 → 1`

This takes about `log₂(n)` steps, so Binary Search is `O(log n)`.

**Important:** Binary Search is not valid on an arbitrary unsorted array
unless you first arrange the data appropriately.

### Example E: Linearithmic time --- `O(n log n)`

Efficient comparison-based sorting algorithms such as Merge Sort and
Heap Sort have `O(n log n)` time complexity in their standard forms.

This grows faster than `O(n)` but much slower than `O(n²)` for large
`n`.

### Example F: Exponential time --- `O(2ⁿ)`

A common example is generating every subset of a set with `n` elements.
Each element has two choices: include it or exclude it. There are `2ⁿ`
subsets.

-   `n = 10`: 1,024 subsets
-   `n = 20`: 1,048,576 subsets
-   `n = 30`: 1,073,741,824 subsets

Exponential algorithms become expensive very quickly. They can still be
suitable for small input sizes or when the problem structure allows
special optimizations.

### Example G: Factorial time --- `O(n!)`

Trying every possible ordering of `n` distinct items can involve `n!`
permutations.

Factorial growth becomes enormous quickly. Such approaches are normally
limited to very small `n`, unless pruning or other techniques reduce the
work.

## 5. Approximate operation growth

The following counts are illustrative, not exact runtime predictions.
For `n = 1000`:

  Complexity                          Approximate growth
  --------------- --------------------------------------
  `O(1)`                                               1
  `O(log₂ n)`                                   about 10
  `O(n)`                                           1,000
  `O(n log₂ n)`                             about 10,000
  `O(n²)`                                      1,000,000
  `O(2ⁿ)`           far too large to list for `n = 1000`

These values help explain why algorithm choice matters. The actual
number of machine operations can differ because of constants and
implementation details.

------------------------------------------------------------------------

# Part 3 --- Big-O notation and related terms

## 6. What does Big-O mean?

Big-O notation describes an upper bound on how an algorithm's resource
use grows, commonly used to express worst-case growth in DSA
discussions.

For example, if an algorithm does roughly `3n + 5` operations, we
describe its growth as `O(n)`.

Why ignore `3` and `5`? For very large `n`, the term proportional to `n`
dominates the fixed amount of work. Big-O focuses on the growth rate
rather than every small detail.

### Simplifying rules

1.  **Ignore constant multipliers:** `O(3n)` becomes `O(n)`.
2.  **Keep the fastest-growing term:** `O(n² + n + 10)` becomes `O(n²)`.
3.  **Sequential blocks add:** `O(n) + O(n)` becomes `O(n)`.
4.  **Nested loops often multiply:** an `n` loop inside another `n` loop
    is usually `O(n²)`.
5.  **Different input sizes should stay distinct:** processing one array
    of size `n` and another of size `m` can be `O(n + m)`, not
    automatically `O(n)`.

## 7. Big-O, Big-Theta, and Big-Omega

These notations describe growth bounds:

-   **Big-O, `O(...)`:** an asymptotic upper bound.
-   **Big-Omega, `Ω(...)`:** an asymptotic lower bound.
-   **Big-Theta, `Θ(...)`:** a tight asymptotic bound (upper and lower
    bounds match in growth rate).

In interview conversations, people often use Big-O informally to mean
the algorithm's expected or worst-case complexity. Be clear about which
case you mean when it matters.

------------------------------------------------------------------------

# Part 4 --- How to calculate time complexity from code

## 8. One loop

``` java
for (int i = 0; i < n; i++) {
    work();
}
```

If `work()` takes constant time, the loop costs `O(n)`.

## 9. Two sequential loops

``` java
for (int i = 0; i < n; i++) {
    work();
}

for (int j = 0; j < n; j++) {
    work();
}
```

The work is `n + n = 2n`, so the complexity is `O(n)`, not `O(n²)`.

## 10. Nested loops

``` java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        work();
    }
}
```

The work is `n × n`, so `O(n²)`.

## 11. A loop that doubles its variable

``` java
for (int i = 1; i < n; i *= 2) {
    work();
}
```

The values are approximately `1, 2, 4, 8, 16, ...`. After `k`
iterations, `i` is about `2ᵏ`. The loop stops when `2ᵏ` reaches `n`, so
`k` is about `log₂(n)`.

Complexity: `O(log n)`.

## 12. A loop that halves its variable

``` java
for (int i = n; i > 0; i /= 2) {
    work();
}
```

The values are approximately `n, n/2, n/4, ...`, so complexity is
`O(log n)`.

## 13. A nested loop that is not always `O(n²)`

``` java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < i; j++) {
        work();
    }
}
```

The inner-loop counts are `0 + 1 + 2 + ... + (n - 1)`. Their sum is
`n(n - 1)/2`, which grows quadratically.

Complexity: `O(n²)`.

## 14. A loop with two different input sizes

``` java
for (int i = 0; i < n; i++) {
    work();
}

for (int j = 0; j < m; j++) {
    work();
}
```

Complexity: `O(n + m)`.

If the loops are nested, one running `n` times and the other `m` times,
the complexity can be `O(nm)`.

## 15. Be careful with method calls

When a loop calls another method, include the cost of that method. For
example, calling a linear search inside an `n`-iteration loop may
produce `O(n²)` total work.

------------------------------------------------------------------------

# Part 5 --- Best, average, and worst cases

Consider searching for a target in an unsorted array.

-   **Best case:** target is the first element, so one comparison:
    `O(1)`.
-   **Worst case:** target is last or absent, so up to `n` comparisons:
    `O(n)`.
-   **Average case:** expected number of checks under a stated input
    distribution; for a uniformly placed target, it is proportional to
    `n`, so `O(n)`.

These cases can differ. When a problem asks for complexity, state the
case if it is relevant.

------------------------------------------------------------------------

# Part 6 --- Space complexity

## 16. What is space complexity?

**Space complexity** describes how memory use grows as input size grows.

This can include: - Extra arrays, lists, maps, sets, and other data
structures - Temporary variables - Recursion call-stack memory -
Sometimes the input storage itself, depending on the convention being
used

In interview problems, people often specifically discuss **auxiliary
space**, meaning extra memory used by the algorithm beyond the input.

## 17. `O(1)` auxiliary space

``` java
int sum = 0;

for (int i = 0; i < n; i++) {
    sum += arr[i];
}
```

The algorithm uses a fixed number of extra variables (`sum`, `i`),
regardless of `n`.

Auxiliary space: `O(1)`.

## 18. `O(n)` auxiliary space

``` java
int[] copy = new int[n];

for (int i = 0; i < n; i++) {
    copy[i] = arr[i];
}
```

The new array has `n` elements. Extra memory grows linearly.

Auxiliary space: `O(n)`.

## 19. `O(n²)` space

``` java
int[][] matrix = new int[n][n];
```

The matrix stores `n × n` values.

Space: `O(n²)`.

## 20. Recursion and space

Recursive calls use the call stack.

``` java
int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

There are up to `n` active calls, so stack space is `O(n)`.

By contrast, a recursive binary search usually has `O(log n)` stack
space because each call halves the remaining range.

## 21. Time and space are different

An algorithm can be: - Fast but use more memory - Slow but use little
extra memory - Both efficient - Both inefficient

For example, using a `HashSet` may make membership checks faster on
average but requires extra memory. An in-place algorithm may save memory
by modifying the input array directly.

**In-place** means the algorithm modifies the input or uses only a small
amount of extra memory. The exact auxiliary-space complexity depends on
the implementation.

------------------------------------------------------------------------

# Part 7 --- Constraints: what they mean

## 22. What are constraints?

Constraints are limits given in the problem statement. They tell you
what input sizes and values are allowed.

Example:

``` text
1 <= n <= 100000
-1000000000 <= nums[i] <= 1000000000
```

Interpretation:

-   `n` is at least 1.
-   `n` can be as large as 100,000.
-   Each `nums[i]` value lies between −1,000,000,000 and +1,000,000,000,
    inclusive.

Constraints are not decoration. They help determine: 1. Which algorithm
can finish in time 2. How much memory may be needed 3. Which Java data
types are safe 4. Whether brute force is feasible 5. Whether edge cases
such as empty arrays or negative numbers are possible

## 23. Common constraint formats

### Array length

``` text
1 <= n <= 100000
```

The array contains from 1 to 100,000 elements.

### Value range

``` text
0 <= nums[i] <= 1000000000
```

Every array value is non-negative and at most one billion.

### Negative and positive values

``` text
-10^9 <= nums[i] <= 10^9
```

Negative values are possible. Do not assume all values are positive.

### Multiple test cases

``` text
1 <= T <= 100
1 <= n <= 100000
```

`T` is the number of test cases. Check whether `n` is the size for each
test case and whether the problem also states a total bound such as
`sum of n across all test cases <= 100000`.

If each test case can have 100,000 elements and there are 100 test
cases, total work can be much larger than for a single case. A total-sum
constraint often matters more than the per-case maximum.

### Two arrays

``` text
1 <= n, m <= 100000
```

There are two input sizes. A solution may be `O(n + m)` or `O(nm)`. Keep
both symbols until you know how the sizes relate.

### String length

``` text
1 <= s.length() <= 200000
```

The string can contain up to 200,000 characters. Check any separate
constraints on the character set, such as lowercase English letters
only.

### Graph constraints

``` text
1 <= V <= 100000
0 <= E <= 200000
```

`V` is the number of vertices and `E` is the number of edges. Graph
algorithms often depend on both: for example, adjacency-list BFS/DFS is
typically `O(V + E)`.

------------------------------------------------------------------------

# Part 8 --- Use constraints to choose an algorithm

## 24. A practical, approximate guide

These are common rules of thumb, not guarantees. Actual feasibility
depends on the language, time limit, implementation, memory, and the
operations performed.

  -----------------------------------------------------------------------
  Maximum input size                  What may be feasible
  ----------------------------------- -----------------------------------
  `n <= 10`                           Exhaustive search, permutations, or
                                      `O(n!)` may be possible

  `n <= 20`                           Some `O(2ⁿ)` subset/bitmask
                                      approaches may be possible

  `n <= 100`                          `O(n³)` may be feasible

  `n <= 1,000`                        `O(n²)` is often plausible

  `n <= 100,000`                      Usually look for `O(n log n)` or
                                      `O(n)`

  `n <= 1,000,000`                    `O(n)` is often a strong target

  Very large `n`                      Look for near-linear, logarithmic,
                                      mathematical, or specialized
                                      approaches
  -----------------------------------------------------------------------

Do not treat this table as a law. For example, an `O(n²)` solution can
pass for small `n`, while an `O(n)` solution can still be too slow if
each operation is extremely expensive.

## 25. Worked example: why nested loops can fail

Suppose:

``` text
1 <= n <= 100000
```

Approach A does about `n` operations: - At `n = 100000`, about 100,000
iterations.

Approach B does about `n²` operations: - At `n = 100000`, about
10,000,000,000 iterations.

That is ten billion loop iterations, which is generally impractical
under typical coding-platform time limits.

So when `n` can reach 100,000, be suspicious of a straightforward double
loop. Look for sorting + two pointers, hashing, binary search, prefix
sums, a sliding window, or another suitable technique depending on the
problem.

## 26. Sorting changes the trade-off

Suppose you need to find whether two array elements sum to a target.

-   A brute-force pair check can take `O(n²)` time and `O(1)` extra
    space.
-   Sorting and then using two pointers can take `O(n log n)` time for
    sorting plus `O(n)` for the scan. Extra space depends on the sorting
    implementation and whether copying is required.
-   Hashing can often solve it in expected `O(n)` time with `O(n)` extra
    space.

The constraints help you decide which trade-off is appropriate.

## 27. Read constraints before choosing brute force

Brute force is a useful first idea because it is often simple and easy
to verify. But always compare its complexity with the maximum input
size.

A good workflow: 1. Understand the required output. 2. Write a simple
brute-force approach. 3. Estimate its time and space complexity. 4.
Compare those estimates with the constraints. 5. Optimize if needed. 6.
Check correctness and edge cases.

------------------------------------------------------------------------

# Part 9 --- Numeric constraints and Java types

## 28. `int` versus `long`

In Java: - `int` is a 32-bit signed integer, with range approximately
−2.147 billion to +2.147 billion. - `long` is a 64-bit signed integer,
with a much larger range.

If each value can be up to `10^9`, adding two values up to `10^9` gives
up to `2 × 10^9`, which still fits in `int`. But sums of three such
values, large prefix sums, products, or accumulated totals may exceed
the `int` range.

Example:

``` java
int a = 1_000_000_000;
int b = 1_000_000_000;
int c = 1_000_000_000;

long sum = (long) a + b + c;
```

The cast before addition ensures the arithmetic is performed as `long`.
Assigning an already-overflowed `int` expression to a `long` does not
fix it.

Bad pattern:

``` java
long sum = a + b + c; // May overflow as int before assignment
```

Safer pattern:

``` java
long sum = (long) a + b + c;
```

## 29. Multiplication deserves special attention

Even when each input fits in `int`, the product may not.

``` java
long product = (long) a * b;
```

Casting one operand before multiplication makes the product calculation
use `long`.

## 30. Other value constraints matter

-   Negative values: can invalidate assumptions used by some
    sliding-window or greedy solutions.
-   Sorted input: may allow binary search or two pointers.
-   Distinct values: can simplify duplicates handling.
-   Small bounded values: may make counting arrays practical.
-   Very large values: may make a direct-index array impossible, making
    hashing or sorting more suitable.

Always read the entire statement, not only `n`.

------------------------------------------------------------------------

# Part 10 --- Memory constraints

## 31. What does a memory limit tell you?

A memory limit restricts how much memory your program can use. If an
array contains millions of values, copying it several times can be
expensive.

Consider: - One array of size `n`: `O(n)` storage - A second copy of
that array: another `O(n)` storage - An `n × n` matrix: `O(n²)` storage,
which becomes huge quickly

For a matrix with `n = 100000`, `n²` entries would be ten billion
entries, far beyond ordinary memory limits.

When memory is tight, consider: - In-place processing - Streaming input
when appropriate - A more compact representation - Avoiding unnecessary
copies - Using an algorithm with lower auxiliary space

Do not optimize memory blindly; first confirm correctness and identify
what storage is necessary.

------------------------------------------------------------------------

# Part 11 --- Common mistakes

1.  **Thinking Big-O is exact seconds.** It describes growth, not a
    stopwatch reading.
2.  **Calling two sequential loops `O(n²)`.** They often add to `O(n)`.
3.  **Assuming every nested loop is `O(n²)`.** The inner loop may have a
    different bound; analyze the actual iteration counts.
4.  **Ignoring method-call costs.** A loop calling a costly method must
    include that method's complexity.
5.  **Ignoring recursion stack space.** Recursive algorithms use stack
    memory.
6.  **Looking only at `n`.** Value ranges, number of test cases, `m`,
    `V`, and `E` can matter too.
7.  **Ignoring total test-case size.** `T` cases of size `n` can
    multiply the total work.
8.  **Using `int` for every calculation.** Large sums and products can
    overflow.
9.  **Assuming a common rule-of-thumb is guaranteed.** Time limits and
    implementation details matter.
10. **Optimizing before understanding the problem.** First establish
    correctness, then analyze and optimize.

------------------------------------------------------------------------

# Part 12 --- A repeatable checklist for every DSA problem

Before coding, ask:

1.  What are the input sizes? (`n`, `m`, `V`, `E`, `T`)
2.  What are the maximum sizes?
3.  What are the allowed value ranges?
4.  Can values be negative, duplicated, sorted, or empty?
5.  What is the simplest correct approach?
6.  What is its time complexity?
7.  What is its auxiliary-space complexity?
8.  Does that complexity look feasible for the maximum input?
9.  Can sums or products overflow `int`?
10. Are there edge cases: one element, all duplicates, target absent,
    very large values?
11. Can I improve time by using more memory, or reduce memory without
    making time unacceptable?
12. Have I tested the approach against the largest and smallest allowed
    inputs?

------------------------------------------------------------------------

# Part 13 --- Worked examples

## Example 1: Find the maximum in an array

``` java
int max = arr[0];

for (int i = 1; i < arr.length; i++) {
    if (arr[i] > max) {
        max = arr[i];
    }
}
```

-   Time: `O(n)` --- each remaining element is checked once.
-   Auxiliary space: `O(1)` --- only a few variables.
-   Constraint impact: this is a good fit for a large array.

Assumption: the array is non-empty. If empty input is allowed, handle it
separately.

## Example 2: Check every pair

``` java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        // Check pair (i, j)
    }
}
```

The number of pairs is `n(n - 1)/2`, so: - Time: `O(n²)` - Auxiliary
space: `O(1)` if the body uses only fixed extra storage.

This is reasonable for small inputs but may fail for very large `n`.

## Example 3: Build a frequency array

If every value is known to be between `0` and `100`, a frequency array
of size 101 may be appropriate.

-   Time to count `n` values: `O(n)`
-   Extra space: `O(1)` relative to `n`, because the array size is fixed
    at 101.

If the value range were up to one billion, a frequency array with one
billion entries would generally be impractical. A hash map or another
strategy may be more suitable.

------------------------------------------------------------------------

# Part 14 --- Practice questions

Try to estimate the time and auxiliary-space complexity. Answers are
provided below each question so you can check your reasoning.

## Question 1

``` java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```

**Answer:** Time `O(n)`, auxiliary space `O(1)`.

## Question 2

``` java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        System.out.println(i + j);
    }
}
```

**Answer:** Time `O(n²)`, auxiliary space `O(1)`.

## Question 3

``` java
for (int i = 1; i < n; i *= 2) {
    System.out.println(i);
}
```

**Answer:** Time `O(log n)`, auxiliary space `O(1)`.

## Question 4

``` java
int[] copy = new int[n];
```

**Answer:** Allocation takes `O(n)` space. Initializing an array of
primitive values also takes `O(n)` time in Java's normal array
initialization behavior.

## Question 5

``` text
1 <= n <= 200000
```

Would a straightforward `O(n²)` solution usually be a safe choice?

**Answer:** Usually no. At the maximum size, `n²` is 40,000,000,000.
Look for a more efficient approach unless the algorithm has additional
structure that reduces the actual work.

## Question 6

``` text
-10^9 <= nums[i] <= 10^9
```

Can you always store the sum of every element in an `int`?

**Answer:** No. The total sum can exceed the `int` range. Use `long`
when the maximum possible sum may overflow `int`, and cast before
arithmetic when needed.

------------------------------------------------------------------------

# Final summary

-   **Time complexity** measures how work grows with input size.
-   **Space complexity** measures how memory use grows with input size.
-   `O(1)` is constant, `O(log n)` grows slowly, `O(n)` is linear,
    `O(n log n)` is common in efficient sorting, and `O(n²)` grows
    quickly.
-   Nested loops often multiply; sequential loops often add.
-   Constraints tell you the maximum input size and value ranges.
-   Use constraints to assess brute force, choose data structures and
    algorithms, and avoid overflow.
-   Complexity rules of thumb are guides, not guarantees.
-   For each DSA problem, state time complexity, auxiliary-space
    complexity, and the assumptions behind them.

**Recommended next topic:** Learn to calculate complexity from code line
by line, including nested loops, loops that double/halve, and recursion.
