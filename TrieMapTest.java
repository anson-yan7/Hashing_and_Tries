import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrieMapTest {
    TrieMap<Integer> tree;
    @Before
    public void setup() {
        tree = new TrieMap<>();
        TrieMap.Node<Integer> root = tree.getRoot();
        assertNull(root.getChildren());
        tree.put("abb", 3);
        tree.put("abbbc", 4);
        tree.put("abcb", 4);
        tree.put("acbb", 5);
        tree.put("cbbb", 4);
        tree.put("abab", 7);
        tree.put("acbb", 8);
        tree.put("abbc", 9);
    }
    @Test
    public void testContainsValue() {
        assertTrue(tree.containsValue(3));
        assertTrue(tree.containsValue(4));
        assertFalse(tree.containsValue(5));
        assertTrue(tree.containsValue(7));
        assertTrue(tree.containsValue(8));
        assertTrue(tree.containsValue(9));
        assertFalse(tree.containsValue(10));
    }
    @Test
    public void testContainsKey() {
        assertTrue(tree.containsKey("abb"));
        assertTrue(tree.containsKey("abbbc"));
        assertTrue(tree.containsKey("abcb"));
        assertTrue(tree.containsKey("acbb"));
        assertTrue(tree.containsKey("cbbb"));
        assertTrue(tree.containsKey("abcb"));
        assertTrue(tree.containsKey("acbb"));
        assertTrue(tree.containsKey("abbc"));
        assertFalse(tree.containsKey("ab"));
    }

    @Test
    public void testRemove() {
        assertEquals(7, tree.size());
        tree.remove("abb");
        assertFalse(tree.containsKey("abb"));
        assertEquals(6, tree.size());
        tree.remove("abbbc");
        assertFalse(tree.containsKey("abbbc"));
        assertEquals(5, tree.size());
        tree.remove("abcb");
        assertFalse(tree.containsKey("abcb"));
        assertEquals(4, tree.size());
        tree.remove("acbb");
        assertFalse(tree.containsKey("acbb"));
        assertEquals(3, tree.size());
        tree.remove("cbbb");
        assertFalse(tree.containsKey("cbbb"));
        assertEquals(2, tree.size());
        tree.remove("abab");
        assertFalse(tree.containsKey("abab"));
        assertEquals(1, tree.size());
        tree.remove("abbc");
        assertFalse(tree.containsKey("abbc"));
        assertEquals(0, tree.size());
    }
    @Test
    public void testEmptyString() {
        assertFalse(tree.containsKey(""));
        tree.put("", 11);
        assertTrue(tree.containsKey(""));
        assertTrue(tree.containsValue(11));
    }
    @Test
    public void testGet() {
        assertEquals(3, (int) tree.get("abb"));
    }
    @Test (expected = IllegalArgumentException.class)
    public void testGetException() {
        tree.get(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testValueException() {
        tree.containsValue(null);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testPutException() {
        tree.put(null, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testKeyException() {
        tree.containsKey(null);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testConvertIndexException() {
        tree.convertToIndex('A');
    }
    @Test (expected = IllegalArgumentException.class)
    public void testConvertCharException() {
        tree.convertToChar(50);
    }
    @Test
    public void testGetWrong() {
        assertNull(tree.get("abd"));
    }
    @Test
    public void testClear() {
        tree.clear();
        assertEquals(0, tree.size());
    }
    @Test (expected = UnsupportedOperationException.class)
    public void testIteratorException() {
        tree.entryIterator();
    }
    @Test
    public void testEmpty() {
        tree.put("", 5);
        tree.remove("");
        assertFalse(tree.containsKey(""));
    }
}
