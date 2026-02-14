package ir.ac.kntu.hashtable.util.collisionhandlers;

import ir.ac.kntu.hashtable.util.Counter;

public interface CollisionHandler {
    public boolean find(Object key, int initialIndex, Object[] hashTable, Counter counter);
    public void add(Object key, int initialIndex, Object[] hashTable, Counter counter);
    public void delete(Object key, int initialIndex, Object[] hashTable, Counter counter);
}