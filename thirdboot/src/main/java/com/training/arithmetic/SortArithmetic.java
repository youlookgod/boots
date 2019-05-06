package com.training.arithmetic;

/**
 * 排序算法
 * george 2018/9/14 14:53
 */
public class SortArithmetic {
    public static void main(String[] args) {
        testCharBubbleSort();
    }

    //冒泡排序
    private static void testCharBubbleSort(){
        String str = "n8ac";
        char[] c = str.toCharArray();
        int length = str.length();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length-1-i; j++){

                if(c[j] > c[j+1]){
                    char temp = c[j];
                    c[j] = c[j+1];
                    c[j+1] = temp;
                }
            }

        }

        String s = String.valueOf(c);
        System.out.println(s);
    }

    //选择排序
    private static void selectSort(){
        int[] num = new int[]{29,43,91,9,2,33,3,71,13,73,107,1,6};
        int length = num.length;
        for(int i = 0; i < length; i++){
            int minIndex = i;
            for(int j = i+1; j < length; j++){
                if(num[j] < num[minIndex]){
                    minIndex = j;
                }
            }

            int temp = num[i];
            num[i] = num[minIndex];
            num[minIndex] = temp;
        }

        System.out.println(num);
    }

    //希尔排序
    private static void shellSort(){
        int[] num = new int[]{29,43,91,9,2,33,3,71,13,73,107,1,6};
        int length = num.length;

    }
}
