package org.js;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SortUsingSumDigits {

    // Sort numbers according to sum of their digits
    public static int getSum(Integer x) {
        int sum = 0;
        while (x > 0) {
            sum = sum + x%10;
            x = x/10;
        }
        return sum;
    }

    public static void main(String [] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        arrayList.add(10);
        arrayList.add(22);
        arrayList.add(121);
        arrayList.add(14);
        arrayList.add(40);
        arrayList.add(35);
        arrayList.add(51);

        /*arrayList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return getSum(o1) - getSum(o2);
            }
        });*/

        arrayList.sort((a, b) -> (getSum(a) - getSum(b)));
        System.out.println(arrayList);
    }
}