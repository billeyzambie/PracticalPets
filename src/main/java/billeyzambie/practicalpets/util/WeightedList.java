package billeyzambie.practicalpets.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedList<T> {

    private static class Entry<T> {
        T item;
        float weight;

        Entry(T item, float weight) {
            this.item = item;
            this.weight = weight;
        }
    }

    private final List<Entry<T>> entries = new ArrayList<>();
    private float totalWeight = 0;
    private final Random random = new Random();

    public void add(T item, float weight) {
        if (weight == 0)
            return;
        if (weight < 0)
            throw new IllegalArgumentException("Weight must be positive");
        entries.add(new Entry<>(item, weight));
        totalWeight += weight;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public T getRandomChoice() {
        if (entries.isEmpty())
            throw new IllegalStateException("Weighted list was empty");
        float randomFloat = random.nextFloat() * totalWeight;
        float weightAddedSoFar = 0;
        for (Entry<T> entry : entries) {
            weightAddedSoFar += entry.weight;
            if (randomFloat < weightAddedSoFar) {
                return entry.item;
            }
        }
        throw new AssertionError("This will never happen");
    }
}