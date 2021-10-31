package ru.otus.jol;

import org.openjdk.jol.info.ClassLayout;

// -Djdk.attach.allowAttachSelf
// -Djol.magicFieldOffset=true

//-XX:ObjectAlignmentInBytes=256
//-XX:-UseCompressedOops
//-XX:-UseCompressedClassPointers
public class RecordLayoutExample {
    public static void main(String[] args) {
        new RecordLayoutExample().demo();
    }

    public void demo() {

        // System.out.println(ClassLayout.parseClass(TestBr.class).toPrintable());
        // System.out.println(ClassLayout.parseInstance(new TestBr(false)).toPrintable());

        Xy xy = new Xy(3, 2);
        int sum = xy.sum();
        System.out.println("sum:" + sum);

        XyClass xyClass = new XyClass(3, 2);
        int sumClass = xyClass.sum();
        System.out.println("sumClass:" + sumClass);

    }

    public record Xy(int x, int y) {
        public int sum() {
            return x + y;
        }
    }

    public static class XyClass {
        private final int x;
        private final int y;

        public XyClass(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int sum() {
            return x + y;
        }

        @Override
        public String toString() {
            return "XyClass{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public enum TestEnum {
        TRUE(true);
        boolean flag;

        TestEnum(boolean flag) {
            this.flag = flag;
        }
    }

}
