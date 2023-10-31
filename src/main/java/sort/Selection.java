package sort;

import collection.list.SimpleList;
import java.util.List;

public class Selection<T extends Comparable<T>> implements Sorter<T> {
    public List<T> sort(final List<T> list) {
        List<T> result = new SimpleList<>();
        if (list.isEmpty()) return result;
        List<T> unsorted = new SimpleList<>(list);


        int index = 0;
        while (!unsorted.isEmpty()) {
            for (int i = 0; i < unsorted.size(); i++) {
                T hold = unsorted.get(index);
                T target = unsorted.get(i);
                if (hold.compareTo(target) > 0) {
                    index = i;
                }
            }
            result.add(unsorted.remove(index));
            index = 0;
        }

        return result;
    }
}
