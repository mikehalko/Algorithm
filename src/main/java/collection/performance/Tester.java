package collection.performance;

import java.io.PrintStream;
import java.util.*;

public class Tester<C> {
    public static TestParam[] defaultParams = TestParam.array(
            10, 5000, 100, 5000, 1000, 5000, 10000, 500);

    // Override this to modify pre-test initialization:
    protected C initialize(int size) {
        return container;
    }

    public static int fieldWidth = 8;

    private static String stringField() {
        return "%" + fieldWidth + "s";
    }
    private static String numberField() {
        return "%" + fieldWidth + "d";
    }

    private static int sizeWidth = 5;
    private static String sizeField = "%" + sizeWidth + "s";


    private TestParam[] paramList = defaultParams;

    protected C container;
    private String headline = "";
    private List<Test<C>> tests;
    private PrintStream outPrint;

    public Tester(PrintStream streamOutPrint, C container, List<Test<C>> tests) {
        this.container = container;
        this.tests = tests;
        if (container != null)
            headline = container.getClass().getSimpleName();
        this.outPrint = streamOutPrint;
    }

    public Tester(PrintStream streamOutPrint, C container, List<Test<C>> tests, TestParam[] paramList) {
        this(streamOutPrint, container, tests);
        this.paramList = paramList;
        this.outPrint = streamOutPrint;
    }


    public void setHeadline(String newHeadline) {
        headline = newHeadline;
    }

    public static <C> void run(C container, List<Test<C>> tests, PrintStream streamOutPrint) {
        new Tester<C>(streamOutPrint, container, tests).timedTest();
    }

    public static <C> void run(C container, List<Test<C>> tests, TestParam[] paramList, PrintStream streamOutPrint) {
        new Tester<C>(streamOutPrint, container, tests, paramList).timedTest();
    }

    public void timedTest() {
        displayHeader();
        for (TestParam param : paramList) {
            outPrint.format(sizeField, param.size);
            for (Test<C> test : tests) {
                C container = initialize(param.size);
                long start = System.nanoTime();

                int repetitions = test.test(container, param);
                long duration = System.nanoTime() - start;
                long timePerRepetition = duration / repetitions; // Nanoseconds
                outPrint.format(numberField(), timePerRepetition);
            }
            outPrint.println();
        }
    }

    private void displayHeader() {
        int width = fieldWidth * tests.size() + sizeWidth;
        int dashLength = width - headline.length() - 1;
        StringBuilder head = new StringBuilder(width);
        for (int i = 0; i < dashLength / 2; i++)
            head.append('-');
        head.append(' ');
        head.append(headline);
        head.append(' ');
        for (int i = 0; i < dashLength / 2; i++)
            head.append('-');

        outPrint.println(head);
        // Print column headers:
        outPrint.format(sizeField, "size");
        for (Test<C> test : tests)
            outPrint.format(stringField(), test.name);
        outPrint.println();
    }
}