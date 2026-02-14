package ir.ac.kntu.hashtable.util.collisionhandlers.openaddressers;

public class QuadricProbe extends OpenAddresser {
    @Override
    public long getProbeIndex(int initialIndex, int step, Object key) {
        return initialIndex + (step * step);
    }
}