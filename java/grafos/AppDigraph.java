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
      }
    }
    System.out.println(path);
  }

  public static void main(String[] args) throws Exception {

    ArrayList<Caixa> caixas = new ArrayList<>();
    // LE CAIXAS DO ARQUIVO TXT
    File file = new File("teste10.txt");
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
    System.out.println("fim");

    //O MAIOR CAMINHO NECESSARIAMENTE COMEÇARÁ EM UMA CAIXA VAZIA
    
    //TER O CONJUNTO DE CAIXAS CHEIAS
    Set<String> caixaCheia = new HashSet<>();
    for (List<String> valores: g.getValues()) {
      caixaCheia.addAll(valores);
    }
    System.out.println("fim");


    //TODO: partir do começo da lista topologica e ir colocando uma caixa dentro da outra
    // EX:
    // 1,2,3,4,5,6,7
    // 1 cabe em 2
    // 2 nao cabe em 3
    // 2 cabe em 4
    // 4 nao cabe em 5
    // 4 cabe em 6
    // 6 cabe em 7
    // 7 nao cabe em niguem
    // caminho final = 1,2,4,6,7
    Topological tp = new Topological(g);
    for (String string : tp.getTopological()) {
      System.out.println(string);
    }

    // for (String v : g.getVerts()) {
    //   //SOMENTE PROCURAR SE A CAIXA É VAZIA
    //   if (!caixaCheia.contains(v)) {
    //     Stack<String> path = new Stack<>();
    //     path.add(v);
    //     geraPath(g, v, path);
    //   }
    // }
    // System.out.println("fim");
    
    //System.out.println(g.toDot());
    //System.out.println(maxDistPath);
  }
}
