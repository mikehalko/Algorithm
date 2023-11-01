package program;

import collection.CollectionData;
import collection.list.SimpleList;
import collection.performance.Test;
import collection.performance.TestParam;
import collection.performance.Tester;
import generator.EntityGenerator;
import generator.Generator;
import model.Person;

import java.io.PrintStream;
import java.util.*;

public class ListPerformance {
    static Random rand = new Random();
    static int reps = 1000;
    static List<Test<List<Person>>> tests = new ArrayList<Test<List<Person>>>();
    static List<Test<LinkedList<Person>>> qTests = new ArrayList<Test<LinkedList<Person>>>();

    static {
        tests.add(new Test<List<Person>>("add") {
            public int test(List<Person> list, TestParam tp) {
                int loops = tp.loops;
                int listSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    list.clear();
                    for (int j = 0; j < listSize; j++)
                        list.add(new Person(j, "", j));
                }
                return loops * listSize;
            }
        });
        tests.add(new Test<List<Person>>("get") {
            public int test(List<Person> list, TestParam tp) {
                int loops = tp.loops * reps;
                int listSize = list.size();
                for (int i = 0; i < loops; i++)
                    list.get(rand.nextInt(listSize));
                return loops;
            }
        });
        tests.add(new Test<List<Person>>("set") {
            public int test(List<Person> list, TestParam tp) {
                int loops = tp.loops * reps;
                int listSize = list.size();
                Person person = new Person(-1, "set-test", 10);
                for (int i = 0; i < loops; i++)
                    list.set(rand.nextInt(listSize), person);
                return loops;
            }
        });
    }

    static class ListTester extends Tester<List<Person>> {
        public ListTester(PrintStream streamOutPrint, List<Person> container, List<Test<List<Person>>> tests) {
            super(streamOutPrint, container, tests);
        }

        // Fill to the appropriate size before each test:
        @Override
        protected List<Person> initialize(int size) {
            container.clear();
            container.addAll(new CollectionData.ListData<Person>(new EntityGenerator.Person(), size));
            return container;
        }

        // Convenience method:
        public static void run(PrintStream streamOutPrint,  List<Person> list, List<Test<List<Person>>> tests) {
            new ListTester(streamOutPrint, list, tests).timedTest();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0)
            Tester.defaultParams = TestParam.array(args);

        // Can only do these two tests on an array:
        Generator<Person> personGenerator = new EntityGenerator.Person(); // set seed
        Tester<List<Person>> arrayTest =
                new Tester<List<Person>>(System.out, null, tests.subList(1, 3)) {
                    @Override
                    protected List<Person> initialize(int size) {
                        Person[] ia = CollectionData.array(personGenerator, Person.class, size);
                        return Arrays.asList(ia);
                    }
                };
        arrayTest.setHeadline("Array as List");
//        arrayTest.timedTest();


        Tester.defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
        ListTester.run(System.out, new ArrayList<Person>(), tests);
        ListTester.run(System.out, new LinkedList<Person>(), tests);
//        ListTester.run(System.out, new Vector<Person>(), tests);
        ListTester.run(System.out, new SimpleList<Person>(), tests);
    }
}