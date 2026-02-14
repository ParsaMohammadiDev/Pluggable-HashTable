package ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers;

import javax.management.openmbean.KeyAlreadyExistsException;
import ir.ac.kntu.hashtable.util.Counter;
import ir.ac.kntu.hashtable.util.collisionhandlers.CollisionHandler;

public abstract class OpenAddresser implements CollisionHandler {
    private static final Object DELETED = new Object();

    @Override
    public void add(Object key, int initialIndex, Object[] hashTable, Counter counter) {
        int insertIndex = getInsertIndex(key, initialIndex, hashTable, counter);
        hashTable[insertIndex] = key;
    }

    @Override
    public boolean find(Object key, int initialIndex, Object[] hashTable, Counter counter) {
        int keyIndex = getKeyIndex(key, initialIndex, hashTable, counter);
        return keyIndex != -1;
    }

    @Override
    public void delete(Object key, int initialIndex, Object[] hashTable, Counter counter) {
        int keyIndex = getKeyIndex(key, initialIndex, hashTable, counter);
        if(keyIndex == -1)
            throw new IllegalArgumentException("key not found");
        
        hashTable[keyIndex] = DELETED;
    }

    public int getKeyIndex(Object key, int initialIndex, Object[] hashTable,  Counter counter) {
        int tableSize = hashTable.length;
        for(int step = 0; step < tableSize; step ++) {
            int probeIndex = (int) (getProbeIndex(initialIndex, step, key) % tableSize);
            Object slot = hashTable[probeIndex];
            if(slot == null)
                return -1;
            if(slot != DELETED && slot.equals(key))
                return probeIndex;

            counter.countProbing();
        }

        return -1;
    }

    public int getInsertIndex(Object key, int initialIndex, Object[] hashTable, Counter counter) {
        int tableSize = hashTable.length;
        int firstDeletedIndex = -1;
        for(int step = 0; step < tableSize; step ++) {
            int probeIndex = (int) (getProbeIndex(initialIndex, step, key) % tableSize);
            Object slot = hashTable[probeIndex];
            if(slot == null)
                return firstDeletedIndex == -1 ? probeIndex : firstDeletedIndex;
            if(slot == DELETED && firstDeletedIndex == -1)
                firstDeletedIndex = probeIndex;
            if(slot != DELETED && key.equals(slot))
                throw new KeyAlreadyExistsException();
            if(step > 0 && slot != DELETED && !slot.equals(key)) {
                counter.countCollision();
            }
            counter.countProbing();
        }

        if(firstDeletedIndex == -1)
            throw new IllegalStateException("hash table is full");

        return firstDeletedIndex;
    }

    public abstract long getProbeIndex(int initialIndex, int step, Object key);
}