package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static support.graph.Constants.MAX_VERTICES;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import net.datastructures.InvalidEntryException;
import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.DirectionException;
import support.graph.Graph;
import support.graph.GraphEdge;
import support.graph.GraphVertex;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;

/**
 * This class tests the functionality of a Graph based on a 'String' type
 * parameter for the vertices. Edge elements are Integers. The general framework
 * of a test case is: - Name the test method descriptively, mentioning what is
 * being tested (it is ok to have slightly verbose method names here) - Set-up
 * the program state (ex: instantiate a heap and insert K,V pairs into it) - Use
 * assertions to validate that the program is in the state you expect it to be
 * See header comments over tests for what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset
 * to a new instance of the a Graph<String> that you can simply use 'as is' via
 * the methods under the 'DO NOT MODIFY ANYTHING BELOW THIS LINE' line. Of
 * course, please do not modify anything below that, or the above assumptions
 * may be broken.
 */
@RunWith(Parameterized.class)
public class GraphTest {

	// Undirected Graph instance variable
	private Graph<String> _graph;
	// Directed Graph instance variable
	private Graph<String> _dirGraph;
	private String _graphClassName;

	/**
	 * A simple test for insertVertex, that adds 3 vertices and then checks to make
	 * sure they were added by accessing them through the vertices iterator.
	 */
	@Test(timeout = 10000)
	public void testInsertVertex() {
		// insert vertices
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");

		// use the vertex iterator to get a list of the vertices in the actual
		// graph
		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}

