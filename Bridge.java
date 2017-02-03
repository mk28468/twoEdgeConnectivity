package hw3;

//MIC: corrected the components() method, modified test client (main)
//to allow a single filename as argument.

/*************************************************************************
*  Compilation:  javac Bridge.java
*  Dependencies: Graph.java
*
*  Identifies bridge edges and prints them out. This decomposes
*  a directed graph into two-edge connected components.
*  Runs in O(E + V) time.
*
*  Key quantity:  low[v] = minimum DFS preorder number of v
*  and the set of vertices w for which there is a back edge (x, w)
*  with x a descendant of v and w an ancestor of v.
*
*  NOTE: this code assumes no parallel edges.  Two parallel edges
*  could be (incorrectly) identified as bridges.
*
*************************************************************************/

public class Bridge {
 private int[] low;
 private int[] pre;
 private int cnt;            // preorder counter
 private int bridges;        // number of bridge edges found
 private int cc;             // number of connected components

 public Bridge(Graph G) {
     low = new int[G.V()];
     pre = new int[G.V()];
     for (int v = 0; v < G.V(); v++) low[v] = -1;
     for (int v = 0; v < G.V(); v++) pre[v] = -1;

     for (int v = 0; v < G.V(); v++)
         if (pre[v] == -1) {
             ++cc;
             dfs(G, v, v);
         }
 }

 // The number of bridge edges:
 public int bridges() { return bridges; }

 // The number of 2-edge-connected components in the graph.
 public int components() { return bridges + cc; }

 private void dfs(Graph G, int u, int v) {
     pre[v] = cnt++;
	 StdOut.println("pre[" + v + "] is " + pre[v]);
     low[v] = pre[v];
     for (int w : G.adj(v)) {
         if (pre[w] == -1) {
             dfs(G, v, w);
             low[v] = Math.min(low[v], low[w]);
             if (low[w] == pre[w]) {
                 StdOut.println(v + "-" + w + " is a bridge");
                 bridges++;
             }
         }

         // update low number - ignore reverse of edge leading to v
         else if (w != u)
             low[v] = Math.min(low[v], pre[w]);
     }
 }

 public static void main(String[] args) {
     Graph G = null;
     if (args.length == 1) {
         G = new Graph(new In(args[0]));
     } else {
         int V = Integer.parseInt(args[0]);
         int E = Integer.parseInt(args[1]);
         G = new Graph(V, E);
         StdOut.println(G);
     }
     Bridge bridge = new Bridge(G);
     StdOut.println("# 2-edge-connected components = " + bridge.components());
 }
}

