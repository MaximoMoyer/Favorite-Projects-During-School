package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class tests the functionality of your PageRank algorithm on a directed
 * AdjacencyMatrixGraph. The general framework of a test case is: - Name the
 * test method descriptively, - Mention what is being tested (it is ok to have
 * slightly verbose method names here)
 * 
 * Some tips to keep in mind when writing test cases: - All pages' ranks should
 * total to 1. - It will be easier to start out by writing test cases on smaller
 * graphs.
 *
 */
public class MyPageRankTest {

	// This is your margin of error for testing
	double _epsilon = 0.03;

	/**
	 * A simple test with four pages. Each page only has one outgoing link to a
	 * different page, resulting in a square shape or cycle when visualized. The
	 * pages' total ranks is 1.
	 */
	@Test
	public void testFourEqualRanks() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");

		/**
		 * Inserting an edge with a null element since a weighted edge is not meaningful
		 * for the PageRank algorithm.
		 */

		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b, c, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c, d, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d, a, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// Check that the number of vertices returned by PageRank is 4
		assertEquals(output.size(), 4);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		// The weights of each vertex should be 0.25
		double expectedRankA = 0.25;
		double expectedRankB = 0.25;
		double expectedRankC = 0.25;
		double expectedRankD = 0.25;

		// The sum of weights should always be 1
		assertEquals(total, 1, _epsilon);