		// assert that the graph state is consistent with what you expect
		assertThat(actualVertices.size(), is(3));
		assertThat(actualVertices.contains(A), is(true));
		assertThat(actualVertices.contains(B), is(true));
		assertThat(actualVertices.contains(C), is(true));
	}

	// Same test as above, but with a directed graph
	@Test(timeout = 10000)
	public void testInsertVertexDirected() {
		// insert vertices
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");

		// use the vertex iterator to get a list of the vertices in the actual
		// graph
		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}

		// assert that the graph state is consistent with what you expect
		assertThat(actualVertices.size(), is(3));
		assertThat(actualVertices.contains(A), is(true));
		assertThat(actualVertices.contains(B), is(true));
		assertThat(actualVertices.contains(C), is(true));
	}

	/**
	 * A simple test for insertEdges that adds 3 vertices, adds two edges to the
	 * graph and then asserts that both edges were in fact added using the edge
	 * iterator as well as checks to make sure their from and to vertices were set
	 * correctly.
	 */
	@Test(timeout = 10000)
	public void testInsertEdges() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");

		// use the edge iterator to get a list of the edges in the actual graph.
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _graph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges.size(), is(2));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(bc), is(true));
	}

	// Same test as above, but with a directed graph
	@Test(timeout = 10000)
	public void testInsertEdgesDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");

		// use the edge iterator to get a list of the edges in the actual graph.
		CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _dirGraph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges.size(), is(2));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(bc), is(true));
	}

	/*
	 * List of graphs for testing!
	 */
	@Parameters(name = "with graph: {0}")
	public static Collection<String> graphs() {
		List<String> names = new ArrayList<>();
		names.add("graph.AdjacencyMatrixGraph");
		return names;
	}

	/**
	 * A simple test for insertVertex, that adds 3 vertices and then checks to make
	 * sure they were added by accessing them through the vertices iterator.
	 */
	@Test(timeout = 10000)
	public void myTestInsertVertex() {
		// insert vertices
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");

		// use the vertex iterator to get a list of the vertices in the actual
		// graph
		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}

		// assert that the graph state is consistent with what you expect
		assertThat(actualVertices.size(), is(3));
		assertThat(actualVertices.contains(A), is(true));
		assertThat(actualVertices.contains(B), is(true));
		assertThat(actualVertices.contains(C), is(true));
	}

	// Same test as above, but with a directed graph
	@Test(timeout = 10000)
	public void myTestInsertVertexDirected() {
		// insert vertices
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");

		// use the vertex iterator to get a list of the vertices in the actual
		// graph
		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}

		// assert that the graph state is consistent with what you expect
		assertThat(actualVertices.size(), is(3));
		assertThat(actualVertices.contains(A), is(true));
		assertThat(actualVertices.contains(B), is(true));
		assertThat(actualVertices.contains(C), is(true));
	}

	/**
	 * A simple test for insertEdges that adds 3 vertices, adds two edges to the
	 * graph and then asserts that both edges were in fact added using the edge
	 * iterator as well as checks to make sure their from and to vertices were set
	 * correctly.
	 */
	@Test(timeout = 10000)
	public void myTestInsertEdges() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");

		// use the edge iterator to get a list of the edges in the actual graph.
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
		CS16Edge<String> cb = _graph.insertEdge(C, B, 2);
		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _graph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges.size(), is(2));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(cb), is(false));
		assertThat(actualEdges.contains(bc), is(true));
	}

	// Testing same thing as above, but with a directed graph
	@Test(timeout = 10000)
	public void myTestInsertEdgesDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");

		// use the edge iterator to get a list of the edges in the actual graph.
		CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
		CS16Edge<String> ba = _dirGraph.insertEdge(B, A, 1);
		CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _dirGraph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges.size(), is(3));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(ba), is(true));
		assertThat(actualEdges.contains(bc), is(true));
	}

	// Doesn't insert more that Max_Vertices on both undirected and directed graphs
	@Test(timeout = 10000)
	public void myMaxVertexTest() {
		for (int i = 0; i < MAX_VERTICES + 5; i++) {
			_dirGraph.insertVertex("A");
			_graph.insertVertex("B");
		}
		List<CS16Vertex<String>> actualVertices1 = new ArrayList<CS16Vertex<String>>();
		List<CS16Vertex<String>> actualVertices2 = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it1 = _dirGraph.vertices();
		Iterator<CS16Vertex<String>> it2 = _graph.vertices();
		while (it1.hasNext()) {
			actualVertices1.add(it1.next());
		}
		while (it2.hasNext()) {
			actualVertices2.add(it2.next());
		}

		// assert that the graph state is consistent with what you expect
		assertThat(actualVertices1.size(), is(MAX_VERTICES));
		assertThat(actualVertices2.size(), is(MAX_VERTICES));
	}

	// testing InvalidVertexExceptions for insertEdge with first vert null on
	// undirected
	@Test(expected = InvalidVertexException.class)
	public void myInvalidInsertEdgeTestFirstUndirected() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.insertEdge(null, A, 2);
	}

	// testing InvalidVertexExceptions for insertEdge with second vertex null on
	// undirected
	@Test(expected = InvalidVertexException.class)
	public void myInvalidInsertEdgeTestSecondUnDirected() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.insertEdge(A, null, 2);
	}

	// testing InvalidVertexExceptions for insertEdge with both vertexes null on
	// undirected
	@Test(expected = InvalidVertexException.class)
	public void myInvalidInsertEdgeTestBothDirected() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.insertEdge(null, null, 1);
	}

	// testing InvalidVertexExceptions for insertEdge with first vert null on
	// directed
	@Test(expected = InvalidVertexException.class)
	public void myInvalidInsertEdgeTestFirstDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		_dirGraph.insertEdge(null, A, 2);
	}

	// testing InvalidVertexExceptions for insertEdge with second vertex null on
	// directed
	@Test(expected = InvalidVertexException.class)
	public void myInvalidInsertEdgeTestSecondDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		_dirGraph.insertEdge(A, null, 2);
	}

	// testing InvalidVertexExceptions for insertEdge with both vertexes null on
	// undirected
	@Test(expected = InvalidVertexException.class)
	public void myInvalidInsertEdgeTestBothUnDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		_dirGraph.insertEdge(null, null, 1);
	}

	// testing remove vertex for undirected and directed graph
	@Test(timeout = 10000)
	public void myRemoveVertexTest() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Vertex<String> D = _dirGraph.insertVertex("D");
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> ba = _dirGraph.insertEdge(B, A, 1);
		CS16Edge<String> ca = _dirGraph.insertEdge(A, C, 2);
		CS16Edge<String> ed = _dirGraph.insertEdge(E, D, 2);
		CS16Edge<String> de = _dirGraph.insertEdge(D, E, 2);
		CS16Edge<String> da = _dirGraph.insertEdge(D, A, 2);
		assertThat(_dirGraph.getNumVertices(), is(5));
		assertThat(_dirGraph.removeVertex(A), is(A.element()));
		assertThat(_dirGraph.getNumVertices(), is(4));
		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _dirGraph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.contains(bc), is(true));
		assertThat(actualEdges.contains(ed), is(true));
		assertThat(actualEdges.contains(de), is(true));
		assertThat(actualEdges.contains(ba), is(false));
		assertThat(actualEdges.contains(ab), is(false));

		_dirGraph.removeVertex(C);
		it = _dirGraph.edges();
		actualEdges = new ArrayList<CS16Edge<String>>();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.contains(bc), is(false));
		assertThat(actualEdges.contains(ca), is(false));
		assertThat(actualEdges.contains(ab), is(false));
		assertThat(actualEdges.contains(ed), is(true));
		assertThat(actualEdges.contains(de), is(true));
		assertThat(_dirGraph.getNumVertices(), is(3));
		it = _dirGraph.edges();
		actualEdges = new ArrayList<CS16Edge<String>>();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.size(), is(2));
		_dirGraph.removeVertex(E);
		it = _dirGraph.edges();
		actualEdges = new ArrayList<CS16Edge<String>>();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.size(), is(0));
		_dirGraph.insertVertex("A");
		actualEdges = new ArrayList<CS16Edge<String>>();
		it = _dirGraph.edges();
		actualEdges = new ArrayList<CS16Edge<String>>();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}
		assertThat(actualEdges.contains(ab), is(false));
		assertThat(actualEdges.contains(ca), is(false));
		assertThat(_dirGraph.getNumVertices(), is(3));
		assertThat(_dirGraph.removeVertex(D), is(D.element()));
		assertThat(_dirGraph.getNumVertices(), is(2));
		_dirGraph.removeVertex(B);
		assertThat(_dirGraph.getNumVertices(), is(1));
		assertThat(_dirGraph.removeVertex(A), is(A.element()));
		assertThat(_dirGraph.getNumVertices(), is(0));
		CS16Vertex<String> F = _graph.insertVertex("F");
		CS16Vertex<String> G = _graph.insertVertex("G");
		CS16Vertex<String> H = _graph.insertVertex("H");
		CS16Vertex<String> I = _graph.insertVertex("I");
		CS16Vertex<String> J = _graph.insertVertex("J");
		assertThat(_graph.getNumVertices(), is(5));
		assertThat(_graph.removeVertex(F), is(F.element()));
		assertThat(_graph.getNumVertices(), is(4));
		assertThat(_graph.removeVertex(H), is(H.element()));
		assertThat(_graph.getNumVertices(), is(3));
		_graph.removeVertex(I);
		_graph.insertVertex("F");
		assertThat(_graph.getNumVertices(), is(3));
		_graph.removeVertex(J);
		assertThat(_graph.getNumVertices(), is(2));
		assertThat(_graph.removeVertex(G), is(G.element()));
		assertThat(_graph.getNumVertices(), is(1));
		assertThat(_graph.removeVertex(F), is(F.element()));
		assertThat(_graph.getNumVertices(), is(0));
	}

	// testing InvalidVertexExceptions for removeVert on directed
	@Test(expected = InvalidVertexException.class)
	public void myInvalidRemoveVertexDir() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		_dirGraph.removeVertex(null);
	}

	// testing InvalidEdgeException for removeEdge on directed
	@Test(expected = InvalidEdgeException.class)
	public void myInvalidRemoveEdgeDir() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		_dirGraph.removeEdge(null);
	}

	// testing InvalidVertexExceptions for removeVert on undirected
	@Test(expected = InvalidVertexException.class)
	public void myInvalidRemoveVertexUndir() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.removeVertex(null);
	}

	// testing InvalidEdgeException for removeEdge on undirected
	@Test(expected = InvalidEdgeException.class)
	public void myInvalidRemoveEdgeUnDir() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		_graph.removeEdge(null);
	}

	// testing removeEdge on undirected and directed
	@Test(timeout = 10000)
	public void myRemoveEdgeTest() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> ba = _dirGraph.insertEdge(B, A, 1);
		CS16Edge<String> ca = _dirGraph.insertEdge(A, C, 2);

		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _dirGraph.edges();
		while (it.hasNext()) {
			actualEdges.add(it.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges.size(), is(4));
		assertThat(actualEdges.contains(ab), is(true));
		assertThat(actualEdges.contains(bc), is(true));
		assertThat(actualEdges.contains(ca), is(true));
		assertThat(actualEdges.contains(ba), is(true));

		// removing an edge then checking that the right edges were removed
		assertThat(_dirGraph.removeEdge(ab), is(ab.element()));
		assertThat(_dirGraph.removeEdge(ca), is(ca.element()));

		List<CS16Edge<String>> actualEdges2 = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it2 = _dirGraph.edges();
		while (it2.hasNext()) {
			actualEdges2.add(it2.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges2.size(), is(2));
		assertThat(actualEdges2.contains(ab), is(false));
		assertThat(actualEdges2.contains(bc), is(true));
		assertThat(actualEdges2.contains(ca), is(false));
		assertThat(actualEdges.contains(ba), is(true));

		CS16Vertex<String> D = _graph.insertVertex("D");
		CS16Vertex<String> E = _graph.insertVertex("E");
		CS16Vertex<String> F = _graph.insertVertex("F");
		CS16Edge<String> DE = _graph.insertEdge(D, E, 1);
		CS16Edge<String> EF = _graph.insertEdge(E, F, 2);
		CS16Edge<String> FD = _graph.insertEdge(F, D, 2);

		// use the edge iterator to get a list of the edges in the actual graph.
		List<CS16Edge<String>> actualEdges3 = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it3 = _graph.edges();
		while (it3.hasNext()) {
			actualEdges3.add(it3.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges3.size(), is(3));
		assertThat(actualEdges3.contains(DE), is(true));
		assertThat(actualEdges3.contains(EF), is(true));
		assertThat(actualEdges3.contains(FD), is(true));

		// removing an edge then checking that the right edges were removed
		assertThat(_graph.removeEdge(DE), is(DE.element()));
		assertThat(_graph.removeEdge(EF), is(EF.element()));

		List<CS16Edge<String>> actualEdges4 = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it4 = _graph.edges();
		while (it4.hasNext()) {
			actualEdges4.add(it4.next());
		}

		// assert that the graph state is consistent with what you expect.
		assertThat(actualEdges4.size(), is(1));
		assertThat(actualEdges4.contains(DE), is(false));
		assertThat(actualEdges4.contains(FD), is(true));
		assertThat(actualEdges4.contains(EF), is(false));

	}

	// testing InvalidVertexException for Connecting Edge undirected with first
	// vertex null
	@Test(expected = InvalidVertexException.class)
	public void myInvalidVertexConnectingEdgeUndirFirst() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		_dirGraph.insertEdge(A, B, 1);
		_dirGraph.connectingEdge(null, B);
		assertThat(6, is(7));
	}

	// testing InvalidVertexException for Connecting Edge undirected with second
	// vertex null
	@Test(expected = InvalidVertexException.class)
	public void myInvalidVertexConnectingEdgeUndirSecond() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		_dirGraph.insertEdge(A, B, 1);
		_dirGraph.connectingEdge(A, null);

	}

	// testing InvalidVertexException for Connecting Edge undirected with both
	// vertexes null
	@Test(expected = InvalidVertexException.class)
	public void myInvalidVertexConnectingEdgeUndirBoth() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		_dirGraph.insertEdge(A, B, 1);
		_dirGraph.connectingEdge(null, null);
	}

	// testing InvalidVertexException for Connecting Edge directed with first vertex
	// null
	@Test(expected = InvalidVertexException.class)
	public void myInvalidVertexConnectingEdgeDirFirst() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		_graph.insertEdge(A, B, 1);
		_dirGraph.connectingEdge(null, B);
		assertThat(6, is(7));
	}

	// testing InvalidVertexException for Connecting Edge directed with second
	// vertex null
	@Test(expected = InvalidVertexException.class)
	public void myInvalidVertexConnectingEdgeDirSecond() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		_graph.insertEdge(A, B, 1);
		_dirGraph.connectingEdge(A, null);

	}

	// testing InvalidVertexException for Connecting Edge directed with both
	// vertexes null
	@Test(expected = InvalidVertexException.class)
	public void myInvalidVertexConnectingEdgeDirBoth() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		_graph.insertEdge(A, B, 1);
		_dirGraph.connectingEdge(null, null);
	}

	// Testing connectingEdges for both directed and undirected, at the end test
	// noSuchEdge when directed edge goes opposite way as input vertices
	@Test(expected = NoSuchEdgeException.class)
	public void myConnectingEdgeTestAndUndirectedNoSuchEdgeDirected() {
		CS16Vertex<String> D = _dirGraph.insertVertex("D");
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Edge<String> DE = _dirGraph.insertEdge(D, E, 1);
		CS16Edge<String> ED = _dirGraph.insertEdge(E, D, 7);
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FD = _dirGraph.insertEdge(F, D, 2);
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> AB = _graph.insertEdge(A, B, 1);
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _graph.insertEdge(A, C, 2);
		assertThat(_graph.connectingEdge(A, B), is(AB));
		assertThat(_graph.connectingEdge(A, C), is(CA));
		assertThat(_graph.connectingEdge(C, B), is(BC));
		assertThat(_dirGraph.connectingEdge(D, E), is(DE));
		assertThat(_dirGraph.connectingEdge(E, D), is(ED));
		assertThat(_dirGraph.connectingEdge(E, F), is(EF));
		_dirGraph.connectingEdge(D, F);
	}

	// Testing for no such connecting edge for directed graphs
	@Test(expected = NoSuchEdgeException.class)
	public void myConnectingEdgeTestNoSuchEdgeunDirected() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _graph.insertEdge(A, C, 2);
		_graph.connectingEdge(A, B);
	}

	// Testing for no such connecting edge for undirected graphs where no edge
	// exists between the vertices
	@Test(expected = NoSuchEdgeException.class)
	public void myConnectingEdgeTestNoSuchEdgeDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> BC = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _dirGraph.insertEdge(A, C, 2);
		_dirGraph.connectingEdge(A, B);
	}

	// Testing for Invalid Vertex expection icoming edge directed
	@Test(expected = InvalidVertexException.class)
	public void incomingEdgeInvalidVertexDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> BC = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _dirGraph.insertEdge(A, C, 2);
		_dirGraph.incomingEdges(null);
	}

	// Testing for Invalid Vertex expection icoming edge directed
	@Test(expected = InvalidVertexException.class)
	public void incomingEdgeInvalidVertexUnDirected() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _graph.insertEdge(A, C, 2);
		_graph.incomingEdges(null);
	}

	// Testing the incomingEdges iterator on undirected and directed graphs
	@Test(timeout = 10000)
	public void testIncomingEdges() {
		// insert vertices
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> AB = _graph.insertEdge(A, B, 2);
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Vertex<String> G = _dirGraph.insertVertex("G");
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FG = _dirGraph.insertEdge(F, G, 2);
		CS16Edge<String> GF = _dirGraph.insertEdge(G, F, 2);
		CS16Edge<String> GE = _dirGraph.insertEdge(G, E, 2);
		CS16Edge<String> EG = _dirGraph.insertEdge(E, G, 2);

		// assert that incoming edges returns the correct edges
		List<CS16Edge<String>> actualVertices = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _graph.incomingEdges(A);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(1));
		assertThat(actualVertices.get(0), is(AB));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _graph.incomingEdges(B);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(2));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _graph.incomingEdges(C);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(1));
		assertThat(actualVertices.get(0), is(BC));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _dirGraph.incomingEdges(E);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(1));
		assertThat(actualVertices.get(0), is(GE));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _dirGraph.incomingEdges(G);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(2));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _dirGraph.incomingEdges(F);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(2));
	}

	// Testing for Invalid Vertex expection outgoing edge directed
	@Test(expected = InvalidVertexException.class)
	public void outgoingEdgeInvalidVertexDirected() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> BC = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _dirGraph.insertEdge(A, C, 2);
		_dirGraph.outgoingEdges(null);
	}

	// Testing for Invalid Vertex expection outgoing edge undirected
	@Test(expected = InvalidVertexException.class)
	public void OutgoingEdgeInvalidVertexUnDirected() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _graph.insertEdge(A, C, 2);
		_graph.outgoingEdges(null);
	}

	// Testing the outgoingEdges iterator on undirected and directed graphs
	@Test(timeout = 10000)
	public void testOutgoingEdges() {
		// insert vertices
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> AB = _graph.insertEdge(A, B, 2);
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Vertex<String> G = _dirGraph.insertVertex("G");
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FG = _dirGraph.insertEdge(F, G, 2);
		CS16Edge<String> GE = _dirGraph.insertEdge(G, E, 2);
		CS16Edge<String> EG = _dirGraph.insertEdge(E, G, 2);

		// assert that outgoing edges returns the correct edges
		List<CS16Edge<String>> actualVertices = new ArrayList<CS16Edge<String>>();
		Iterator<CS16Edge<String>> it = _graph.outgoingEdges(A);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(1));
		assertThat(actualVertices.get(0), is(AB));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _graph.outgoingEdges(B);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(2));
		assertThat(actualVertices.contains(BC), is(true));
		assertThat(actualVertices.contains(AB), is(true));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _graph.outgoingEdges(C);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(1));
		assertThat(actualVertices.get(0), is(BC));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _dirGraph.outgoingEdges(E);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(2));
		assertThat(actualVertices.contains(EG), is(true));
		assertThat(actualVertices.contains(EF), is(true));
		assertThat(actualVertices.contains(GE), is(false));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _dirGraph.outgoingEdges(G);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(1));
		assertThat(actualVertices.get(0), is(GE));

		actualVertices = new ArrayList<CS16Edge<String>>();
		it = _dirGraph.outgoingEdges(F);
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.size(), is(1));
		assertThat(actualVertices.get(0), is(FG));
	}

	// Direction exception for numOutgoingEdge
	@Test(expected = DirectionException.class)
	public void numOutgoingEdgeDirectionException() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CA = _graph.insertEdge(A, C, 2);
		_graph.numOutgoingEdges(B);
	}

	// InvalidVertexException for numOutgoingEdge due to null
	@Test(expected = InvalidVertexException.class)
	public void numOutgoingEdgeVertexException() {
		_dirGraph.numOutgoingEdges(null);
	}

	// testing the numOutgoingEdges method
	@Test(timeout = 10000)
	public void testnumOutgoingEdges() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> BC = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> CB = _dirGraph.insertEdge(C, B, 2);
		CS16Edge<String> CA = _dirGraph.insertEdge(C, A, 2);
		CS16Edge<String> AC = _dirGraph.insertEdge(A, C, 2);
		assertThat(_dirGraph.numOutgoingEdges(A), is(1));
		assertThat(_dirGraph.numOutgoingEdges(C), is(2));
		assertThat(_dirGraph.numOutgoingEdges(B), is(1));
		CS16Edge<String> BA = _dirGraph.insertEdge(B, A, 2);
		assertThat(_dirGraph.numOutgoingEdges(A), is(1));
		assertThat(_dirGraph.numOutgoingEdges(C), is(2));
		assertThat(_dirGraph.numOutgoingEdges(B), is(2));
	}

	// Testing opposite InvalidVertexException on Directed graph due to null
	@Test(expected = InvalidVertexException.class)
	public void oppositeDirectedVertexExceptionNull() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> BC = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> CB = _dirGraph.insertEdge(C, B, 2);
		CS16Edge<String> CA = _dirGraph.insertEdge(C, A, 2);
		_dirGraph.opposite(null, BC);
	}

	// Testing opposite InvalidVertexException on unDirected graph
	@Test(expected = InvalidVertexException.class)
	public void oppositeUnDirectedVertexException() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CB = _graph.insertEdge(C, B, 2);
		CS16Edge<String> CA = _graph.insertEdge(C, A, 2);
		_graph.opposite(null, BC);
	}

	// Testing opposite InvalidEdgeException on Directed graph due to null
	@Test(expected = InvalidEdgeException.class)
	public void oppositeDirectedEdgeExceptionDueToNull() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> BC = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> CB = _dirGraph.insertEdge(C, B, 2);
		CS16Edge<String> CA = _dirGraph.insertEdge(C, A, 2);
		_dirGraph.opposite(A, null);
	}

	// Testing opposite InvalidEdgeException on unDirected graph
	@Test(expected = InvalidEdgeException.class)
	public void oppositeUnDirectedEdgeException() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CB = _graph.insertEdge(C, B, 2);
		CS16Edge<String> CA = _graph.insertEdge(C, A, 2);
		_graph.opposite(B, null);
	}

	// Testing opposite NoSuchVertexException on Directed graph
	@Test(expected = NoSuchVertexException.class)
	public void oppositeDirectedNoSuchVertexException() {
		CS16Vertex<String> A = _dirGraph.insertVertex("A");
		CS16Vertex<String> B = _dirGraph.insertVertex("B");
		CS16Vertex<String> C = _dirGraph.insertVertex("C");
		CS16Edge<String> BC = _dirGraph.insertEdge(B, C, 2);
		CS16Edge<String> CB = _dirGraph.insertEdge(C, B, 2);
		_dirGraph.opposite(A, CB);
	}

	// Testing opposite NoSuchVertexException on unDirected graph
	@Test(expected = NoSuchVertexException.class)
	public void oppositeUnDirectedNoSuchVertexException() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> CB = _graph.insertEdge(C, B, 2);
		CS16Edge<String> CA = _graph.insertEdge(C, A, 2);
		_graph.opposite(A, BC);
	}

	// testing opposite on undirected and directed graph
	@Test(timeout = 10000)
	public void testOpposite() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> AB = _graph.insertEdge(A, B, 2);
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Vertex<String> G = _dirGraph.insertVertex("G");
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FG = _dirGraph.insertEdge(F, G, 2);
		CS16Edge<String> GE = _dirGraph.insertEdge(G, E, 2);
		CS16Edge<String> EG = _dirGraph.insertEdge(E, G, 2);

		assertThat(_graph.opposite(A, AB), is(B));
		assertThat(_graph.opposite(B, AB), is(A));
		assertThat(_graph.opposite(C, BC), is(B));
		assertThat(_graph.opposite(B, BC), is(C));

		assertThat(_dirGraph.opposite(E, EF), is(F));
		assertThat(_dirGraph.opposite(F, EF), is(E));
		assertThat(_dirGraph.opposite(G, FG), is(F));
		assertThat(_dirGraph.opposite(F, FG), is(G));
		assertThat(_dirGraph.opposite(G, GE), is(E));
		assertThat(_dirGraph.opposite(E, GE), is(G));
	}

	// Testing for InvalidEdgeException for end vertices directed
	@Test(expected = InvalidEdgeException.class)
	public void endVerticesInvalidEdgedirected() {
		_dirGraph.endVertices(null);
	}

	// Testing for InvalidEdgeException for end vertices unDirected
	@Test(expected = InvalidEdgeException.class)
	public void endVerticesInvalidEdgeUnDirected() {
		_graph.endVertices(null);
	}

	// Testing the efficacy of end vertices on directed and undirected
	@Test(timeout = 10000)
	public void testEndVertices() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> AB = _graph.insertEdge(A, B, 2);
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Vertex<String> G = _dirGraph.insertVertex("G");
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FG = _dirGraph.insertEdge(F, G, 2);
		CS16Edge<String> GE = _dirGraph.insertEdge(G, E, 2);
		CS16Edge<String> EG = _dirGraph.insertEdge(E, G, 2);

		assertThat(_graph.endVertices(AB).contains(A), is(true));
		assertThat(_graph.endVertices(AB).contains(B), is(true));
		assertThat(_graph.endVertices(BC).contains(B), is(true));
		assertThat(_graph.endVertices(BC).contains(C), is(true));

		assertThat(_graph.endVertices(EF).contains(E), is(true));
		assertThat(_graph.endVertices(FG).contains(F), is(true));
		assertThat(_graph.endVertices(GE).contains(G), is(true));
		assertThat(_graph.endVertices(EG).contains(E), is(true));

		assertThat(_graph.endVertices(EF).contains(F), is(true));
		assertThat(_graph.endVertices(FG).contains(G), is(true));
		assertThat(_graph.endVertices(GE).contains(E), is(true));
		assertThat(_graph.endVertices(EG).contains(G), is(true));
	}

	// Testing for InvalidEdgeException for first of end vertices directed
	@Test(expected = InvalidVertexException.class)
	public void areAdjacentInvalidVertexDirectedFirstVertex() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_dirGraph.areAdjacent(null, A);
	}

	// Testing for InvalidEdgeException for first of end vertices unDirected
	@Test(expected = InvalidVertexException.class)
	public void areAdjacentInvalidVertexUnDirectedFirstVertex() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.areAdjacent(null, A);
	}

	// Testing for InvalidEdgeException for second of end vertices directed
	@Test(expected = InvalidVertexException.class)
	public void areAdjacentInvalidVertexDirectedSecondvertex() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_dirGraph.areAdjacent(null, A);
	}

	// Testing for InvalidEdgeException for second of end vertices unDirected
	@Test(expected = InvalidVertexException.class)
	public void areAdjacentInvalidVertexUnDirectedSecondVertex() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		_graph.areAdjacent(null, A);
	}

	// Testing the areAdjacent method
	@Test(timeout = 10000)
	public void testareAdjacent() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> AB = _graph.insertEdge(A, B, 2);
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Vertex<String> G = _dirGraph.insertVertex("G");
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FG = _dirGraph.insertEdge(F, G, 2);
		CS16Edge<String> GE = _dirGraph.insertEdge(G, E, 2);
		CS16Edge<String> EG = _dirGraph.insertEdge(E, G, 2);

		assertThat(_graph.areAdjacent(A, B), is(true));
		assertThat(_graph.areAdjacent(B, A), is(true));
		assertThat(_graph.areAdjacent(C, B), is(true));
		assertThat(_graph.areAdjacent(B, C), is(true));
		assertThat(_graph.areAdjacent(C, B), is(true));
		assertThat(_graph.areAdjacent(B, C), is(true));
		assertThat(_dirGraph.areAdjacent(E, F), is(true));
		assertThat(_dirGraph.areAdjacent(E, G), is(true));
		assertThat(_dirGraph.areAdjacent(G, E), is(true));
		assertThat(_dirGraph.areAdjacent(F, G), is(true));
		assertThat(_dirGraph.areAdjacent(F, E), is(false));

		_graph.removeEdge(BC);
		_dirGraph.removeEdge(EG);
		_dirGraph.removeEdge(GE);
		assertThat(_graph.areAdjacent(B, C), is(false));
		assertThat(_graph.areAdjacent(C, B), is(false));
		assertThat(_dirGraph.areAdjacent(G, E), is(false));
		assertThat(_dirGraph.areAdjacent(E, G), is(false));
	}

	// testing clear on a undirected graph
	@Test(timeout = 10000)
	public void clearUnDir() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> AB = _graph.insertEdge(A, B, 2);
		_graph.clear();
		assertThat(_graph.getNumVertices(), is(0));

		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.isEmpty(), is(true));

		actualVertices = new ArrayList<CS16Vertex<String>>();
		it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.isEmpty(), is(true));
	}

	// testing clear on a directed graph
	@Test(timeout = 10000)
	public void clearDir() {
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Vertex<String> G = _dirGraph.insertVertex("G");
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FG = _dirGraph.insertEdge(F, G, 2);
		CS16Edge<String> GE = _dirGraph.insertEdge(G, E, 2);
		CS16Edge<String> EG = _dirGraph.insertEdge(E, G, 2);
		_dirGraph.clear();
		_dirGraph.clear();
		assertThat(_dirGraph.getNumVertices(), is(0));

		List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
		Iterator<CS16Vertex<String>> it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.isEmpty(), is(true));

		actualVertices = new ArrayList<CS16Vertex<String>>();
		it = _graph.vertices();
		while (it.hasNext()) {
			actualVertices.add(it.next());
		}
		assertThat(actualVertices.isEmpty(), is(true));
	}

	// testing Get num vertices on both undirected and directed graph
	@Test(timeout = 10000)
	public void getNumVertices() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Edge<String> BC = _graph.insertEdge(B, C, 2);
		CS16Edge<String> AB = _graph.insertEdge(A, B, 2);
		CS16Vertex<String> D = _dirGraph.insertVertex("D");
		CS16Vertex<String> E = _dirGraph.insertVertex("E");
		CS16Vertex<String> F = _dirGraph.insertVertex("F");
		CS16Vertex<String> G = _dirGraph.insertVertex("G");
		CS16Edge<String> EF = _dirGraph.insertEdge(E, F, 2);
		CS16Edge<String> FG = _dirGraph.insertEdge(F, G, 2);
		CS16Edge<String> GE = _dirGraph.insertEdge(G, E, 2);
		CS16Edge<String> EG = _dirGraph.insertEdge(E, G, 2);
		assertThat(_graph.getNumVertices(), is(3));
		assertThat(_dirGraph.getNumVertices(), is(4));
		_graph.removeVertex(A);
		_graph.removeVertex(B);
		_dirGraph.removeVertex(E);
		_dirGraph.removeVertex(F);
		_dirGraph.removeVertex(G);
		_dirGraph.removeVertex(D);
		assertThat(_graph.getNumVertices(), is(1));
		assertThat(_dirGraph.getNumVertices(), is(0));

	}
	//
	/*
	 * ####################################################
	 * 
	 * DO NOT MODIFY ANYTHING BELOW THIS LINE
	 * 
	 * ####################################################
	 */

	@SuppressWarnings("unchecked")
	@Before
	public void makeGraph() {
		Class<?> graphClass = null;
		try {
			graphClass = Class.forName(_graphClassName);
			Constructor<?> constructor = graphClass.getConstructors()[0];
			_graph = (Graph<String>) constructor.newInstance(false);
			_dirGraph = (Graph<String>) constructor.newInstance(true);
		} catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException
				| IllegalArgumentException e) {
			System.err.println("Exception while instantiating Graph class in GraphTest.");
			e.printStackTrace();
		}
	}

	public GraphTest(String graphClassName) {
		this._graphClassName = graphClassName;
	}
}
