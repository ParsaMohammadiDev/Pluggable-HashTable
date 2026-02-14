package ir.ac.kntu.hashtable.util.collisionhandlers;

import ir.ac.kntu.hashtable.util.Counter;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class ChainingHandler implements CollisionHandler {
    @Override
    public void add(Object key, int initialIndex, Object[] hashTable, Counter counter) {
        int insertIndex = getInsertIndex(key, initialIndex, hashTable, counter);
        List<Object> chain;
        if(hashTable[insertIndex] == null) {
            chain = new ArrayList<>();
            hashTable[insertIndex] = chain;
        } else {
            chain = getChain(hashTable, insertIndex);
        }
        if(chain.contains(key))
            throw new KeyAlreadyExistsException();

        chain.add(key);
    }

    @Override
    public boolean find(Object key, int initialIndex, Object[] hashTable, Counter counter) {
        List<Object> chain = getChain(hashTable, initialIndex);
        if(chain == null)
            return false;

        return chain.contains(key);
    }

    @Override
    public void delete(Object key, int initialIndex, Object[] hashTable,  Counter counter) {
        List<Object> chain = getChain(hashTable, initialIndex);
        if(chain == null)
            throw new IllegalArgumentException("key not found");

        chain.remove(key);
        if(chain.isEmpty())
            hashTable[initialIndex] = null;
    }

    private int getInsertIndex(Object key, int initialIndex, Object[] hashTable, Counter counter) {
        if(hashTable[initialIndex] != null)
            counter.countCollision();

        return initialIndex;
    }

    private List<Object> getChain(Object[] hashTable, int index) {
        return (List<Object>) (hashTable[index]);
    }
}