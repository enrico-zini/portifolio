import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class AppDigraph {

  public static int maxDist = 0;
  static List<String> maxDistPath;

  public static void geraPath(Digraph g, String v, Stack<String> path) 
  {
    // int dist = distTo.get(v);
    if (g.getAdj(v) != null) {
      for (String w : g.getAdj(v)) {
        path.add(w);
        geraPath(g, w, path);
        path.pop(); 
      }
    } else {
      if (path.size() > maxDist) {
        maxDist = path.size();
        maxDistPath = new ArrayList<>(path);
        System.out.println(maxDist + ": " + maxDistPath);
      }
    }
  }

  public static void main(String[] args) throws Exception {

    ArrayList<Caixa> caixas = new ArrayList<>();
    // LE CAIXAS DO ARQUIVO TXT
    File file = new File("caixas.txt");
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        // System.out.println(line);
        String dimensoes[] = line.split(" ");
        caixas.add(
            new Caixa(Integer.valueOf(dimensoes[0]), Integer.valueOf(dimensoes[1]), Integer.valueOf(dimensoes[2])));
      }
    }
    // System.out.println(caixas);

    // VE QUAIS CAIXAS CABEM DENTRO DE OUTRA
    Digraph g = new Digraph();
    for (Caixa caixa : caixas) {
      for (Caixa caixa2 : caixas) {
        if (caixa.fitsInside(caixa2)) {
          g.addEdge(caixa.toString(), caixa2.toString());
        }
      }
    }

    //O MAIOR CAMINHO NECESSARIAMENTE COMEÇARÁ EM UMA CAIXA VAZIA
    
    //TER O CONJUNTO DE CAIXAS CHEIAS
    Set<String> caixaCheia = new HashSet<>();
    for (List<String> valores: g.getValues()) {
      caixaCheia.addAll(valores);
    }

    
    for (String v : g.getVerts()) {
      //SOMENTE PROCURAR SE A CAIXA É VAZIA
      if (!caixaCheia.contains(v)) {
        Stack<String> path = new Stack<>();
        path.add(v);
        geraPath(g, v, path);
        System.out.println(); 
      }
    }
    System.out.println(maxDistPath);
    System.out.println(g.toDot());
  }
}
