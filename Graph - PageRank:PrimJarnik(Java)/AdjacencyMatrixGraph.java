import graph;

import static support.graph.Constants.MAX_VERTICES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.GraphEdge;
import support.graph.GraphVertex;
import support.graph.DirectionException;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;
import support.graph.NodeSequence;

/**
 * This class defines a Graph that tracks its edges through the use of an
 * adjacency matrix. Please review the lecture slides and the book before
 * attempting to write this program. An adjacency matrix consists of a 2D array
 * of Vertices, with each vertex of the graph appearing in both dimensions.
 *
 * Since we are using an adjacency matrix, each vertex must have a 'number', so
 * that it can represent an index in one of the dimensional arrays. This
 * assignment is not as trivial as it may appear. Remember that your arrays have
 * a maximum index. Thus, you cannot just up the number for each vertex. Why
 * not? Think about what happens when you constantly add and delete new
 * vertices. You will soon exceed the size of your adjacency matrix array. Note
 * further that this number must be unique.
 * 
 * Make sure your AdjacencyMatrixGraph can be both directed and undirected!
 *
 * Good luck, and as always, start early, start today, start yesterday!
 */

public class AdjacencyMatrixGraph<V> implements Graph<V> {

	// The underlying data structure of your graph: the adjacency matrix
	private CS16Edge<V>[][] _adjMatrix;
	// Sets to store the vertices and edges of your graph
	private Set<CS16Vertex<V>> _vertices;
	private Set<CS16Edge<V>> _edges;
	/*
	 * data structure to keep track of the indices from which vertices were removed
	 * so that the next vertex can be added there
	 */
	private Stack<Integer> _unused;
	// number of vertices
	private int _numVertices;
	// boolean that keeps track of directedness of graph
	private boolean _directed;

	// constructor for my graph that takes in a boolean to dictate if it is directed
	// and runs in O(1) time

	public AdjacencyMatrixGraph(boolean directed) {
		// initializing all instance variables
		_adjMatrix = this.makeEmptyEdgeArray();
		_directed = directed;
		_vertices = new HashSet<CS16Vertex<V>>();
		_edges = new HashSet<CS16Edge<V>>();
		_unused = new Stack<Integer>();
		_numVertices = 0;
	}

	// Returns an iterator holding all the Vertices of the graph in O(1)
	@Override
	public Iterator<CS16Vertex<V>> vertices() {
		return _vertices.iterator();

	}

	// Returns an iterator holding all the Vertices of the graph in O(1)
	@Override
	public Iterator<CS16Edge<V>> edges() {
		return _edges.iterator();
	}

	// Inserts a new Vertex into your Graph in O(1)

	@Override
	public CS16Vertex<V> insertVertex(V vertElement) {
		CS16Vertex<V> vertex = new GraphVertex<V>(vertElement);
		// checking if the number of vertices less than the max
		if (_numVertices < MAX_VERTICES) {
			/*
			 * checking to see if there are empty spaces before end of vertices array due to
			 * vertex removal. If not, set vertex number to next open space of array,
			 * indicated by _numVertices. If there is an empty space then pop an empty space
			 * from the stack and set the vertex number accordingly
			 */
			if (_unused.isEmpty()) {
				vertex.setVertexNumber(_numVertices);
			} else {
				vertex.setVertexNumber(_unused.pop());
			}
			// updates number of vertices and adds to array
			_numVertices += 1;
			_vertices.add(vertex);
		}

		return vertex;
	}

	// inserts a new edge to your graph in O(1)

