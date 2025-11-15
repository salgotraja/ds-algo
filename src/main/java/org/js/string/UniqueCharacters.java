package org.js.string;

import java.util.HashMap;

/**
 * Algorithm to determine if a string has all unique characters.
 */
public class UniqueCharacters {
    public static void main(String[] args) {
        UniqueCharacters unique = new UniqueCharacters();
        String str = "abecdef";
//        System.out.println(unique.unique(str));

        String str1 = "abeec";
        System.out.println(unique.isUniqueChars(str1));
    }

    public boolean unique(String str) {
        if (str == null || str.isEmpty() || str.length() > 128) {
            return false;
        }

        boolean[] char_set = new boolean[128];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (char_set[val]) {
                return false;
            }
            char_set[val] = true;
        }

        return true;
    }


    boolean isUniqueChars(String str) {
        if (str == null || str.isEmpty() || str.length() > 128) {
            return false;
        }
        int checker = 0;

        for (int i = 0; i < str.length(); i++) {
            int value = str.charAt(i) - 'a';
            if ((checker & (1 << value)) > 0) {
                return false;
            }

            checker |= (1 << value);
        }
        return true;
    }

    public boolean uniqueChars(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty");
        }
        if (str.length() > 128) {
            return false;
        }

        var map = new HashMap<Character, Integer>();

        for (int i = 0; i < str.length(); i++) {
            if (!map.containsKey(str.charAt(i))) {
                map.put(str.charAt(i), i);
            } else {
                return false;
            }
        }

        return true;
    }
}
