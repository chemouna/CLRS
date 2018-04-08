package com.mounacheikhna.clrs.chapter4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaximumSubarray {

    SubArraySum findMaximumSubarrayBruteForce(int[] arr) {
        int max = Integer.MIN_VALUE;
        int left = 0;
        int right = 0;
        int sum;
        for (int i = 0; i < arr.length; i++) {
            sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if (max < sum) {
                    max = sum;
                    left = i;
                    right = j;
                }
            }
        }

        return new SubArraySum(left, right, max);
    }

    SubArraySum findMaximumSubarrayRec(int[] arr, int low, int high) {
        if (high == low) {
            return new SubArraySum(low, high, arr[low]);
        } else {
            int mid = (low + high) / 2;
            SubArraySum leftSubSum = findMaximumSubarrayRec(arr, low, mid);
            SubArraySum rightSubSum = findMaximumSubarrayRec(arr, mid + 1, high);
            SubArraySum crossingSubArray = findMaximumCrossingSubArray(arr, low, mid, high);

            if (leftSubSum.sum >= rightSubSum.sum && leftSubSum.sum >= crossingSubArray.sum) {
                return leftSubSum;
            } else if (rightSubSum.sum >= leftSubSum.sum && rightSubSum.sum >= crossingSubArray.sum) {
                return rightSubSum;
            } else {
                return crossingSubArray;
            }
        }
    }

    private SubArraySum findMaximumCrossingSubArray(int[] arr, int low, int mid, int high) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        int maxLeft = 0;
        for (int i = mid; i >= low; i--) {
            sum += arr[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        int maxRight = 0;
        for (int i = mid + 1; i <= high; i++) {
            sum += arr[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }
        return new SubArraySum(maxLeft, maxRight, leftSum + rightSum);
    }

    SubArraySum findMaximumSubarrayMixed(int[] arr, int low, int high) {
        if (arr.length < 52) {
            return findMaximumSubarrayBruteForce(arr);
        } else {
            int mid = (low + high) / 2;
            SubArraySum leftSubSum = findMaximumSubarrayRec(arr, low, mid);
            SubArraySum rightSubSum = findMaximumSubarrayRec(arr, mid + 1, high);
            SubArraySum crossingSubArray = findMaximumCrossingSubArray(arr, low, mid, high);

            if (leftSubSum.sum >= rightSubSum.sum && leftSubSum.sum >= crossingSubArray.sum) {
                return leftSubSum;
            } else if (rightSubSum.sum >= leftSubSum.sum && rightSubSum.sum >= crossingSubArray.sum) {
                return rightSubSum;
            } else {
                return crossingSubArray;
            }
        }
    }


    class SubArraySum {
        int left;
        int right;
        int sum;

        SubArraySum(int left, int right, int sum) {
            this.left = left;
            this.right = right;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "SubArraySum{" +
                    "left=" + left +
                    ", right=" + right +
                    ", sum=" + sum +
                    '}';
        }
    }

    public static void main(String[] args) {
        MaximumSubarray maximumSubarray = new MaximumSubarray();
        int[] allNegativeArr = {-2, -3, -1, -4};
        System.out.println(maximumSubarray.findMaximumSubarrayBruteForce(allNegativeArr));
        System.out.println(maximumSubarray.findMaximumSubarrayRec(allNegativeArr, 0, allNegativeArr.length - 1));

        int[] clrsArr = {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println(maximumSubarray.findMaximumSubarrayBruteForce(clrsArr));
        System.out.println(maximumSubarray.findMaximumSubarrayRec(clrsArr, 0, clrsArr.length - 1));

        int[] oneElementArr = {13};
        System.out.println(maximumSubarray.findMaximumSubarrayBruteForce(oneElementArr));
        System.out.println(maximumSubarray.findMaximumSubarrayRec(oneElementArr, 0, oneElementArr.length - 1));

        System.out.println("-- Tests to find the crossover point --");
        List<Integer> values = new ArrayList<>();
        Random random = new Random();
        int max = 52;
        for (int i = 0; i < 55; i++) {
            values.add(random.nextInt(max) * (random.nextBoolean() ? -1 : 1));
        }

        int[] arr = values.stream().mapToInt(i -> i).toArray();
        long startBruteForce = System.nanoTime();

        SubArraySum resBruteForce = maximumSubarray.findMaximumSubarrayBruteForce(arr);

        long endBruteForce = System.nanoTime();
        long durationBruteForce = endBruteForce - startBruteForce;

        long startRec = System.nanoTime();
        SubArraySum resRec = maximumSubarray.findMaximumSubarrayRec(arr, 0, arr.length - 1);
        long endRec = System.nanoTime();
        long durationRec = endRec - startRec;

        long startMixed = System.nanoTime();
        SubArraySum resMixed = maximumSubarray.findMaximumSubarrayMixed(arr, 0, arr.length - 1);
        long endMixed = System.nanoTime();
        long durationMixed = endMixed - startMixed;

        System.out.println("Duration for brute force : " + durationBruteForce +
                "\nDuration for recursive : " + durationRec +
                "\nDuration for mixed approach : " + durationMixed);

        // 4.1-4
        int[] emptyArr = new int[]{};
        System.out.println(maximumSubarray.findMaximumSubarrayBruteForce(emptyArr));
        System.out.println(maximumSubarray.findMaximumSubarrayRec(emptyArr, 0, 0));
    }

    // On my machine the crossover point where the recursove algorithm beats the brute force algorithm happens at 52
    // elements (n0 = 52) , where Duration for brute force = 27872 , Duration for recursive = 27866 (in nano seconds)

    // the mixed approach is faster then both recursive and brute force methods for n <= 52 but seems to slow down after
    // n0 = 55 , so the crossover point doesnt change significantly

    /* 4.1-4 :
     * for negative sums result it's better to return an empty subarray with sum = 0 which is bigger then the negative
     * sum, we can do this by initializing leftSum and rightSum in findMaximumCrossingSubArray to 0 instead of
     * Integer.MIN_VALUE
    */

}
