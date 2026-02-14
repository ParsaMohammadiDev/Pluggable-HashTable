package ir.ac.kntu.hashtable;

import ir.ac.kntu.hashtable.util.collisionhandlers.CollisionHandler;
import ir.ac.kntu.hashtable.util.hashfunctions.HashFunction;
import ir.ac.kntu.hashtable.util.Counter;

public class HashTable {
    private final HashFunction hashFunc;
    private final CollisionHandler handler;
    private final Counter counter;
    private final Object[] hashTable;
    private final long tableSize;

    private int size;

    public HashTable(HashFunction hashFunc, int tableSize, CollisionHandler handler, Counter counter) {
        this.hashFunc = hashFunc;
        this.hashTable = new Object[tableSize];
        this.tableSize = tableSize;
        this.counter = counter;
        this.handler = handler;
        this.size = 0;
    }

    public void add(Object key) {
        long initialHash = hashFunc.hash(key);
        int initialIndex = (int) (initialHash % tableSize);
        handler.add(key, initialIndex, hashTable, counter);
        size ++;
    }

    public boolean find(Object key) {
        long initialHash = hashFunc.hash(key);
        int initialIndex = (int) (initialHash % tableSize);
        return handler.find(key, initialIndex, hashTable, counter);
    }

    public void delete(Object key) {
        long initialHash = hashFunc.hash(key);
        int initialIndex = (int) (initialHash % tableSize);
        handler.delete(key, initialIndex, hashTable, counter);
        size --;
    }

    public int size() {
        return size;
    }
}