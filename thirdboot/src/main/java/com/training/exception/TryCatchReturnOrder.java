package com.training.exception;

/**
 * george 2018/12/3 17:57
 * try{}catch(){}finally{}中各种return情况的执行顺序，及return结果
 */
public class TryCatchReturnOrder {
    public static void main(String[] args) {
        int i = test();
        System.out.println(i);
    }

    /**
     * 功能描述:
     * 1.不管有没有出现异常，finally块中代码都会执行；
     * 2.当try和catch中有return时，finally仍然会执行；
     * 3.finally是在return后面的表达式运算后执行的（此时并没有返回运算后的值，而是先把要返回的值保存起来，不管finally中的代码怎么样，返回的值都不会改变，
     * 任然是之前保存的值），所以函数返回值是在finally执行前确定的；
     * 4.finally中最好不要包含return，否则程序会提前退出，返回值不是try或catch中保存的返回值。
     * **总结**: 任何执行try 或者catch中的return语句之前,都会先执行finally语句，此时若finally中有return则直接返回。
     */
    public static int test() {
        int i = 10;
        try {
            i = i - 8;
            return i;
        } catch (Exception e) {
            return 0;
        } finally {
            return 3;
        }
    }
}
