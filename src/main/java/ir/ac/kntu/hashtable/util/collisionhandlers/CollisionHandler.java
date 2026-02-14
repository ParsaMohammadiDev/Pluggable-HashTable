package ir.ac.kntu.hashtable.util.collisionhandlers;

import ir.ac.kntu.hashtable.util.Counter;

public interface CollisionHandler {
    boolean find(Object key, int initialIndex, Object[] hashTable, Counter counter);
    void add(Object key, int initialIndex, Object[] hashTable, Counter counter);
    void delete(Object key, int initialIndex, Object[] hashTable, Counter counter);
}