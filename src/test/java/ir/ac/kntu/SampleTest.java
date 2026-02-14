package ir.ac.kntu;

import ir.ac.kntu.hashtable.util.Counter;
import ir.ac.kntu.hashtable.HashTable;
import ir.ac.kntu.hashtable.util.collisionhandlers.ChainingHandler;
import ir.ac.kntu.hashtable.util.collisionhandlers.CollisionHandler;
import ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers.DualHash;
import ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers.LinearProbe;
import ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers.QuadricProbe;
import ir.ac.kntu.hashtable.util.hashfunctions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class SampleTest {
    private static final String INTEGERS_FILE_PATH = "integers.txt";
    private static final String STRING_DATASET_PATH = "string_dataset.txt";
    private static final int TABLE_SIZE = 1000;

    private final List<Integer> integers;
    private final List<String> strings;

    public SampleTest() {
        this.integers = readListFromFile(INTEGERS_FILE_PATH).stream().map(Integer::parseInt).toList();
        this.strings = readListFromFile(STRING_DATASET_PATH);
    }

    @Test
    public void testAddIntegers() {
        CollisionHandler handler = new ChainingHandler();
        long divisionCollCount = runHashingTest(new DivisionMethod(TABLE_SIZE), handler, integers, TestParms.COLLISION_COUNT);
        long multCollCount = runHashingTest(new MultiplactionMethod(TABLE_SIZE), handler, integers, TestParms.COLLISION_COUNT);
        long universCollCount = runHashingTest(new UniversalHash(TABLE_SIZE), handler, integers, TestParms.COLLISION_COUNT);
        System.out.println("Integers Table data:");
        System.out.println("Division Method collisions: " + divisionCollCount);
        System.out.println("Multiplication Method collisions: " + multCollCount);
        System.out.println("Universal Method collisions: " + universCollCount);
    }

    @Test
    public void testAddStrings() {
        CollisionHandler handler = new ChainingHandler();
        long asciiCollCount = runHashingTest(new ASCIIHash(TABLE_SIZE), handler, strings, TestParms.COLLISION_COUNT);
        long polynomCollCount = runHashingTest(new PolynomialRollingHash(), handler, strings, TestParms.COLLISION_COUNT);
        System.out.println("Strings Table data:");
        System.out.println("ASCII Hashing collisions: " + asciiCollCount);
        System.out.println("Polynomial Rolling Hash collisions: " + polynomCollCount);
    }

    @Test
    public void testOpenAddressers() {
        HashFunction hashFunc = new ASCIIHash(TABLE_SIZE);
        long datasetSize = strings.size();
        long linearProbeCount = runHashingTest(hashFunc,  new LinearProbe(), strings, TestParms.PROBE_COUNT) / datasetSize;
        long quadricProbeCount = runHashingTest(hashFunc, new QuadricProbe(), strings, TestParms.PROBE_COUNT) / datasetSize;
        long dualHashProbeCount = runHashingTest(hashFunc,  new DualHash(), strings, TestParms.PROBE_COUNT) / datasetSize;
        System.out.println("Open Addressing probes info:");
        System.out.println("Linear Probe probes: " + linearProbeCount);
        System.out.println("Quadric Probe probes: " + quadricProbeCount);
        System.out.println("Dual Hash probes: " + dualHashProbeCount);
    }

    private long runHashingTest(HashFunction hashFunc, CollisionHandler handler, List<?> dataset, TestParms parm) {
        Counter counter = new Counter();
        HashTable hashTable = new HashTable(hashFunc, TABLE_SIZE, handler, counter);
        for(Object data : dataset) {
            hashTable.add(data);
        }
        return parm == TestParms.COLLISION_COUNT ? counter.getCollisionCount() : counter.getProbeCount();
    }

    private List<String> readListFromFile(String filePath) {
        try(Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.map(String::trim).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.fail();
        return null;
    }

    private enum TestParms {
        COLLISION_COUNT,
        PROBE_COUNT,
    }
}