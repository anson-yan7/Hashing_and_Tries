/**
 * A skeletal implementation of the {@link java.util.Map} interface.
 *
 * @author davix
 */
public abstract class AbstractTrieMap<V> extends BaseAbstractMap<CharSequence, V> {

    /**
     * @see #get(CharSequence)
     */
    @Override
    public final V get(Object key) {
        return get((CharSequence) key);
    }

    /**
     * @see #containsKey(CharSequence)
     */
    @Override
    public final boolean containsKey(Object key) {
        return containsKey((CharSequence) key);
    }

    /**
     * @see #remove(CharSequence)
     */
    @Override
    public final V remove(Object key) {
        return remove((CharSequence) key);
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this map contains
     * no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null} if this map contains
     * no mapping for the key
     * @throws IllegalArgumentException if the specified key is null
     */
    public abstract V get(CharSequence key);

    /**
     * @throws IllegalArgumentException if either the specified key or value is null
     */
    @Override
    public abstract V put(CharSequence key, V value);

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified key
     * @throws IllegalArgumentException if the specified key is null
     */
    public abstract boolean containsKey(CharSequence key);

    /**
     * Returns {@code true} if this map maps one or more keys to the specified value.
     *
     * @throws IllegalArgumentException if the specified key is null
     */
    @Override
    public abstract boolean containsValue(Object value);

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with the key, or {@code null} if there was no mapping
     * for the key.
     * @throws IllegalArgumentException if the specified key is null
     */
    public abstract V remove(CharSequence key);
}
