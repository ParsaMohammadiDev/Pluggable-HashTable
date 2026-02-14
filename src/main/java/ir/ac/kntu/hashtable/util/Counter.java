package ir.ac.kntu.hashtable.util;

public class Counter {
    private long collisionCount = 0;
    private long probeCount = 0;

    public void countProbing() {
        probeCount ++;
    }

    public void countCollision() {
        collisionCount += 1;
    }

    public long getCollisionCount() {
        return collisionCount;
    }

    public long getProbeCount() {
        return probeCount;
    }

    public void reset() {
        collisionCount = 0;
        probeCount = 0;
    }
}