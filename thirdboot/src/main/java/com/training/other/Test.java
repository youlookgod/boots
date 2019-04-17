package com.training.other;

import io.netty.buffer.ByteBufUtil;
import org.apache.tomcat.util.buf.ByteBufferUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * george 2019/2/13 14:30
 */
public class Test {
    private static final AtomicInteger ctl = new AtomicInteger(ctlOf(-1 << (Integer.SIZE - 3), 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    public static void main(String[] args) {
        String udId = "AFC340DB9F97E9EE4409A945982F8B8A";
        int i = Math.abs(udId.hashCode()) % 6;
        System.out.println(i);
    }

}
