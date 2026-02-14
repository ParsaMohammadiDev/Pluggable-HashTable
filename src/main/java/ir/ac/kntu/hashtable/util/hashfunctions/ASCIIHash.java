package ir.ac.kntu.hashtable.util.hashfunctions;

public class ASCIIHash implements HashFunction {
    private final long tableSize;

    public ASCIIHash(long tableSize) {
        this.tableSize = tableSize;
    }

    @Override
    public long hash(Object obj) {
        if(!(obj instanceof String key))
            throw new IllegalArgumentException("ASCII hashing accepts strings as keys");

        return (int) (key.chars().sum() % tableSize);
    }
}
