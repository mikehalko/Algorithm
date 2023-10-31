package collection;

import collection.list.SimpleList;
import generator.Generator;
import model.Person;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.List;

public class CollectionData {

    @SuppressWarnings("unchecked")
    public static <T> T[] array(Generator<T> gen, Class<T> type, int size) {
        T[] array = (T[]) Array.newInstance(type, size);
        return new ListData<>(gen, size).toArray(array);
    }

    public static class ListData<T> extends SimpleList<T> {
        private ListData(Generator<T> gen, int quantity) {
            for (int i = 0; i < quantity; i++)
                add(gen.next());
        }
    }

    public static class MapData<K, V> extends LinkedHashMap<K, V> {

        public MapData(Generator<Pair<K, V>> gen, int quantity) {
            for (int i = 0; i < quantity; i++) {
                Pair<K, V> p = gen.next();
                put(p.key, p.value);
            }
        }

        public MapData(Generator<K> genK, Generator<V> genV,
                       int quantity) {
            for (int i = 0; i < quantity; i++) {
                put(genK.next(), genV.next());
            }
        }

        public MapData(Generator<K> genK, V value, int quantity) {
            for (int i = 0; i < quantity; i++) {
                put(genK.next(), value);
            }
        }

        public MapData(Iterable<K> genK, Generator<V> genV) {
            for (K key : genK) {
                put(key, genV.next());
            }
        }

        public MapData(Iterable<K> genK, V value) {
            for (K key : genK) {
                put(key, value);
            }
        }
    }
}