package generator;

public class EntityGenerator {
    private static RandomGenerator.String stringGen = new RandomGenerator.String();
    private static RandomGenerator.Integer integerGen = new RandomGenerator.Integer();

    public static class Person implements Generator<model.Person> {
        private int ageMod = 100;

        @Override
        public model.Person next() {
            return new model.Person(integerGen.next(), stringGen.next(), integerGen.next() % ageMod);
        }
    }
}
