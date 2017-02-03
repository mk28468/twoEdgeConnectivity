package hw3;

//THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
//A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Junyuan Ke 

//You are welcome to copy code from share/book/ to here.

//Summary: TwoEC should compute the 2-edge-connected components of a
//given graph, using an API similar to CC (see CC.java).
//
//Additional requirements:
// * do not modify the given Graph object, G
// * your solution should use O(V+E) time and O(V) space, like DFS
// * for all v, id(v) should be at least 0 and less than G.V()
//
//Step 1:
//
//  In your TwoEC(G) constructor, you need a first DFS pass like
//  that in Bridge.java, to identify all the bridge edges. So,
//  copy all the code from Bridge.java to this class.
//  Keep the private fields, and rename the constructor.
//  Modify the dfs to be quiet (do not print out the bridges).
//  You can lose the "bridges()" and "main()" methods.
//  The "components()" method becomes your "count()" method.
//
//Step 2:
//
//  You still need a second pass of DFS.
//  Add private "id" and "size" arrays (like low and pre).
//
//  In the first DFS pass from Bridge.java, you compute
//  pre[v] and low[v] for all v.  You can also compute size[v],
//  which is the size of the DFS-subtree rooted at v, after bridge
//  edges have been deleted.  (You do not actually have to delete
//  the bridge edges, just don't count sizes over a bridge.)
//
//  The second DFS pass should visit vertices in exactly the same
//  order as before, but now we can detect bridge edges when we
//  first see them (look for low[w]==pre[w]).  Using that, we can
//  now compute id[v] for each v.  You may also want to compute
//  the final version of size[v] in this pass.  Or alternatively,
//  store size(v) as size2[id[v]], where size2 is an array that
//  maps each id to its size.)
//
//Step 3:
//
//  Make sure these public methods are correctly defined:
//     int count()     -- return the number of 2EC components
//     int id(int v)   -- identify the 2EC component containing v
//     int size(int v) -- size of 2EC component containing v
//
//Step 4:
//
//  Test your code! The Makefile defines several test inputs
//  (try just "make" to get a list of possibilities).
//  When you  are done or out of time, turnin (see Notes.txt)


//Skeletal class (just enough to compile here)
public class TwoEC
{
	private int[] low;
    private int[] pre;
    private int cnt;            // preorder counter
    private int bridges;        // number of bridge edges found
    private int cc;             // number of connected components
    private int[] id;
    private int[] size;
    private boolean[] marked;
    private int twoecc;
 public TwoEC(Graph G) {
	 low = new int[G.V()];
     pre = new int[G.V()];
     id = new int[G.V()];
     size = new int[G.V()];
     marked = new boolean[G.V()];
     for (int v = 0; v < G.V(); v++) low[v] = -1;
     for (int v = 0; v < G.V(); v++) pre[v] = -1;

     for (int v = 0; v < G.V(); v++)
         if (pre[v] == -1) {
             ++cc;
             dfs(G, v, v);
         }
     for (int v = 0; v < G.V(); v++) {
         if (!marked[v]) {
        	 id[v] = twoecc++;
             dfs2(G, v);
         }
     }
 }

private void dfs2(Graph G, int v) {
     marked[v] = true;
     size[id[v]]++;
     for (int w : G.adj(v)) {
    	 if (!marked[w]) {
    		 if(low[w] != pre[w]){
        		 id[w] = id[v];
        	 }
    		 else{
        		 id[w] = twoecc++;
        	 }
             dfs2(G, w);
         }
     }
 }
 private void dfs(Graph G, int u, int v) {
     pre[v] = cnt++;
	// StdOut.println("pre[" + v + "] is " + pre[v]);
     low[v] = pre[v];
     for (int w : G.adj(v)) {
         if (pre[w] == -1) {
             dfs(G, v, w);
             low[v] = Math.min(low[v], low[w]);
             if (low[w] == pre[w]) {
                // StdOut.println(v + "-" + w + " is a bridge");
                 bridges++;
             }
         }

         // update low number - ignore reverse of edge leading to v
         else if (w != u)
             low[v] = Math.min(low[v], pre[w]);
     }
 }
 public int count() { 
	 // The number of 2-edge-connected components in the graph.
	 return bridges + cc; 
 }
 public int id(int v) { return id[v]; }
 public int size(int v) { return size[id[v]]; }
}