		// The Rank for each vertex should be 0.25 +/- epsilon
		assertEquals(output.get(a), expectedRankA, _epsilon);
		assertEquals(output.get(b), expectedRankB, _epsilon);
		assertEquals(output.get(c), expectedRankC, _epsilon);
		assertEquals(output.get(d), expectedRankD, _epsilon);

	}

	/**
	 * A simple test with three pages. Note that vertex A's rank is not 0 even
	 * though it has no incoming edges, demonstrating the effects of the damping
	 * factor and handling sinks.
	 */
	@Test
	public void simpleTestOne() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b, c, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 3);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		// These are precomputed values
		double expectedRankA = 0.186;
		double expectedRankB = 0.342;
		double expectedRankC = 0.471;

		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), expectedRankA, _epsilon);
		assertEquals(output.get(b), expectedRankB, _epsilon);
		assertEquals(output.get(c), expectedRankC, _epsilon);

	}

	// a test with one sink
	@Test
	public void simpleTestOneSink() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(c, b, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 3);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), output.get(c));
		assertThat((output.get(b) > output.get(a) && output.get(b) > output.get(c)), is(true));

	}

	// two unconnected directed graphs each with sinks
	@Test
	public void UnconnectedSinksTest() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");

		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(c, b, null);

		CS16Edge<String> e3 = adjMatrix.insertEdge(d, e, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(f, e, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), output.get(c));
		assertThat((output.get(b) > output.get(a) && output.get(b) > output.get(c)), is(true));
		assertEquals(output.get(d), output.get(f));
		assertThat((output.get(e) > output.get(d) && output.get(e) > output.get(f)), is(true));
		assertEquals(output.get(a), output.get(d));
		assertEquals(output.get(a), output.get(f));
		assertEquals(output.get(c), output.get(f));
		assertEquals(output.get(c), output.get(d));
	}

	// testing multiple sinks that are in one connected graph
	@Test
	public void multipleConnectedSinksTest() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");

		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a, c, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(b, c, null);

		CS16Edge<String> e3 = adjMatrix.insertEdge(d, a, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(d, e, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 5);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertThat((output.get(c) > output.get(b)), is(true));
		assertThat((output.get(b) > output.get(e)), is(true));
		assertEquals(output.get(a), output.get(e), _epsilon);
		assertThat((output.get(a) > output.get(d)), is(true));
		assertThat((output.get(e) > output.get(d)), is(true));
		assertThat((output.get(c) > output.get(a)), is(true));
	}

	// Testing one vertex
	@Test
	public void oneVertex() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 1);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), 1, _epsilon);
	}

	// Testing two Vertexes
	@Test
	public void twoVertexes() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");

		CS16Edge<String> e1 = adjMatrix.insertEdge(a, b, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 2);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertThat((output.get(b) > output.get(a)), is(true));
	}

	// Testing a directed graph that is a cycle
	@Test
	public void cycleTest() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b, c, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c, d, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d, e, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(e, a, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 5);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), output.get(b), _epsilon);
		assertEquals(output.get(b), output.get(c), _epsilon);
		assertEquals(output.get(c), output.get(d), _epsilon);
		assertEquals(output.get(d), output.get(e), _epsilon);
		assertEquals(output.get(e), output.get(a), _epsilon);
	}

	// Testing two Vertexes pointing at one another
	@Test
	public void twoVertexesPointingAtEachOther() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");

		CS16Edge<String> e1 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(b, a, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 2);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), output.get(b), _epsilon);
	}

	// All vertices pointing to one vertex
	@Test
	public void allPointingAtOne() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");

		CS16Edge<String> e1 = adjMatrix.insertEdge(b, a, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c, a, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d, a, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(e, a, null);
		CS16Edge<String> e5 = adjMatrix.insertEdge(f, a, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}
		assertEquals(total, 1, _epsilon);
		assertThat((output.get(a) > output.get(b)), is(true));
		assertThat((output.get(a) > output.get(c)), is(true));
		assertThat((output.get(a) > output.get(d)), is(true));
		assertThat((output.get(a) > output.get(e)), is(true));
		assertThat((output.get(a) > output.get(d)), is(true));
		assertEquals(output.get(b), output.get(c), _epsilon);
		assertEquals(output.get(c), output.get(d), _epsilon);
		assertEquals(output.get(d), output.get(e), _epsilon);
		assertEquals(output.get(e), output.get(f), _epsilon);
	}

	// one Vertex points to all other vertices
	@Test
	public void onePointingAtAll() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");

		CS16Edge<String> e1 = adjMatrix.insertEdge(a, f, null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(a, c, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(a, d, null);
		CS16Edge<String> e5 = adjMatrix.insertEdge(a, e, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertThat((output.get(f) > output.get(a)), is(true));
		assertThat((output.get(e) > output.get(a)), is(true));
		assertThat((output.get(d) > output.get(a)), is(true));
		assertThat((output.get(c) > output.get(a)), is(true));
		assertThat((output.get(b) > output.get(a)), is(true));
		assertEquals(output.get(b), output.get(c), _epsilon);
		assertEquals(output.get(c), output.get(d), _epsilon);
		assertEquals(output.get(d), output.get(e), _epsilon);
		assertEquals(output.get(e), output.get(f), _epsilon);
	}

	// empty graph test
	@Test
	public void emptyGraph() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		MyPageRank<String> pr = new MyPageRank<String>();
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
		assertEquals(output.size(), 0);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}
		assertEquals(total, 0, _epsilon);
	}

	// sinks, unconnected graphs, and edges pointing back at each other.  Ran page rank twice to make sure graph doesn't change when page rank is run in any context
	@Test
	public void sinksUnconnctedPointEdgeAKAeverythingAndTheKitchenSinkGetItKitchenSINKHA() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> y = adjMatrix.insertVertex("Y");
		CS16Vertex<String> r = adjMatrix.insertVertex("R");

		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");
		CS16Vertex<String> z = adjMatrix.insertVertex("Z");
		CS16Vertex<String> s = adjMatrix.insertVertex("S");

		CS16Edge<String> e0 = adjMatrix.insertEdge(a, b, null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(c, b, null);
		CS16Edge<String> e5 = adjMatrix.insertEdge(b, c, null);
		CS16Edge<String> e7 = adjMatrix.insertEdge(a, y, null);
		CS16Edge<String> e8 = adjMatrix.insertEdge(c, r, null);

		CS16Edge<String> e3 = adjMatrix.insertEdge(d, e, null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(f, e, null);
		CS16Edge<String> e6 = adjMatrix.insertEdge(e, f, null);
		CS16Edge<String> e9 = adjMatrix.insertEdge(d, z, null);
		CS16Edge<String> e10 = adjMatrix.insertEdge(f, s, null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 10);
		double total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertThat((output.get(b) > output.get(a) && output.get(c) > output.get(a)), is(true));
		assertThat((output.get(c) > output.get(b)), is(true));
		assertThat((output.get(b) > output.get(r)), is(true));
		assertThat((output.get(r) > output.get(y)), is(true));
		assertThat((output.get(y) > output.get(a)), is(true));
		assertThat((output.get(e) > output.get(d) && output.get(f) > output.get(d)), is(true));
		assertThat((output.get(f) > output.get(e)), is(true));
		assertThat((output.get(e) > output.get(s)), is(true));
		assertThat((output.get(s) > output.get(z)), is(true));
		assertThat((output.get(z) > output.get(d)), is(true));
		assertEquals(output.get(a), output.get(d), _epsilon);
		assertEquals(output.get(b), output.get(e), _epsilon);
		assertEquals(output.get(c), output.get(f), _epsilon);
		assertEquals(output.get(z), output.get(y), _epsilon);
		assertEquals(output.get(r), output.get(s), _epsilon);
		
		//running twice to make sure the graph isn't altered
		output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 10);
		total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertThat((output.get(b) > output.get(a) && output.get(c) > output.get(a)), is(true));
		assertThat((output.get(c) > output.get(b)), is(true));
		assertThat((output.get(b) > output.get(r)), is(true));
		assertThat((output.get(r) > output.get(y)), is(true));
		assertThat((output.get(y) > output.get(a)), is(true));
		assertThat((output.get(e) > output.get(d) && output.get(f) > output.get(d)), is(true));
		assertThat((output.get(f) > output.get(e)), is(true));
		assertThat((output.get(e) > output.get(s)), is(true));
		assertThat((output.get(s) > output.get(z)), is(true));
		assertThat((output.get(z) > output.get(d)), is(true));
		assertEquals(output.get(a), output.get(d), _epsilon);
		assertEquals(output.get(b), output.get(e), _epsilon);
		assertEquals(output.get(c), output.get(f), _epsilon);
		assertEquals(output.get(z), output.get(y), _epsilon);
		assertEquals(output.get(r), output.get(s), _epsilon);
		//making sure no edges were left in the graph, or removed from the graph
		Iterator<CS16Edge<String>> iterator = adjMatrix.edges();
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count+=1 ;
		}
		assertEquals(count, 10);
		
		adjMatrix.removeVertex(a);
		output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 9);
		total = 0;
		for (double rank : output.values()) {
			total += rank;
		}

		assertEquals(total, 1, _epsilon);
		assertThat((output.get(c) > output.get(b)), is(true));
		assertThat((output.get(b) > output.get(r)), is(true));
		assertThat((output.get(r) > output.get(y)), is(true));
		assertThat((output.get(e) > output.get(d) && output.get(f) > output.get(d)), is(true));
		assertThat((output.get(f) > output.get(e)), is(true));
		assertThat((output.get(e) > output.get(s)), is(true));
		assertThat((output.get(s) > output.get(z)), is(true));
		assertThat((output.get(z) > output.get(d)), is(true));
		//making sure no edges were left in the graph, or removed from the graph
		iterator = adjMatrix.edges();
		count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count+=1 ;
		}
		assertEquals(count, 8);

	}

}
