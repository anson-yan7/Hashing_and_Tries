import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation exercise for a {@link BaseAbstractMap} using hashing. The final result will be an
 * implementation with characteristics similar to the OpenJDK implementation for Java 7.
 *
 * @author scheiber
 * @author cquanze
 */
public class HashMap<K, V> extends BaseAbstractMap<K, V> {

    // The default initial capacity - MUST be a power of two.
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // The maximum capacity, used if a higher value is implicitly specified by
    // either of the constructors with arguments. Here, we define capacity to be
    // the number of buckets in the hash table.
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    // The load factor used when not specified in constructor.
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // The load factor for the hash table.
    private final float loadFactor;

    // The table, resized as necessary. Length MUST always be a power of two.
    // The Entry class is really a primitive linked list; each Entry object has
    // a pointer to the next item of the linked list, or null if that Entry
    // corresponds to the "end" of the linked list. This approach uses slightly
    // less memory, and is in fact the technique that the pre-Java 8 JDK uses.
    // The size of the table is the number of buckets.
    private Entry<K, V>[] table;

    // The number of key-value mappings contained in this map. Note that this is
    // different from the table capacity.
    private int size;

    // The next size value at which to resize (capacity * load factor).
    private int threshold;

    /**
     * Constructs an empty HashMap with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is non-positive, or the load factor
     *                                  is non-positive or NaN
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException(
                    "Illegal load factor: " + loadFactor);
        }

        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1;
        }

        this.loadFactor = loadFactor;
        this.threshold = (int) (capacity * loadFactor);
        this.table = new Entry[capacity];
    }

    /**
     * Constructs an empty HashMap with the specified initial capacity and the default load factor
     * (0.75).
     *
     * @param initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is non-positive.
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty HashMap with the default initial capacity (16) and the default load
     * factor (0.75).
     */
    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Applies a supplemental hash function to a given hashCode, which defends against poor quality
     * hash functions. This is critical because HashMap uses power-of-two length hash tables that
     * otherwise encounter collisions for hashCodes that do not differ in lower bits. Note: Null
     * keys always map to hash 0, thus index 0.
     * <p>
     * Additionally, this method truncates the hash to be a valid bucket based on the length
     * parameter, which represents the number of hash table buckets.
     */
    private static int hash(int h, int length) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        h ^= (h >>> 7) ^ (h >>> 4);
        return h & (length - 1);
    }

    /**
     * Returns the underlying table that represents the HashMap
     * Provides the visualizer access to the table
     */
    public Entry<K, V>[] getTable() {
        return table;
    }

    /* NOTE: You shouldn't need to modify anything above this line. */

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(Object key) {
        // 1. Null keys should hash to index 0. Otherwise, hash the key via hash() (see above)
        // 2. Search the corresponding bucket for the specific key.
        // 3. Return the key's value, or null if it does not exist.
        int h;
        if (key != null) {
            h = hash(key.hashCode(), table.length);
        } else {
            h = 0;
        }
        Entry<K, V> curr = table[h];
        if (curr == null) {
            return null;
        }
        if (curr.getKey() == null) {
            if (key == null) {
                return curr.value;
            } else {
                curr = curr.next;
            }
        }
        while (!curr.getKey().equals(key)) {
            curr = curr.next;
            if (curr == null) {
                return null;
            }
            if (curr.getKey() == null) {
                if (key == null) {
                    return curr.value;
                } else {
                    curr = curr.next;
                }
            }
        }
        return curr.getValue();
    }

    @Override
    public boolean containsKey(Object key) {
        int h;
        if (key != null) {
            h = hash(key.hashCode(), table.length);
        } else {
            h = 0;
        }
        Entry<K, V> curr = table[h];
        if (curr == null) {
            return false;
        }
        if (curr.getKey() == null) {
            if (key == null) {
                return true;
            } else {
                curr = curr.next;
            }
        }
        while (!curr.getKey().equals(key)) {
            curr = curr.next;
            if (curr == null) {
                return false;
            }
            if (curr.getKey() == null) {
                if (key == null) {
                    return true;
                } else {
                    curr = curr.next;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        // 1. Hash the key.
        // 2. Walk the appropriate bucket.
        // 3. If you find a matching key, replace its value with value.
        //    Otherwise, make a new entry and insert it at the front of the bucket.
        // 4. Don't forget to resize the array and update the size variable as
        //    necessary. Remember, you resize if this call to put causes the new size
        //    to be greater than or equal to the threshold.

        // NOTE: you should return the previous value associated with key, or null if there
        // was no mapping for key. (A null return can also indicate that the map previously
        // associated null with key).
        int h;
        if (key != null) {
            h = hash(key.hashCode(), table.length);
        } else {
            h = 0;
        }
        Entry<K, V> curr = table[h];
        boolean has = false;
        V output = null;
        while (curr != null) {
            if (curr.getKey() == null) {
                if (key == null) {
                    output = curr.getValue();
                    curr.setValue((V) value);
                    has = true;
                    break;
                } else {
                    curr = curr.next;
                }
            }
            if (curr == null) {
                break;
            }
            if (curr.getKey().equals(key)) {
                output = curr.getValue();
                curr.setValue((V) value);
                has = true;
                break;
            } else {
                curr = curr.next;
            }
        }
        if (!has) {
            size = size + 1;
            if (table[h] != null) {
                table[h] = new Entry(key, value, table[h]);
            } else {
                table[h] = new Entry(key, value, null);
            }
        }
        if (size >= threshold) {
            resize(2 * table.length);
            threshold = (int) (table.length * loadFactor);
        }
        return output;
    }

    /**
     * Rehashes the contents of this map into a new array with a larger capacity. This method
     * should be called automatically when the number of keys in this map reaches its threshold.
     * <p>
     * If current capacity is MAXIMUM_CAPACITY, this method should not resize the map, but instead
     * set threshold to Integer.MAX_VALUE. This has the effect of preventing future calls.
     *
     * @param newCapacity the new capacity, MUST be a power of two; must be greater than current
     *                    capacity unless current capacity is MAXIMUM_CAPACITY (in which case
     *                    value is irrelevant).
     *                    However, there is no need to invoke an exception if an invalid capacity
     *                    is passed in; since
     *                    this is a helper method only used by your implementation internally,
     *                    you can guarantee that
     *                    invalid capacities are not passed in.
     */
    void resize(int newCapacity) {
        // 1. Save the old hash table.
        // 2. Instantiate a new hash table (unless, of course, the current
        //    capacity is MAXIMUM_CAPACITY).
        // 3. Re-hash the old table into it. That is, re-hash all the keys as if you were
        //    reinserting them, in order from index 0 to the end, from the head of the linked
        //    list to its end.
        // 4. Set the new table threshold.

        // NOTE: You do not need to worry about resizing down.
        Iterator<Map.Entry<K, V>> iter = entryIterator();
        Entry<K, V>[] temp = new Entry[newCapacity];
        while (iter.hasNext()) {
            Entry<K, V> curr = (HashMap.Entry<K, V>) iter.next();
            int h;
            if (curr.getKey() != null) {
                h = hash(curr.getKey().hashCode(), newCapacity);
            } else {
                h = 0;
            }
            if (temp[h] != null) {
                temp[h] = new Entry<>(curr.getKey(), curr.getValue(), temp[h]);
            } else {
                temp[h] = new Entry<>(curr.getKey(), curr.getValue(), null);
            }
        }
        table = temp;
    }

    @Override
    public V remove(Object key) {
        int h;
        if (key != null) {
            h = hash(key.hashCode(), table.length);
        } else {
            h = 0;
        }
        Entry<K, V> curr = table[h];
        V output;
        if (curr == null) {
            return null;
        }
        if (curr.getKey() == null) {
            if (key == null) {
                output = curr.getValue();
                table[h] = curr.next;
                size = size - 1;
                return output;
            } else {
                curr = curr.next;
            }
        }
        if (curr.getKey().equals(key)) {
            output = curr.getValue();
            table[h] = curr.next;
            size = size - 1;
            return output;
        }
        while (curr.next != null) {
            if (curr.next.getKey() == null) {
                if (key == null) {
                    output = curr.next.getValue();
                    curr.next = curr.next.next;
                    size = size - 1;
                    return output;
                } else {
                    curr = curr.next;
                }
            }
            if (curr == null) {
                break;
            }
            if (curr.next.getKey().equals(key)) {
                output = curr.next.getValue();
                curr.next = curr.next.next;
                size = size - 1;
                return output;
            }
        }
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator<Map.Entry<K, V>> iter = entryIterator();
        while (iter.hasNext()) {
            if (iter.next().getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        // Clear each bucket of the hash table. In order for clear() to be done in O(1),
        // you can definitely use a table of smaller size, as long as it's a power of two.
        // DEFAULT_INITIAL_CAPACITY is a good size to use.
        this.table = new Entry[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }
    @SuppressWarnings("unchecked")
    @Override
    protected Iterator<Map.Entry<K, V>> entryIterator() {
        // Implement an iterator that walks through every single entry in the
        // hash map. Your iterator should NOT support the remove operation.
        // Your iterator MUST be lazy.
        return new TableIterator();
    }

    private class TableIterator implements Iterator {
        int index = 0;
        Entry<K, V> curr = table[0];
        public TableIterator() {

        }
        @Override
        public boolean hasNext() {
            while (curr == null) {
                if (index < table.length - 1) {
                    index += 1;
                    curr = table[index];
                } else {
                    break;
                }
            }
            return curr != null;
        }

        @Override
        public Entry<K, V> next() {
            if (hasNext()) {
                Entry<K, V> output = curr;
                curr = curr.next;
                return output;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
    /* NOTE: Please do not modify anything below this line. If you'd like to delete the toString
             method for code coverage reasons, go ahead!
     */

    static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        Entry<K, V> next;

        /**
         * Creates new entry.
         */
        Entry(K k, V v, Entry<K, V> n) {
            value = v;
            next = n;
            key = k;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key)
                    && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return String.format("Entry{Key=%s, Value=%s}", key, value);
        }
    }
}
