# Minimum Window Substring
Track character counts, not last-seen indices or substring.contains(). The problem requires covering counts of characters from t within a window of s and shrinking that window to the minimum size while keeping it valid.
## Pattern: counts + shrink-when-valid
Two common variants; pick one:
### Variant A: need[] + missing (simplest)
- Build a frequency table need[c] for all chars in t.
- Let missing = t.length() — the total characters we still need to cover.
- Expand right over s:
    - For c = s[right], do need[c]--.
    - If need[c] >= 0, we just satisfied one needed char → missing--.

- Once missing == 0, the window [left..right] contains all of t.
- Shrink from the left while the window stays valid:
    - While need[s[left]] < 0: increment need[s[left]] and move left++.
    - Update best answer if this window is smaller.

- To make the window invalid and continue searching:
    - Move left one more step: increment need[s[left]]; if it becomes > 0, missing++. Then left++.

- Continue expanding right.

This avoids extra bookkeeping like “formed” vs “required”.
### Variant B: needMap + haveMap + formed == required
- Build needMap with counts from t; required = needMap.size().
- Move right; update haveMap; if a char’s count meets needMap, increment formed.
- While formed == required, shrink left and update best.

Also correct, just slightly more bookkeeping.
## Why common attempts fail
- Using last-seen index (e.g., map.put(ch, right)) fails because counts matter (t="AABC" needs two 'A's).
- substring.contains(t) checks contiguous equality, not multiset coverage.
- “Minimum window” requires shrinking while valid; just tracking max or last positions won’t minimize.

## Pseudocode (Variant A)
```groovy

function minWindow(s, t):
    if t.length == 0 or s.length == 0: return ""
    need[128] = {0}  // ASCII is enough for letters/digits/symbols/space
    for each ch in t:
        need[ch]++

    missing = t.length
    left = 0
    bestStart = 0
    bestLen = +INF

    for right in [0..s.length-1]:
        c = s[right]
        need[c]--
        if need[c] >= 0:
            missing--

        while missing == 0:
            // update best
            if (right - left + 1) < bestLen:
                bestLen = right - left + 1
                bestStart = left

            // pop left char
            cLeft = s[left]
            need[cLeft]++
            if need[cLeft] > 0:
                // we made window invalid
                missing++
            left++

    return bestLen == +INF ? "" : s.substring(bestStart, bestStart + bestLen)


```
## Complexity
- Time: O(|s| + |t|) — each index advances at most once.
- Space: O(1) using a fixed-size array (e.g., 128 for ASCII).

## Micro–walkthrough: s="ADOBECODEBANC", t="ABC"
- Build need: A:1, B:1, C:1.
- Move right across s, decrement counts, decrement missing only when need[c] stays ≥ 0.
- When the window first covers A, B, C (at “…ADOBEC”), missing == 0.
- Shrink from left while valid → best becomes "ADOBEC".
- Continue; eventually the smallest valid window becomes "BANC".

## Common bugs to avoid
- Using .contains(t) — wrong criterion.
- Forgetting to shrink while valid — that’s how you get the minimum.
- Not distinguishing need[c] >= 0 vs < 0 when decrementing — only >= 0 reduces missing.
- Not restoring counts when moving left.
- Returning empty when t is longer than s — early return "" is fine.

## Skeleton (Java)
```java

    public String minWindow(String s, String t) {
    if (s == null || t == null) throw new IllegalArgumentException("String or target can not be null");
    if (t.isEmpty() || s.isEmpty() || t.length() > s.length()) return "";

    int[] need = new int[128];
    for (int i = 0; i < t.length(); i++) need[t.charAt(i)]++;

    int missing = t.length();
    int left = 0, bestStart = 0, bestLen = Integer.MAX_VALUE;

    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        // decrement need[c], adjust missing
        if (--need[c] >= 0) {
            missing--;
        }

        // shrink while valid
        while (missing == 0) {
            // update best
            int winLen = right - left + 1;
            if (winLen < bestLen) {
                bestLen = winLen;
                bestStart = left;
            }

            char cLeft = s.charAt(left++);
            if (++need[cLeft] > 0) {
                // window no longer covers t
                missing++;
            }
        }
    }

    return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
}

```
## Debugging tip
If it doesn’t pass "ADOBECODEBANC" / "ABC", log left, right, missing, and need['A'/'B'/'C'] at key steps to spot off-by-one or count-restore mistakes quickly.
