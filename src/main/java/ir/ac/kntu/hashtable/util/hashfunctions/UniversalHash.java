package ir.ac.kntu.hashtable.util.hashfunctions;

import java.util.Random;

public class UniversalHash implements HashFunction {
    private final long a;
    private final long b;
    private final int tableSize;
    private static final long BOUND = 100_000_007;

    @Override
    public long hash(Object obj) {
        if(!(obj instanceof Integer key))
            throw new IllegalArgumentException("division method accepts integers as keys");

        return ((a * key + b) % BOUND) % tableSize;
    }

    public UniversalHash(int tableSize) {
        this.tableSize = tableSize;
        Random random = new Random();
        a = random.nextLong(1, BOUND);
        b = random.nextLong(BOUND);
    }
}
