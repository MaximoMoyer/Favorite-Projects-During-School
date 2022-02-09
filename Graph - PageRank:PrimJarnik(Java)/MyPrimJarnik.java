package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import net.datastructures.Entry;
import support.graph.CS16AdaptableHeapPriorityQueue;
import support.graph.CS16Edge;
import support.graph.CS16GraphVisualizer;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.MinSpanForest;

public class MyPrimJarnik<V> implements MinSpanForest<V> {

	@Override
	public Collection<CS16Edge<V>> genMinSpanForest(Graph<V> g, CS16GraphVisualizer<V> visualizer) {
		/*
		 * setting a priority queue to remove from, an iterator for all vertices, a cost
		 * decorator to keep track of the minimum edge, a previous decorator to keep
		 * track of the opposite vertex that the minimum cost edge is connected to, a
		 * visited decorotor to keep track if a vertex has been visited, and an entry
		 * decorator to be able to access the entry of a given vertex in the PQ. Also
		 * have vertex, edge, and u variables that I use in my while loop
		 */
		CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>> PQ = new CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>>();
		Iterator<CS16Vertex<V>> iterator = g.vertices();
		MyDecorator<CS16Vertex<V>, Integer> cost = new MyDecorator<CS16Vertex<V>, Integer>();
		MyDecorator<CS16Vertex<V>, CS16Vertex<V>> previous = new MyDecorator<CS16Vertex<V>, CS16Vertex<V>>();
		MyDecorator<CS16Vertex<V>, Boolean> visited = new MyDecorator<CS16Vertex<V>, Boolean>();
		MyDecorator<CS16Vertex<V>, Entry<Integer, CS16Vertex<V>>> entry = new MyDecorator<CS16Vertex<V>, Entry<Integer, CS16Vertex<V>>>();
		CS16Vertex<V> vertex = null;
		CS16Edge<V> edge = null;
		CS16Vertex<V> u = null;

		/*
		 * looping over vertices. Setting visited to false for all vertices. Setting the
		 * last vertex in the iterator to have a cost of 0, and inserting it into the
		 * PQ, this is my "randomization". Otherwise, I am setting the cost decoration
		 * to be infinity, and inserting the vertex into my PQ.
		 */
		while (iterator.hasNext()) {
			vertex = iterator.next();
			visited.setDecoration(vertex, false);
			if (iterator.hasNext() == false) {
				cost.setDecoration(vertex, 0);
				entry.setDecoration(vertex, PQ.insert(cost.getDecoration(vertex), vertex));
			} else {
				cost.setDecoration(vertex, Integer.MAX_VALUE);
				entry.setDecoration(vertex, PQ.insert(cost.getDecoration(vertex), vertex));
			}

		}
		// instantiating the MST that will keep track of the edges in my MST, and an
		// iterator to iterator over all edges of a given vertex
		ArrayList<CS16Edge<V>> MST = new ArrayList<CS16Edge<V>>();
		Iterator<CS16Edge<V>> edgeIterator = null;
		// While the PQ is not empty
		while (!PQ.isEmpty()) {
			// remove min
			vertex = PQ.removeMin().getValue();
			// set visited deocaration to true
			visited.setDecoration(vertex, true);
			// if it has a previous pointed, add the edge to the MST
			if (previous.getDecoration(vertex) != null) {
				MST.add(g.connectingEdge(vertex, previous.getDecoration(vertex)));
			}
			// setting the edge iterator to all incomingEdges of the vertex
			edgeIterator = g.incomingEdges(vertex);
			// looping over all edges
			while (edgeIterator.hasNext()) {
				edge = edgeIterator.next();
				// u is the vertex on the opposite of the given edge of vertex
				u = g.opposite(vertex, edge);
				// if u has not been visited, and removed from the PQ
				if (visited.getDecoration(u) != true) {
					/*
					 * and if the current cost of connecting u to the MST is greater than the
					 * current edge being looped over then set the cost of u equal to the cost of
					 * the edge being looped over. Then, replace the key of the PQ with this new
					 * lesser value.
					 */
					if (cost.getDecoration(u) > edge.element()) {
						cost.setDecoration(u, edge.element());
						previous.setDecoration(u, vertex);
						PQ.replaceKey(entry.getDecoration(u), cost.getDecoration(u));
					}
				}

			}
		}
		// return the MST containing all the edges of the MST.
		return MST;
	}
}
