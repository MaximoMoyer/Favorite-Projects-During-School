package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.MinSpanForest;

/**
 * This class tests the functionality of your MSF algorithms on an
 * AdjacencyMatrixGraph with a 'String' type parameter for the vertices. Edge
 * elements are Integers. The general framework of a test case is: - Name the
 * test method descriptively, mentioning what is being tested (it is ok to have
 * slightly verbose method names here) Set-up the program state (ex: instantiate
 * a heap and insert K,V pairs into it) - Use assertions to validate that the
 * progam is in the state you expect it to be See header comments over tests for
 * what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset
 * to a new instance of the a Graph<String> that you can simply use 'as is', as
 * well as the '_msf' variable.
 *
 * Of course, please do not modify anything below the 'DO NOT MODIFY ANYTHING
 * BELOW THIS LINE' line, or the above assumptions may be broken.
 */
@RunWith(Parameterized.class)
public class MsfTest {

	private String _msfClassName;
	private MinSpanForest<String> _msf;
	private Graph<String> _graph;

	@Test
	public void simpleTest() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 1);
		CS16Edge<String> ca = _graph.insertEdge(A, C, 10);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(2));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(bc), is(true));
		assertThat(MSF.contains(ca), is(false));
	}

	/*
	 * This is the method that, using junit magic, provides the list of MSF
	 * algorithms that should be created and be tested via the methods above. By
	 * default, all of the above tests will be run on MyPrimJarnik algorithm
	 * implementations. If you're interested in testing the methods on just one of
	 * the algorithms, comment out the one you don't want in the method below!
	 */
	@Parameters(name = "with msf algo: {0}")
	public static Collection<String> msts() {
		List<String> algoNames = new ArrayList<>();
		algoNames.add("graph.MyPrimJarnik");
		return algoNames;
	}

	// testing one vertex
	@Test
	public void oneVetex() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(0));
	}

	// testing two vertices
	@Test
	public void twoVetex() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(1));
		assertThat(MSF.contains(ab), is(true));

	}

	// edges with the same value
	@Test
	public void sameValue() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");
		CS16Vertex<String> E = _graph.insertVertex("E");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 1);
		CS16Edge<String> bd = _graph.insertEdge(D, B, 2);
		CS16Edge<String> de = _graph.insertEdge(D, E, 5);
		CS16Edge<String> be = _graph.insertEdge(B, E, 4);
		CS16Edge<String> ae = _graph.insertEdge(A, E, 5);
		CS16Edge<String> cd = _graph.insertEdge(D, C, 2);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(4));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(be), is(true));
		assertThat((MSF.contains(bd) && MSF.contains(cd)), is(false));
		assertThat((MSF.contains(bd) | MSF.contains(cd)), is(true));
		assertThat(MSF.contains(ac), is(true));
		assertThat(MSF.contains(de), is(false));
		assertThat(MSF.contains(ae), is(false));
	}

	// test with a complete graph
	@Test
	public void completeGraph() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 10);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 15);
		CS16Edge<String> ad = _graph.insertEdge(A, D, 20);
		CS16Edge<String> bc = _graph.insertEdge(B, C, 19);
		CS16Edge<String> bd = _graph.insertEdge(B, D, 11);
		CS16Edge<String> cd = _graph.insertEdge(C, D, 14);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(3));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(ac), is(false));
		assertThat(MSF.contains(ad), is(false));
		assertThat(MSF.contains(bc), is(false));
		assertThat(MSF.contains(bd), is(true));
		assertThat(MSF.contains(cd), is(true));
	}

	// testing a standard graph
	@Test
	public void standardTest() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");
		CS16Vertex<String> E = _graph.insertVertex("E");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 2);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
		CS16Edge<String> bd = _graph.insertEdge(B, D, 4);
		CS16Edge<String> cd = _graph.insertEdge(C, D, 2);
		CS16Edge<String> de = _graph.insertEdge(E, D, 1);
		CS16Edge<String> ce = _graph.insertEdge(E, C, 4);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(4));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(cd), is(true));
		assertThat(MSF.contains(de), is(true));
		assertThat((MSF.contains(ac) & MSF.contains(bd)), is(false));
		assertThat((MSF.contains(ac) | MSF.contains(bd)), is(true));
		assertThat(MSF.contains(ce), is(false));
	}

	// testing a spanning forest with a single unconnected vertex
	@Test
	public void singelUnconnectedTest() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");
		CS16Vertex<String> E = _graph.insertVertex("E");
		CS16Vertex<String> F = _graph.insertVertex("F");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 2);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
		CS16Edge<String> bd = _graph.insertEdge(B, D, 4);
		CS16Edge<String> cd = _graph.insertEdge(C, D, 2);
		CS16Edge<String> de = _graph.insertEdge(E, D, 1);
		CS16Edge<String> ce = _graph.insertEdge(E, C, 4);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(4));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(cd), is(true));
		assertThat(MSF.contains(de), is(true));
		assertThat((MSF.contains(bd) && MSF.contains(ac)), is(false));
		assertThat((MSF.contains(ac) | MSF.contains(bd)), is(true));
		assertThat(MSF.contains(ce), is(false));
	}

	// testing a spanning forest composed of two unconnected parts
	@Test
	public void UnconnectedTest() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");
		CS16Vertex<String> E = _graph.insertVertex("E");

		CS16Vertex<String> G = _graph.insertVertex("G");
		CS16Vertex<String> H = _graph.insertVertex("H");
		CS16Vertex<String> I = _graph.insertVertex("I");
		CS16Vertex<String> J = _graph.insertVertex("J");
		CS16Vertex<String> K = _graph.insertVertex("K");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 2);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
		CS16Edge<String> bd = _graph.insertEdge(B, D, 4);
		CS16Edge<String> cd = _graph.insertEdge(C, D, 2);
		CS16Edge<String> de = _graph.insertEdge(E, D, 1);
		CS16Edge<String> ce = _graph.insertEdge(E, C, 4);

		CS16Edge<String> gh = _graph.insertEdge(G, H, 1);
		CS16Edge<String> gi = _graph.insertEdge(G, I, 4);
		CS16Edge<String> ik = _graph.insertEdge(I, K, 10);
		CS16Edge<String> kj = _graph.insertEdge(K, J, 10);
		CS16Edge<String> ij = _graph.insertEdge(I, J, 11);
		CS16Edge<String> hj = _graph.insertEdge(H, J, 30);
		CS16Edge<String> hi = _graph.insertEdge(H, I, 0);

		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(8));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(cd), is(true));
		assertThat(MSF.contains(de), is(true));
		assertThat((MSF.contains(bd) && MSF.contains(ac)), is(false));
		assertThat((MSF.contains(ac) | MSF.contains(bd)), is(true));
		assertThat(MSF.contains(ce), is(false));

		assertThat(MSF.contains(gh), is(true));
		assertThat(MSF.contains(hi), is(true));
		assertThat(MSF.contains(ik), is(true));
		assertThat(MSF.contains(kj), is(true));
		assertThat(MSF.contains(hj), is(false));
		assertThat(MSF.contains(ij), is(false));
		assertThat(MSF.contains(gi), is(false));
	}

	// empty graph test
	@Test
	public void emptyGraph() {

		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(0));
	}

	// testing a tree with only one path
	@Test
	public void onePath() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");
		CS16Vertex<String> E = _graph.insertVertex("E");
		CS16Vertex<String> F = _graph.insertVertex("F");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 10);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 15);
		CS16Edge<String> cd = _graph.insertEdge(C, D, 14);
		CS16Edge<String> ce = _graph.insertEdge(C, E, 14123123);
		CS16Edge<String> df = _graph.insertEdge(D, F, 141244134);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(5));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(ac), is(true));
		assertThat(MSF.contains(cd), is(true));
		assertThat(MSF.contains(ce), is(true));
		assertThat(MSF.contains(df), is(true));
	}

	// two separate sections of vertexes connected by one edge
	@Test
	public void connectedByOneEdgeTest() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");
		CS16Vertex<String> E = _graph.insertVertex("E");

		CS16Vertex<String> G = _graph.insertVertex("G");
		CS16Vertex<String> H = _graph.insertVertex("H");
		CS16Vertex<String> I = _graph.insertVertex("I");
		CS16Vertex<String> J = _graph.insertVertex("J");
		CS16Vertex<String> K = _graph.insertVertex("K");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 2);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
		CS16Edge<String> bd = _graph.insertEdge(B, D, 4);
		CS16Edge<String> cd = _graph.insertEdge(C, D, 2);
		CS16Edge<String> de = _graph.insertEdge(E, D, 1);
		CS16Edge<String> ce = _graph.insertEdge(E, C, 4);

		CS16Edge<String> gh = _graph.insertEdge(G, H, 1);
		CS16Edge<String> gi = _graph.insertEdge(G, I, 4);
		CS16Edge<String> ik = _graph.insertEdge(I, K, 10);
		CS16Edge<String> kj = _graph.insertEdge(K, J, 10);
		CS16Edge<String> ij = _graph.insertEdge(I, J, 11);
		CS16Edge<String> hj = _graph.insertEdge(H, J, 30);
		CS16Edge<String> hi = _graph.insertEdge(H, I, 0);
		CS16Edge<String> ha = _graph.insertEdge(H, A, 29381);

		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(9));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(cd), is(true));
		assertThat(MSF.contains(de), is(true));
		assertThat((MSF.contains(bd) && MSF.contains(ac)), is(false));
		assertThat((MSF.contains(ac) | MSF.contains(bd)), is(true));
		assertThat(MSF.contains(ce), is(false));

		assertThat(MSF.contains(gh), is(true));
		assertThat(MSF.contains(hi), is(true));
		assertThat(MSF.contains(ik), is(true));
		assertThat(MSF.contains(kj), is(true));
		assertThat(MSF.contains(hj), is(false));
		assertThat(MSF.contains(ij), is(false));
		assertThat(MSF.contains(gi), is(false));

		assertThat(MSF.contains(ha), is(true));
	}

	// test that is just a loop between four vertices
	@Test
	public void loopGraph() {
		CS16Vertex<String> A = _graph.insertVertex("A");
		CS16Vertex<String> B = _graph.insertVertex("B");
		CS16Vertex<String> C = _graph.insertVertex("C");
		CS16Vertex<String> D = _graph.insertVertex("D");

		CS16Edge<String> ab = _graph.insertEdge(A, B, 10);
		CS16Edge<String> ac = _graph.insertEdge(A, C, 15);
		CS16Edge<String> bd = _graph.insertEdge(B, D, 11);
		CS16Edge<String> cd = _graph.insertEdge(C, D, 14);
		Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

		assertThat(MSF.size(), is(3));
		assertThat(MSF.contains(ab), is(true));
		assertThat(MSF.contains(ac), is(false));
		assertThat(MSF.contains(bd), is(true));
		assertThat(MSF.contains(cd), is(true));
	}

	/*
	 * ####################################################
	 * 
	 * DO NOT MODIFY ANYTHING BELOW THIS LINE
	 * 
	 * ####################################################
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		Class<?> msfClass = null;
		try {
			msfClass = Class.forName(_msfClassName);
			Constructor<?> constructor = msfClass.getConstructors()[0];
			_msf = (MinSpanForest<String>) constructor.newInstance();
		} catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException
				| IllegalArgumentException e) {
			System.err.println("Exception while instantiating msf class " + _msfClassName + " from test.");
			e.printStackTrace();
		}
		_graph = new AdjacencyMatrixGraph<>(false);
	}

	public MsfTest(String msfClassName) {
		_msfClassName = msfClassName;
	}
}
