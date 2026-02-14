package ir.ac.kntu.hashtable.util.hashfunctions;

public class MultiplactionMethod implements HashFunction {
    private final int modulus;
    private static final double CONST = (Math.sqrt(5) - 1) / 2;

    public MultiplactionMethod(int tableSize) {
        this.modulus = getNextTwoPow(tableSize);
    }

    @Override
    public long hash(Object obj) {
        if(!(obj instanceof Integer key))
            throw new IllegalArgumentException("multiplication method accepts integers as keys");

        double mult = key * CONST;
        return (long) (Math.floor(modulus * (mult - Math.floor(mult))));
    }

    private int getNextTwoPow(int bound) {
        return (int)(Math.pow(2, Math.ceil(Math.log(bound) / Math.log(2))));
    }
}