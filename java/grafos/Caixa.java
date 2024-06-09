import java.util.Arrays;

public class Caixa implements Comparable<Caixa>{
    int dimensoes[] = new int[3];

    public Caixa(int altura, int largura, int profundidade)
    {
      this.dimensoes[0] = altura;
      this.dimensoes[1] = largura;
      this.dimensoes[2] = profundidade;
      Arrays.sort(this.dimensoes);
    }

    public boolean fitsInside(Caixa o) {
      return this.dimensoes[0] < o.dimensoes[0] && this.dimensoes[1] < o.dimensoes[1] && this.dimensoes[2] < o.dimensoes[2];
    }

    @Override
    public String toString() {
        return "\"" + dimensoes[0] + " " + dimensoes[1] + " " + dimensoes[2] + "\"" ;
    }

    @Override
    public int compareTo(Caixa o) {
      return this.dimensoes[0]*this.dimensoes[1]*this.dimensoes[2] - o.dimensoes[0]*o.dimensoes[1]*o.dimensoes[2] ;
    }
  }
