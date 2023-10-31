package generator;

import java.util.Random;

public class RandomGenerator {
    private static Random random = new Random(24);

    public static class Character implements Generator<java.lang.Character> {
        int index = -1;
        static char[] chars = ("aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ").toCharArray();

        public java.lang.Character next() {
            return chars[random.nextInt(chars.length)];
        }
    }

    public static class String implements Generator<java.lang.String> {
        private int length = 5;
        Generator<java.lang.Character> cg = new Character();

        public String() {
        }

        public String(int length) {
            this.length = length;
        }

        public java.lang.String next() {
            char[] buf = new char[length];
            for (int i = 0; i < length; i++)
                buf[i] = cg.next();
            return new java.lang.String(buf);
        }
    }

    public static class Integer implements Generator<java.lang.Integer> {
        private int mod = 24000;

        public Integer() {
        }

        public Integer(int modulo) {
            this.mod = modulo;
        }

        public java.lang.Integer next() {
            return random.nextInt(mod);
        }
    }
}
