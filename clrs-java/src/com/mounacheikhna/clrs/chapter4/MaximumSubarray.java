package com.mounacheikhna.clrs.chapter4;

public class MaximumSubarray {

    SubArraySum findMaximumSubarray(int[] arr) {
        int max = Integer.MIN_VALUE;
        int left = 0;
        int right = 0;
        int sum;
        for (int i = 0; i < arr.length; i++) {
            sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if(max < sum) {
                    max = sum;
                    left = i;
                    right = j;
                }
            }
        }

        return new SubArraySum(left, right, max);
    }

    class SubArraySum {
        int left;
        int right;
        int sum;

        public SubArraySum(int left, int right, int sum) {
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
        System.out.println(maximumSubarray.findMaximumSubarray(new int[]{-2, -3, -1, -4}));
    }

}
