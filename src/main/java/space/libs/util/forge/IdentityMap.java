package space.libs.util.forge;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.*;

@SuppressWarnings("unused")
public class IdentityMap<K, V> implements Map<K, V> {

    public TIntObjectHashMap<V> values;

    public TIntObjectHashMap<K> keys;

    public EntrySet entrySet;

    public IdentityMap(int initialCapacity, float loadFactor) {
        keys = new TIntObjectHashMap<>(initialCapacity, loadFactor, -1);
        values = new TIntObjectHashMap<>(initialCapacity, loadFactor, -1);
    }

    @Override public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return values.containsKey(System.identityHashCode(key));
    }

    @Override public boolean containsValue(Object value) {
        return values.containsValue(value);
    }

    @Override public V get(Object key) {
        return values.get(System.identityHashCode(key));
    }

    @Override
    public V put(K key, V value) {
        int id = System.identityHashCode(key);
        keys.put(id, key);
        return values.put(id, value);
    }

    @Override public V remove(Object key) {
        throw new IllegalStateException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        throw new IllegalStateException();
    }

    @Override
    public Set<K> keySet() {
        return new TreeSet<K>(keys.valueCollection());
    }

    @Override
    public Collection<V> values() {
        return values.valueCollection();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (entrySet == null) entrySet = new EntrySet();
        return entrySet;
    }

    public class EntrySet extends AbstractSet<Entry<K,V>> {

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator(keys.iterator());
        }

        @Override
        public int size() {
            return keys.size();
        }
    }

    public class EntryIterator implements Iterator<Entry<K, V>> {

        public TIntObjectIterator<K> itr;

        public int last = -1;

        public EntryIterator(TIntObjectIterator<K> itr) {
            this.itr = itr;
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            itr.advance();
            return new AbstractMap.SimpleEntry<K,V>(itr.value(), values.get(itr.key()));
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }
    }
}
