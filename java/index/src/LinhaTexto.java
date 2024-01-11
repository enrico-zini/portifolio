/**
 * @author Isabel H. Manssour
 */
public class LinhaTexto {
    private String linha;
    private String palavras[];
    private int contPalavras;
    
    /**
     * Recebe a string da linha que sera armazenada.
     * @param lin String com a linha de texto
     */
    public void setLine(String lin) {
        linha = lin;
        linha = linha.replaceAll("\\t",""); // substitui tab por espaco em branco
        linha = linha.replaceAll(",",""); // para remover vírgulas
        linha = linha.replaceAll("\"",""); // para remover aspas duplas
        linha = linha.replaceAll("\'",""); // para remover aspas simples
        linha = linha.replaceAll("0",""); // para remover numero
        linha = linha.replaceAll("1",""); // para remover numero
        linha = linha.replaceAll("2",""); // para remover numero
        linha = linha.replaceAll("3",""); // para remover numero
        linha = linha.replaceAll("4",""); // para remover numero
        linha = linha.replaceAll("5",""); // para remover numero
        linha = linha.replaceAll("6",""); // para remover numero
        linha = linha.replaceAll("7",""); // para remover numero
        linha = linha.replaceAll("8",""); // para remover numero
        linha = linha.replaceAll("9",""); // para remover numero
        linha = linha.replaceAll("\\(",""); // para remover parenteses
        linha = linha.replaceAll("\\)",""); // para remover parenteses
        linha = linha.replaceAll("\\[",""); // para remover colchete
        linha = linha.replaceAll("\\]",""); // para remover colchete
        linha = linha.replaceAll("\\:",""); // para remover dois pontos
        linha = linha.replaceAll("\\;",""); // para remover ponto e virgula
        linha = linha.replaceAll("\\--",""); // para remover dois tracos
        linha = linha.replaceAll("\\.",""); // para remover ponto final
        linha = linha.replaceAll("\\?",""); // para remover ponto interrogacao
        linha = linha.replaceAll("\\!",""); // para remover ponto exclamacao
        palavras = linha.split(" "); // divide a string pelo espaco em branco 
        contPalavras = 0;
    }
    
    /**
     * Retorna uma palavra da linha.
     * @return a palavra, ou null caso nao tenha mais palavras.
     */
    public String getNextWord() {
      String pal = null;
      if (contPalavras < palavras.length) {
          pal = palavras[contPalavras];
          contPalavras++;
      }
      return pal;
    }
}
