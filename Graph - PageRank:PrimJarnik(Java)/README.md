### Summary ###

This project creates a graph data strcuture from scratch (AdjacencyMatrixGraph.java and MyDecorator.java) and implements the pagerank (elementary search) algorithm and PrimJarnik (MST) algorithm on the graph. Short descriptions of the classes are written below.


## Class Descriptions ##


MyPageRank: Class to find the pageRank of every Vertex.  In this class, outside of the 

provided instance variables, I created  numVert, sink, prevRank, and currRank, instance 

variables. These were used to keep track of the total number of vertices, the index that sinks

 vertices were stored at in all my lists, the previous rank of vertices, and the currentRank of 

vertices throughout all three of my methods respectively.  I used a local variable decorator 

called index in calcPageRank, so that I could access the index that represents the vertices on 

the opposite ends of incoming edges.  I also created an iterator called verts so that I could 

loop over all vertices in the beginning of calcPageRank to initialize.  I handled sinks by adding 

their (previous rank/number outgoing vertices)*dampingFactor + (1-damping Factor)/number 

vertices to all vertices. This is more clearly commented within the class. I also

implemented  the extra credit in removeBlackList.  Information on this method can 

be found in the conceptual question above.



MyPrimJarnik: This class implemented the PrimJarnik algorithm to find a MST.  In this class I 

used 4 decorators, cost, previous, visited, and entry.  I used cost to keep track of the minimum 

edge that could connect an edge to the MST, and updated it as different minimum edges were 

found.  I used previous to show which vertex the edge that connected a given vertex to the 

MST came from.  I used visited, to keep track of which edges were still in my PQ, when they 

were removed I set visited equal to true.  Finally, I used entry so that I could replace the key in 

my PQ when updating a cost.  I should also mention a PQ key was used were I removed the 

vertex with the lowest cost from the PQ at each iteration. 



AdjacencyMatrixGraph: had an adjacency matrix as the underlying data structure to keep 

track of edges going from a vertex, which dictated the row, and to a vertex, which dictated the 

column.  Also had two sets to keep track of the edges and vertices respectively.  Had a stack 

to keep track of the indexes from which remove vertices were removed in my vertices set, so I 

could add new vertices at these indices before adding to the end of the set. An integer to keep 

track of the number of vertices. And a boolean that dictates whether the graph was directed. I 

then implemented all methods as asked for, and commented for clarity.



MyDecorator: implemented all methods using a HashMap so that the methods could 

run in 0(1) time.  Thus, everytime a decorator is instantiated, you pass in a vertex, and a key 

to the decorator, and the hashMap stores the key value pair.  Given the key, the value can be 

accessed in O(1) time, and all methods in the class can be performed in O(1) time.



GraphTest: In this class I simply wrote two test for each exception raised, one for a directed, 

and one for an undirected graph (when the method functioned on both graphs).  And then I 

wrote one large test for the efficacy of the method that included both undirected and directed 

graphs.  I did not break up the test of the actual functionality of the tests into directed and 

undirected because I already had so many tests, I thought it was easier to follow if combined 

into one.


MSFtest: oneVertex tested a graph with one vertex.  sameValue tested a graph where some 

edges had the same value.  completeGraph tested a complete graph. standardTest was just 

testing a general graph.  singleUnconnectedTest tested a graph with a single unconnected 

vertex, unConnectedTest tested a graph with two unconnected segments where each 

segment had edges, emptyGraph tested an emptyGraph, onePath tested a graph where there 

was only one path between all vertices, connectedByOneEdgeTest tested a graph where two 

segments were just connected by one edge, loopGraph tested a graph that was a loop (i.e 

looks like a square).



MyPageRankTest: simpleTestOneSink tests a directed graph with one sink.  

UnconnectedSinkstest tests a directed graph each with a sink.  MultipleConnectedSinksTest 

tests a connected directed graph with multiple sinks. oneVertex tests a directed graph with 

one vertex. twoVertexes tests a directed graph with two vertexes. cycleTest tests a directed 

graph that is one big cycle. twoVertexesPointingAtEachother tests a directed graph with two 
vertexes that each have a directed edge pointing at the other vertex.  allPointingAtOne tests a 

directed graph where all vertices point to one vertex.  onePointingatAll tests a directed graph 

where one vertex points to all other vertices.  emptyGraph tests a directed graph that is 

empty. And 

sinksUnconnctedPointEdgeAKAeverythingAndTheKitchenSinkGetItKitchenSINKHA has 2 

unconnected graphs edge with two sinks and vertices that have two edges between them, ran pageRank twice

to make sure it doesn't alter any kind of graph.


