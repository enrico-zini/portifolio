import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BreadthFirst {
    private Map<String, Boolean> marked;
    private Map<String, String> edgeTo;
    private Map<String, Integer> distTo;

    public BreadthFirst() {
        marked = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
    }

    public void bfs(Graph G, String s) {
        int dist = 0;
        Queue<String> q = new LinkedList<>();
        q.add(s);
        marked.put(s, true);
        distTo.put(s, dist);

        while (!q.isEmpty()) {
            String v = q.remove();
            dist++;
            for (String w : G.getAdj(v)) {
                if (!marked.containsKey(w)) {
                    q.add(w);
                    marked.put(w, true);
                    edgeTo.put(w, v);
                    distTo.put(w, dist);
                }
            }
        }

        // Print vertices and their distances from vertex 0
        for (Map.Entry<String, Integer> entry : distTo.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
