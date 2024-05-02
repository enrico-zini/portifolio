
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class DepthFirst {
    private Set<String> marked;
    private Map<String, String> edgeTo;
    private String s;

    public DepthFirst(Graph g, String s) {
        marked = new HashSet<>();
        edgeTo = new HashMap<>();
        this.s = s;
        dfs(g, s);
    }

    private void dfs(Graph g, String v) {
        marked.add(v);
        System.out.println("Visited: " + v);
        for (String w : g.getAdj(v)) {
            if (!marked.contains(w)) {
                edgeTo.put(w, v);
                dfs(g, w);
            }
        }
    }
}

