# Pluggable HashTable

A flexible, educational hash table implementation that allows you to compare different hash functions and collision resolution strategies.

## Overview

Hash tables are fundamental data structures in computer science that use hash functions to map keys to specific indices, providing efficient O(1) time complexity for insert, delete, and search operations.

This project offers a **pluggable architecture** by means of strategy design pattern where you can mix and match hash functions and collision handlers to compare their performance and understand their behavior. It's designed specifically for learning and experimentation.

## Architecture

The implementation is built around three core components:

### HashTable Class
The main data structure that stores keys and orchestrates hashing and collision resolution.

**Constructor:**
```java
HashTable(HashFunction hashFunc, int tableSize, CollisionHandler handler, Counter counter)
```

### HashFunction Interface
A functional interface that defines how keys are hashed:
```java
long hash(Object obj)
```

**Available implementations:**

*For integer keys:*
- `DivisionMethod` - Uses modulo division
- `MultiplicationMethod` - Uses multiplicative hashing
- `UniversalHash` - Random hash function from universal family

*For string keys:*
- `ASCIIHash` - Sums ASCII values
- `PolynomialRollingHash` - Polynomial rolling hash for better distribution

### CollisionHandler Interface
Manages collisions when multiple keys hash to the same index.

**Available implementations:**

*Chaining:*
- `ChainingHandler` - Uses linked lists for each bucket

*Open Addressing:*
- `LinearProbe` - Linear probing sequence
- `QuadraticProbe` - Quadratic probing sequence
- `DualHash` - Double hashing with secondary hash function

## Getting Started

### Running Sample Tests

The project includes pre-configured tests with sample datasets in [SampleTest.java](src/test/java/ir/ac/kntu/SampleTest.java) file. Run it to see the results.

This uses the included datasets:
- `students.txt` - Integer dataset
- `string_dataset.txt` - String dataset

### Creating Custom Tests

Build your own test configurations with JUnit:

```java
// Example:
long TABLE_SIZE = 1000;
Counter counter = new Counter();

// Configuration:
HashTable testTable = new HashTable(
    new UniversalHash(TABLE_SIZE), 
    TABLE_SIZE, 
    new QuadraticProbe(), 
    counter
);

testTable.add(100)
long collisionCount = counter.getCollisionCount();
long probeCount = counter.getProbeCount();
```