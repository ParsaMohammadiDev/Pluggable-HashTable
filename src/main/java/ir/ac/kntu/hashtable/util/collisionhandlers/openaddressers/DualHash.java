package ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers;

import ir.ac.kntu.hashtable.util.hashfunctions.PolynomialRollingHash;
import ir.ac.kntu.hashtable.util.hashfunctions.HashFunction;

public class DualHash extends OpenAddresser {
    private static final int X = 997;
    private static final HashFunction hashFunc = new PolynomialRollingHash();

    @Override
    public long getProbeIndex(int initialIndex, int step, Object key) {
        return initialIndex + (step * secondaryHash(key));
    }

    private long secondaryHash(Object key) {
        long hashValue = hashFunc.hash(key);
        return X - (hashValue % X);
    }
}
