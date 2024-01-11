public class Dia {
    private String nome;
    private String max;
    private String min;
    private String clima;
    
    public Dia(String nome, String max, String min, String clima) {
        this.nome = nome;
        this.max = max;
        this.min = min;
        this.clima = clima;
    }

    public String getNome() {
        return nome;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }

    public String getClima() {
        return clima;
    }
   
}
