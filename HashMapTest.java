import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class HashMapTest {
    private HashMap<String, String> map;

    @Before
    public void setup() {
        map = new HashMap<>(4);
    }

    @Test
    public void testKeys() {
        HashMap.Entry<String, String> test = new HashMap.Entry<>("a", "5", null);
        test.toString();
        test.hashCode();
        map.put("a", "5");
        assertEquals(new HashMap.Entry<>("a", "5", null), test);
        map.put("b", "8");
        map.put("c", "10");
        assertNull(map.remove("f"));
        assertNull(map.remove(null));
        map.put(null, "9");
        assertTrue(map.containsKey(null));
        assertTrue(map.containsKey("a"));
        assertTrue(map.containsKey("b"));
        assertTrue(map.containsKey("c"));
        assertFalse(map.containsKey("g"));
        assertEquals("9", map.get(null));
        assertEquals("5", map.get("a"));
        assertEquals("8", map.get("b"));
        assertEquals("10", map.get("c"));
    }

    @Test
    public void testValues() {
        map.put(null, "99");
        map.put("a", "5");
        map.put("b", "8");
        map.put("c", "10");
        map.put("ac", "7");
        map.put("acc", "2");
        map.put("acccc", "7");
        map.put("acgv", "7");
        assertEquals("2", map.get("acc"));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsValue("5"));
        assertTrue(map.containsValue("8"));
        assertTrue(map.containsValue("10"));
        assertTrue(map.containsValue("7"));
        assertTrue(map.containsValue("2"));
        assertEquals("99", map.get(null));
        map.put("acc", "9");
        map.getTable();
        map.remove(null);
        assertFalse(map.containsValue("99"));
        assertFalse(map.containsKey(null));
        assertTrue(map.containsValue("9"));
        assertFalse(map.containsValue("2"));
        assertEquals("9", map.get("acc"));
        assertEquals(7, map.size());
        assertTrue(map.containsKey("acccc"));
        map.remove("acccc");
        assertFalse(map.containsKey("acccc"));
        map.remove("acc");
        assertFalse(map.containsKey("acc"));
        map.remove("ac");
        assertFalse(map.containsKey("ac"));
        map.clear();
        assertEquals(0, map.size());
    }
    @Test
    public void testNull() {
        map.put(null, "99");
        map.put("a", "5");
        map.put("b", "8");
        map.put("c", "10");
        map.put("ac", "7");
        assertTrue(map.containsKey(null));
        assertTrue(map.containsKey("a"));
        assertTrue(map.containsKey("b"));
        assertTrue(map.containsKey("c"));
        assertTrue(map.containsKey("ac"));
        assertTrue(map.containsValue("99"));
        assertTrue(map.containsValue("5"));
        assertTrue(map.containsValue("8"));
        assertTrue(map.containsValue("10"));
        assertTrue(map.containsValue("7"));
        assertEquals("99", map.get(null));
        assertEquals("5", map.get("a"));
        assertEquals("8", map.get("b"));
        assertEquals("10", map.get("c"));
        assertEquals("7", map.get("ac"));
        map.remove(null);
        assertFalse(map.containsValue("99"));
        assertFalse(map.containsKey(null));
    }
    @Test
    public void testNullOtherKeys() {
        HashMap<String, String> map2 = new HashMap<>();
        map2.put(null, "99");
        map2.put("a", "5");
        map2.put("b", "8");
        map2.put("c", "10");
        map2.put("ac", "7");
        map2.remove("a");
        assertFalse(map2.containsValue("5"));
        assertFalse(map2.containsKey("a"));
    }
    @Test (expected = IllegalArgumentException.class)
    public void testConstructorException() {
        HashMap<String, String> map2 = new HashMap<>(-1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testConstructorException2() {
        HashMap<String, String> map2 = new HashMap<>(2, (float) -.5);
    }

}
