package graph;

import java.util.HashMap;
import java.util.Set;

import support.graph.CS16Decorator;
import support.graph.CS16Vertex;

/**
 * 
 * Your implementation of decorations. All methods must run in O(1) time.
 * 
 * <p>
 * This program makes heavy use of the decorator pattern. It is absolutely
 * essential that you understand this design pattern before you start coding. In
 * previous programs, like Heap, you were allowed to modify your Position
 * classes when you wanted that class to store an additional piece of
 * information. However, when you want to potentially store a lot of things in
 * your classes, and especially when you do not know in advance what you will be
 * storing, the decorator's usefulness is profound.
 * </p>
 *
 * <p>
 * Think of the decorator pattern like a post-it note. You write what you want
 * on it, then stick it on the class. Later on, when you want to retrieve that
 * information, you just retrieve the note, and read what it has to say. Please
 * refer to the handout for more details on the decorator pattern and some
 * examples on its usage.
 * </p>
 *
 * 
 */
public class MyDecorator<K, V> implements CS16Decorator<K, V> {
	// to store decoratrions
	private HashMap<K, V> _map;

	public MyDecorator() {
		_map = new HashMap<K, V>();
	}

	// gets decoration associated with a given key
	@Override
	public V getDecoration(K key) {
		return _map.get(key);
	}

	// sets decoration given key and value
	@Override
	public void setDecoration(K key, V value) {
		_map.put(key, value);
	}

	// returns true if there is a decoration for the given key
	@Override
	public boolean hasDecoration(K key) {
		return _map.containsKey(key);
	}

	// removes the decoration from a given key
	@Override
	public V removeDecoration(K key) {
		return _map.remove(key);
	}

	// returns a set of all the keys for a given decoration
	@Override
	public Set<K> getKeys() {
		return _map.keySet();
	}
}
