package ir.ac.kntu.hashtable.util.hashfunctions;

public class PolynomialRollingHash implements HashFunction {
    private static final int PRIME_BASE = 31;
    private static final long PRIME_MODULUS = (long) (1e9 + 7);

    @Override
    public long hash(Object obj) {
        if(!(obj instanceof String key))
            throw new IllegalArgumentException("ASCII hashing accepts strings as keys");
        
        long hash = 0;
        for(int i = 0; i < key.length(); i ++) {
            hash += key.charAt(i) * (long) (Math.pow(PRIME_BASE, i));
        }

        return hash % PRIME_MODULUS;
    }
}
