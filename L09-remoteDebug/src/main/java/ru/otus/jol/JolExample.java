package ru.otus.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

//sudo java -Xms512m -Xmx512m -XX:MaxTenuringThreshold=5 -Djol.tryWithSudo=true -Djol.magicFieldOffset=true -jar jolExample.jar
public class JolExample {
    private static volatile Object sink;

    public static void main(String[] args) {
        new JolExample().demo();
    }

    public void demo() {

        objectLayout();

    }
//https://github.com/openjdk/jol/blob/master/jol-samples/src/main/java/org/openjdk/jol/samples/JOLSample_22_Promotion.java

    private void objectLayout() {
        TestB testB = new TestB();
        System.out.println(ClassLayout.parseInstance(testB).toPrintable());

        long lastAddr = VM.current().addressOf(testB);
        int moves = 0;
        for (int idx = 0; idx < 100000; idx++) {
            long cur = VM.current().addressOf(testB);
            if (cur != lastAddr) {
                moves++;
                System.out.printf("*** Move %2d, object is at %x%n%n", moves, cur);
                System.out.println(ClassLayout.parseInstance(testB).toPrintable());
                lastAddr = cur;
            }

            // make garbage
            for (int c = 0; c < 1000; c++) {
                sink = new Object();
            }
        }

        long finalAddr = VM.current().addressOf(testB);
        System.out.printf("*** Final object is at %x%n", finalAddr);
        System.out.println(ClassLayout.parseInstance(testB).toPrintable());


        System.gc();
        makeGarbage();
        sleep();
        System.out.println(ClassLayout.parseInstance(testB).toPrintable());

        System.gc();
        makeGarbage();
        sleep();

        System.out.println(ClassLayout.parseInstance(testB).toPrintable());
    }

    public class TestB {
        boolean valBool;
    }

    private void makeGarbage() {
        for (int idx = 0; idx < Integer.MAX_VALUE; idx++) {
            Object obj = new String[10000];
        }
    }


    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
