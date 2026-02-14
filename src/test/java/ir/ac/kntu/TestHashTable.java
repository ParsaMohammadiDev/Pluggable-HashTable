package ir.ac.kntu;

import ir.ac.kntu.hashtable.util.Counter;
import ir.ac.kntu.hashtable.HashTable;
import ir.ac.kntu.hashtable.util.collisionhandlers.ChainingHandler;
import ir.ac.kntu.hashtable.util.collisionhandlers.CollisionHandler;
import ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers.DualHash;
import ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers.LinearProbe;
import ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers.QuadricProbe;
import ir.ac.kntu.hashtable.util.hashfunctions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestHashTable {
    private final Counter counter = new Counter();
    private CollisionHandler handler;
    private HashFunction hashFunc;

    @Test
    public void testEmptyTable() {
        hashFunc = new DivisionMethod(10);
        handler = new ChainingHandler();
        HashTable table = new HashTable(hashFunc, 10, handler, counter);
        assertEquals(0, table.size());
        assertFalse(table.find(42));
    }

    @Test
    public void testDuplicateInsertionThrows() {
        hashFunc = new DivisionMethod(10);
        handler = new ChainingHandler();
        HashTable table = new HashTable(hashFunc, 10, handler, counter);
        table.add(5);
        assertThrows(javax.management.openmbean.KeyAlreadyExistsException.class, () -> table.add(5));
    }

    @Test
    public void testDeleteNonExistentThrows() {
        hashFunc = new DivisionMethod(10);
        handler = new ChainingHandler();
        HashTable table = new HashTable(hashFunc, 10, handler, counter);
        assertThrows(IllegalArgumentException.class, () -> table.delete(99));
    }

    @Test
    public void testChainingHandlerBasicOps() {
        hashFunc = new DivisionMethod(10);
        handler = new ChainingHandler();
        counter.reset();
        HashTable table = new HashTable(hashFunc, 10, handler, counter);
        successIntHashTableTest(table);
    }

    @Test
    public void testLinearProbingBasicOps() {
        hashFunc = new DivisionMethod(10);
        handler = new LinearProbe();
        counter.reset();
        HashTable table = new HashTable(hashFunc, 10, handler, counter);
        successIntHashTableTest(table);
    }

    @Test
    public void testQuadricProbingBasicOps() {
        hashFunc = new DivisionMethod(10);
        handler = new QuadricProbe();
        counter.reset();
        HashTable table = new HashTable(hashFunc, 10, handler, counter);
        successIntHashTableTest(table);
    }

    @Test
    public void testDoubleHashingBasicOps() {
        hashFunc = new ASCIIHash(10);
        handler = new DualHash();
        counter.reset();
        HashTable table = new HashTable(hashFunc, 10, handler, counter);
        successStringHashTableTest(table);
    }

    @Test
    public void testNumericHashFunctions() {
        handler = new ChainingHandler();
        List<HashFunction> functions = List.of(new UniversalHash(10), new MultiplactionMethod(10));
        for (HashFunction h : functions) {
            HashTable hashTable = new HashTable(h, 10, handler, counter);
            successIntHashTableTest(hashTable);
        }
    }

    @Test
    public void testStringHashFunctions() {
        handler = new ChainingHandler();
        List<HashFunction> functions = List.of(new ASCIIHash(10), new PolynomialRollingHash());
        for (HashFunction h : functions) {
            HashTable hashTable = new HashTable(h, 10, handler, counter);
            successStringHashTableTest(hashTable);
        }
    }

    @Test
    public void testCollisionCounterIncrements() {
        hashFunc = new DivisionMethod(2);
        handler = new ChainingHandler();
        counter.reset();
        HashTable table = new HashTable(hashFunc, 2, handler, counter);
        table.add(2);
        table.add(4);
        assertTrue(counter.getCollisionCount() > 0);
    }

    @Test
    public void testProbeCounterIncrements() {
        hashFunc = new DivisionMethod(2);
        handler = new LinearProbe();
        counter.reset();
        HashTable table = new HashTable(hashFunc, 2, handler, counter);
        table.add(2);
        table.add(4);
        assertTrue(counter.getProbeCount() > 0);
    }

    private void successIntHashTableTest(HashTable table) {
        table.add(12);
        table.add(23);
        assertTrue(table.find(12));
        assertTrue(table.find(23));
        table.delete(12);
        assertFalse(table.find(12));
        assertEquals(1, table.size());
    }

    private void successStringHashTableTest(HashTable table) {
        table.add("Parsa");
        table.add("Ali");
        assertTrue(table.find("Parsa"));
        assertTrue(table.find("Ali"));
        table.delete("Parsa");
        assertFalse(table.find("Parsa"));
        assertEquals(1, table.size());
    }
}