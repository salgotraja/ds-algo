package org.js.array;

import java.util.Arrays;
import java.util.Scanner;

public class TwoDimensionalArraysInput {
    // Create a 2D array of size N X M and read it
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // no. of rows
        int M = sc.nextInt(); // no. of cols

        int [][] arr = new int[N][M];

        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        System.out.println(Arrays.deepToString(arr));
    }
}
