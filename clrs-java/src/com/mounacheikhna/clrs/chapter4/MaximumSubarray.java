package com.mounacheikhna.clrs.chapter4;

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

    // TODO: test the boundary case of an array with one element

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
    }

}
