package com.training.socket;

import java.net.Socket;

/**
 * george 2019/4/2 11:32
 */
public class Test {
    public static void main(String[] args) {
        boolean show = false;
        int i = 1;
        for (; ; ) {
            if (show || i < 6) {
                System.out.println("加能力");
                i++;
            }
        }
    }
}
