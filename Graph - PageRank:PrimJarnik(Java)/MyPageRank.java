package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.GraphVertex;
import support.graph.PageRank;

public class MyPageRank<V> implements PageRank<V> {
	// declaring my instance variables, vertices g and vertsToRanks declared for me
	private Graph<V> _g;
	private ArrayList<CS16Vertex<V>> _vertices;
	private Map<CS16Vertex<V>, Double> _vertsToRanks;
	private static final double _dampingFactor = 0.85;
	private static final int _maxIterations = 100;
	private static final double _error = 0.01;
	private int _numVert;
	private ArrayList<Integer> _sink;
	private ArrayList<Double> _prevRank;
	private ArrayList<Double> _currRank;

	@Override
	public Map<CS16Vertex<V>, Double> calcPageRank(Graph<V> g) {
		// initialize private instance variables
		_g = g;
		_numVert = _g.getNumVertices();
		ArrayList<Integer> outgoing = new ArrayList<Integer>();
		_vertices = new ArrayList<CS16Vertex<V>>();
		// stores previous page ranks
		_prevRank = new ArrayList<Double>();
		// stores current page rank
		_currRank = new ArrayList<Double>();
		// list of all sinks
		_sink = new ArrayList<Integer>();
		// map to be returned
		_vertsToRanks = new HashMap<CS16Vertex<V>, Double>();
		// decorator so that I can keep track of the vertices
		MyDecorator<CS16Vertex<V>, Integer> index = new MyDecorator<CS16Vertex<V>, Integer>();
		// iterator to iterate over all the vertices in g
		Iterator<CS16Vertex<V>> verts = _g.vertices();

		int i = 0;
		// pageRank of all vertices to begin with
		double start = 1 / (double) _numVert;
		/*
		 * adding to the vertices list, setting the starting pageRanks, setting the
		 * index number of the vertices used across all lists, getting the number
		 * outgoing edges and determining which vertices are sinks and adding them to
		 * the sinks list.
		 */
		while (verts.hasNext()) {
			_vertices.add(verts.next());
			index.setDecoration(_vertices.get(i), i);
			_prevRank.add(0.00);
			_currRank.add(start);
			outgoing.add(_g.numOutgoingEdges(_vertices.get(i)));
			if (outgoing.get(i) == 0) {
				_sink.add(i);
			}
			i += 1;
		}

		// local variables to figure out when to stop the loop
		int iterations = 0;
		double maxError = 100;
		double errorCalc = 0.00;
		// creating local variable for the iterator that will be used to iterate over
		// all incoming edges
		Iterator<CS16Edge<V>> iterator = null;
		// integer to store the index for a given vertex
		int bucket = 0;
		/*
		 * running loop until there have been 100 iterations, or the page rank changes
		 * by less than _error from one round to the next for all vertices
		 */
		while (iterations < _maxIterations && maxError > _error) {
			// resetting maxError
			maxError = 0;
			// setting the previous rank equal to the current rank for each vertex
			for (int j = 0; j < _numVert; j++) {
				_prevRank.set(j, _currRank.get(j));
			}
			// looping over each vertex
			for (int k = 0; k < _numVert; k++) {
				// setting current rank to 0
				_currRank.set(k, 0.00);
				// setting the iterator to iterate over all incoming edges of vertex k
				iterator = _g.incomingEdges(_vertices.get(k));
				// looping over all incoming edges for vertex k
				while (iterator.hasNext()) {
					// getting the vertex opposite the vertex being looped over given the edge being
					// looped over
					bucket = index.getDecoration(_g.opposite(_vertices.get(k), iterator.next()));
					/*
					 * setting the current rank of a vertex equal to its current rank + damping
					 * factor times (the previous rank of the vertex opposite the incoming edge/ the
					 * number of outgoing edges of that vertex)
					 */
					_currRank.set(k,
							_currRank.get(k) + (_dampingFactor * _prevRank.get(bucket) / outgoing.get(bucket)));
				}
				/*
				 * calculating how much page rank changed for a given vertex, setting it to
				 * maxError if its the biggest of all the vertices Had to make if else
				 * statements to deal with the absolute value error of a double
				 */
				errorCalc = _prevRank.get(k) - _currRank.get(k);
				if (errorCalc >= 0) {
					if (errorCalc > maxError) {
						maxError = errorCalc;

					} else {
						if (-errorCalc > maxError) {
							maxError = -errorCalc;
						}
					}
				}
			}
			// handling the pageRank of sinks once
			// adding one to the iterations in order to stop the while loop when we hit 100
			// iterations
			this.handleSinks();
			iterations += 1;
			// handling the page rank of blackListed pages
			this.removeBlackList();
		}
		// adding the vertices and their final page rank to the Map to be returned
		for (i = 0; i < _numVert; i++) {
			_vertsToRanks.put(_vertices.get(i), _currRank.get(i));
		}
		return _vertsToRanks;
	}

	// method used to handle sinks
	private void handleSinks() {
		// initializing variable that will be added to each vertex
		double sinkSum = 0;
		/*
		 * adding the previous rank of each sink over the total number of vertices times
		 * the dampingFactor
		 */
		for (int i = 0; i < _sink.size(); i++) {
			sinkSum = sinkSum + _dampingFactor * (_prevRank.get(_sink.get(i)) / _numVert);
		}
		// adding 1- dampingFactor over the number of vertices to the sinkSum
		sinkSum = sinkSum + (1 - _dampingFactor) / _numVert;
		// adding the sink sum to the page rank of each vertex
		for (int k = 0; k < _numVert; k++) {
			_currRank.set(k, _currRank.get(k) + sinkSum);
		}

	}

	// Feel free to add helper methods below.
	private void removeBlackList() {
		// setting the distribute variable to be added to all good Vertexes equal to 0
		double distribute = 0;
		// set with list of names of all the blacklisted sites
		Set<String> blackList = PageRank.blacklist;
		// list to contain the indexes that point to the non black listed, or good,
		// vertices
		ArrayList<Integer> goodVertIndex = new ArrayList<Integer>();
		// looping over all the indexes for each vertex
		for (int i = 0; i < _numVert; i++) {
			// if the vertex is on the black list, add its current page rank to distribute,
			// and set its current page rank to 0
			if (blackList.contains(_vertices.get(i).getVertexName())) {
				distribute = distribute + _currRank.get(i);
				_currRank.set(i, 0.00);
			}
			// if vertex not on black list add its index to goodVertex
			else {
				goodVertIndex.add(i);
			}
		}
		// divide distribute by the number of good vertices
		distribute = distribute / goodVertIndex.size();
		// setting the current rank of all good vertices equal to their previous current
		// rank + distribute
		for (int i = 0; i < goodVertIndex.size(); i++) {
			_currRank.set(goodVertIndex.get(i), _currRank.get(goodVertIndex.get(i)) + distribute);
		}

	}

}
