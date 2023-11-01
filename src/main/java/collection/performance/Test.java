package collection.performance;


public abstract class Test<C> {
    String name;

    public Test(String name) {
        this.name = name;
    }

    // Returns actual number of repetitions of test.
    public abstract int test(C container, TestParam param);
}