	@Override
	public CS16Edge<V> insertEdge(CS16Vertex<V> v1, CS16Vertex<V> v2, Integer edgeElement)
			throws InvalidVertexException {
		// if either vertex null invalidVertexException
		if (v1 == null || v2 == null) {
			throw new InvalidVertexException("a vertex is null");
		}
		/*
		 * Checking that edge does not already exist. If so, adding new edge to the
		 * graph, and setting the appropriate entries in the adjacency matrix equal to
		 * the edge. Just one entry if directed with v1 indicated row, and v2 column,
		 * adding the v1,v2 and v2,v1 if the graph is undirected.
		 */
		if (_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] == null) {
			CS16Edge<V> edge = new GraphEdge<V>(edgeElement, v1, v2);
			_edges.add(edge);
			_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] = edge;
			if (_directed == false) {
				_adjMatrix[v2.getVertexNumber()][v1.getVertexNumber()] = edge;
			}
			return edge;
		} else {
			return null;
		}
	}

	// Removes a vertex and all edges from my graph in O(V) time
	@Override
	public V removeVertex(CS16Vertex<V> vert) throws InvalidVertexException {
		// excpetion if vert null
		if (vert == null) {
			throw new InvalidVertexException("vertex is null");
		}
		/*
		 * adding the index of the removed vertex to unused stack. Iterating over all
		 * incoming edges and setting all entries in adjacency matrix involving the
		 * removed vertex and the opposite vertex of the edge equal to null. Did the
		 * same for the outgoing edges. Removed the edges looped over from the edges
		 * list. Outise of loop removed the vertex from the vertices list.
		 */
		int vertNum = vert.getVertexNumber();
		_unused.add(vertNum);
		_numVertices -= 1;
		Iterator<CS16Edge<V>> iteratorIn = this.incomingEdges(vert);
		Iterator<CS16Edge<V>> iteratorOut = this.outgoingEdges(vert);
		CS16Edge<V> edge = null;
		int opVertNum  = 0;
		while (iteratorIn.hasNext()) {
			edge = iteratorIn.next();
			opVertNum = this.opposite(vert, edge).getVertexNumber();
			_adjMatrix[opVertNum][vertNum] = null;
			_adjMatrix[vertNum][opVertNum] = null;
			_edges.remove(edge);
		}
		/*
		 * have to run this because all incoming edges are outgoing edges for undirected graphs
		 * cannot remove the same edges twice
		 */
		if(_directed == false){
			_vertices.remove(vert);
			return vert.element();
		}
		while (iteratorOut.hasNext()) {
			edge = iteratorOut.next();
			opVertNum = this.opposite(vert, edge).getVertexNumber();
			_adjMatrix[opVertNum][vertNum] = null;
			_adjMatrix[vertNum][opVertNum] = null;
			_edges.remove(edge);
		}
		_vertices.remove(vert);
		return vert.element();
	}

	// Removes an edge in O(1)
	@Override
	public Integer removeEdge(CS16Edge<V> edge) throws InvalidEdgeException {
		// checking if edge valid
		if (edge == null) {
			throw new InvalidEdgeException("vertex is null");
		}
		_edges.remove(edge);
		CS16Vertex<V> v1 = edge.getVertexOne();
		CS16Vertex<V> v2 = edge.getVertexTwo();
		_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] = null;
		if (_directed == false) {
			_adjMatrix[v2.getVertexNumber()][v1.getVertexNumber()] = null;
		}
		return edge.element();

	}

	// Removes an edge in O(1)
	@Override
	public CS16Edge<V> connectingEdge(CS16Vertex<V> v1, CS16Vertex<V> v2)
			throws InvalidVertexException, NoSuchEdgeException {
		// checking if vertices not null, if one is throw exception
		if (v1 == null || v2 == null) {
			throw new InvalidVertexException("A vertex is null");
		}
		int num1 = v1.getVertexNumber();
		int num2 = v2.getVertexNumber();
		/*
		 * If directed and edge doesn't exist throw noSuchEdge. Only one index dictates
		 * directed edge hence the need for if else statement. Return the remove edge
		 */
		if (_directed == true) {
			if (_adjMatrix[num1][num2] == null) {
				throw new NoSuchEdgeException("There is no connecting edge");
			} else {
				return _adjMatrix[num1][num2];
			}
			/*
			 * If undirected and edge doesn't exist throw noSuchEdge. Both indexes dictate
			 * undirected edge hence the need for if else statement. Return the removed
			 * edge.
			 */
		} else {
			if (_adjMatrix[num1][num2] == null & _adjMatrix[num1][num2] == null) {
				throw new NoSuchEdgeException("There is no edge connecting edge");
			} else {
				return _adjMatrix[num1][num2];
			}
		}
	}

	/*
	 * Returns an Iterator over all the Edges that are incoming to this Vertex in
	 * O(|V|). Sets the column of the adjMatrix but loops over all rows in that
	 * column, adds each edge in the column to the incoming set "edgeSet", and makes
	 * an iterator from this set.
	 */
	@Override
	public Iterator<CS16Edge<V>> incomingEdges(CS16Vertex<V> vert) throws InvalidVertexException {
		// returns null if vertex is null
		if (vert == null) {
			throw new InvalidVertexException("vertex is null");
		}
		// iterator for all edges
		Iterator<CS16Vertex<V>> iterator = this.vertices();
		Set<CS16Edge<V>> edgeSet = new HashSet<CS16Edge<V>>();
		int vertNum = vert.getVertexNumber();
		CS16Edge<V> tempEdge = null;
		// looping over all vertices
		while (iterator.hasNext()) {
			// fixing receiving vetex to vertex that was passed in, and setting edge
			// depending on index of first vertex
			tempEdge = _adjMatrix[iterator.next().getVertexNumber()][vertNum];
			// if edge not null, then there is an incoming edge and we add it to the list
			if (tempEdge != null) {
				edgeSet.add(tempEdge);
			}
		}
		// we make an iterator from the edgeSet and return it
		return edgeSet.iterator();
	}

	/*
	 * Returns an Iterator of all the Edges that are outgoing from this vertex in
	 * O(V). Sets the row of the adjMatrix but loops over all columns in that row,
	 * adds each edge in the column to the outGoing set "edgeSet", and makes an
	 * iterator from this set. Mirrors method above just with ougoing edge, so
	 * fixing the columns instead of rows.
	 */
	@Override
	public Iterator<CS16Edge<V>> outgoingEdges(CS16Vertex vert) throws InvalidVertexException {
		// checking for invalid input
		if (vert == null) {
			throw new InvalidVertexException("vertex is null");
		}
		Iterator<CS16Vertex<V>> iterator = this.vertices();
		Set<CS16Edge<V>> edgeSet = new HashSet<CS16Edge<V>>();
		int vertNum = vert.getVertexNumber();
		CS16Edge<V> tempEdge = null;
		while (iterator.hasNext()) {
			tempEdge = _adjMatrix[vertNum][iterator.next().getVertexNumber()];
			if (tempEdge != null) {
				edgeSet.add(tempEdge);
			}
		}
		return edgeSet.iterator();
	}

	/*
	 * Returns an int of the number Edges that are leaving from this Vertex. This
	 * only works if called on a directed graph and this method is used in
	 * MyPageRank. Sets the row of the vertex and loops over all the columns. If
	 * there is an entry in a given column then the outoing pointer is incremented
	 * by 1 because that means there is an outgoing edge.
	 */
	@Override
	public int numOutgoingEdges(CS16Vertex<V> vert) throws InvalidVertexException, DirectionException {
		// exception if graph is undirected
		if (_directed == false) {
			throw new DirectionException("vertex is null");
		}
		// exception is vertex is null
		if (vert == null ) {
			throw new InvalidVertexException("vertex is null");
		}

		Iterator<CS16Vertex<V>> iterator = this.vertices();
		int outgoing = 0;
		int vertNumber = vert.getVertexNumber();
		CS16Edge<V> tempEdge = null;
		// iterates of all vertices in a given row, adding one to outgoing is the entry
		// is not null
		while (iterator.hasNext()) {
			tempEdge = _adjMatrix[vertNumber][iterator.next().getVertexNumber()];
			if (tempEdge != null) {
				outgoing += 1;
			}
		}
		return outgoing;
	}

	// Returns the Vertex that is on the other side of Edge e opposite of Vertex v
	// in O(1)

	@Override
	public CS16Vertex<V> opposite(CS16Vertex<V> vert, CS16Edge<V> edge)
			throws InvalidVertexException, InvalidEdgeException, NoSuchVertexException {
		// if edge or vertex null then exception
		if (edge == null) {
			throw new InvalidEdgeException("edge is not valid");
		}
		if (vert == null) {
			throw new InvalidVertexException("vertex is not valid");
		}
		CS16Vertex<V> v1 = edge.getVertexOne();
		CS16Vertex<V> v2 = edge.getVertexTwo();
		// if the neither of the vertices on either side of the edge equal the vertex
		// passed in then NoSuchVertexException
		if (v1 != vert && v2 != vert) {
			throw new NoSuchVertexException("Edge is not incident on vertex");
		}
		// returns the vertex on the edge that is not the vertex that was passed in
		if (v1 != vert) {
			return v1;
		} else {
			return v2;
		}

	}

	// Returns the two Vertices that the Edge e is connected to in O(1) time.
	@Override
	public List<CS16Vertex<V>> endVertices(CS16Edge<V> e) throws InvalidEdgeException {
		if (e == null) {
			throw new InvalidEdgeException("edge is null");
		}
		// makes a list, adds each vertex on either side of the passed in edge to the
		// list, returns the list
		ArrayList<CS16Vertex<V>> list = new ArrayList<CS16Vertex<V>>();
		list.add(e.getVertexOne());
		list.add(e.getVertexTwo());
		return list;

	}

	/*
	 * Returns true if there exists an Edge that starts from Vertex v1 and ends at
	 * Vertex v2 in O(1)for both a directed and undirected graph, and false
	 * otherwise. For a directed graph two vertices are adjacent if there is an edge
	 * from the first vertex to the second vertex.
	 */
	@Override
	public boolean areAdjacent(CS16Vertex<V> v1, CS16Vertex<V> v2) throws InvalidVertexException {
		// invalidVertexException if either vertex is null
		if (v1 == null || v2 == null) {
			throw new InvalidVertexException("vertex is null");
		}
		/*
		 * if there no entry in the adjMatrix at v1,v2, meaning no edge starts at v1 and
		 * ends at v2, return false. Otherwise returns false.
		 */
		if (_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] != null) {
			return true;
		} else {
			return false;
		}

	}

	// toggles the directedness of a graph
	@Override
	public void toggleDirected() {
		if (_directed == true) {
			_directed = false;
		} else {
			_directed = true;
		}
	}

	// clears vertices from graph. Removes all edges, resets the edge list, vertex
	// list, adjMatrix, numVertices, and unused stack
	@Override
	public void clear() {
		_adjMatrix = this.makeEmptyEdgeArray();
		_vertices = new HashSet<CS16Vertex<V>>();
		_edges = new HashSet<CS16Edge<V>>();
		_unused = new Stack<Integer>();
		_numVertices = 0;
	}

	// returns the number of vertices in the graph
	@Override
	public int getNumVertices() {
		return _numVertices;
	}

	// Do not change this method!
	@SuppressWarnings("unchecked")
	private CS16Edge<V>[][] makeEmptyEdgeArray() {
		return new CS16Edge[MAX_VERTICES][MAX_VERTICES];
	}
}
