import java.util.*;

/**
 * Skeletal implementation of a {@link Map}.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author davix
 */
public abstract class BaseAbstractMap<K, V> extends AbstractMap<K, V> {

    /**
     * Returns an {@link Iterator} that visits all the {@link Map.Entry}s in the {@link Map}. The
     * iterator reflects the entries in the map when it was called. This {@link Iterator} does not
     * support element removal.
     * <p>
     * If the map is modified after the {@link Iterator} is generated, the behavior of further
     * iteration on the iterator is undefined.
     *
     * @return an iterator over all the map entries
     */
    protected abstract Iterator<Map.Entry<K, V>> entryIterator();

    @Override
    public abstract int size();

    @Override
    public abstract boolean containsValue(Object value);

    @Override
    public abstract boolean containsKey(Object key);

    @Override
    public abstract V get(Object key);

    @Override
    public abstract V put(K key, V value);

    @Override
    public abstract V remove(Object key);

    @Override
    public abstract void clear();

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return entryIterator();
        }

        @Override
        public int size() {
            return BaseAbstractMap.this.size();
        }
    }
}
