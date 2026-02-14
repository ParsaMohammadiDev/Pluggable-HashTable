package ir.ac.kntu.hashtable.util.hashfunctions;

public class DivisionMethod implements HashFunction{
    private final int modulus;

    public DivisionMethod(int tableSize) {
        this.modulus = getNextPrime(tableSize);
    }

    @Override
    public long hash(Object obj) {
        if(!(obj instanceof Integer key))
            throw new IllegalArgumentException("division method accepts integers as keys");

        return key % modulus;
    }

    private int getNextPrime(int bound) {
        if(bound == 2)
            return bound;

        if(bound % 2 == 0)
            bound += 1;

        while(!isPrime(bound)) {
            bound += 2;
        }
        return bound;
    }

    private boolean isPrime(int num) {
        if(num < 2)
            return false;
        else if(num % 2 == 0)
            return num == 2;

        for(int i = 3; i <= Math.sqrt(num); i += 2) {
            if(num % i == 0)
                return false;
        }
        return true;
    }
}