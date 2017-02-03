package hw3;

public class P4 {
	public static void main(String[] args){
		int V = 1000;
		int E = 1000;
		double compSum = 0;
		double maxSum = 0;
		for(int i = 0; i < 10000; i++){
			Graph g = new Graph(V, E);
			CC cc = new CC(g);
			int M = cc.count();
			compSum = compSum + M;
	        //StdOut.println(M + " components");

	        // compute list of vertices in each connected component
	        int max = 0;
	        /*Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
	        for (int j = 0; j < M; j++) {
	            components[j] = new Queue<Integer>();
	        }
	        for (int v = 0; v < g.V(); v++) {
	            components[cc.id(v)].enqueue(v);
	        }
	        for (int k = 0; k < components.length; k++){
	        	if(max < components[k].size())
		        	   max = components[k].size();
	        }
	        maxSum = maxSum + max;
	        */
	        for (int k = 0; k < cc.count(); k++){
	        	int test = cc.size(k);
	        	if(max < test)	max = test;
	        }
	        maxSum = maxSum + max;
		}
	double ansSum = compSum / 10000;
	double ansMax = maxSum / 10000;
	System.out.println(ansSum);
	System.out.println(ansMax);
	}
	
	
}
