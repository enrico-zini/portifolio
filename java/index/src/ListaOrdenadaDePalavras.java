/**
 * Esta classe guarda as palavra do indice remissivo em ordem alfabetica.
 * @author Isabel H. Manssour
 */

public class ListaOrdenadaDePalavras {

    // Classe interna 
    private class Palavra// como se fosse o Node
    {
        public String s;
        public ListaDeOcorrencias listaOcorrencias;
        public Palavra next;    
        public Palavra(String str) //construtor
        {
            s = str;
            next = null;
            listaOcorrencias = new ListaDeOcorrencias();
        }
        
        // Metodos
        @Override
        public String toString()
        {
            return s.toString()+":"+listaOcorrencias.toString();
        }
    }
    
    // Atributos
    private Palavra head;
    private Palavra tail;
    private int count;
    
    // Metodos
    public void add(String palavra, int numPag)
    {
        if(this.contains(palavra))//se a lista de palavras ja contem a palavra
        {
            if(!this.get(palavra).listaOcorrencias.contains(numPag))//se a lista de paginas nao contem a pagina
            {
                this.get(palavra).listaOcorrencias.add(numPag);   
            }
        }
        else//se a lista ainda nao tem a palavra
        {
            Palavra p = new Palavra(palavra);//cria nova palavra
            p.listaOcorrencias.add(numPag);

            if(head == null)//se a lista estiver vazia
            {
                head = p;
                tail = p;
                count++;
            }
            else if(p.s.compareTo(tail.s)>0)//se p for maior que o ultimo
            {
                tail.next=p;
                tail=p;
            }
            else if(p.s.compareTo(head.s) < 0)//se a p for menor que o primeiro
            {
                p.next = head;
                head = p;
            } 
            else
            {
                Palavra atual = head;
                while(atual.next != null && p.s.compareTo(atual.next.s) > 0)//avanca ate achar palavra maior que p
                {
                    atual = atual.next;
                }
                p.next = atual.next;
                atual.next = p;
                count++;
            }
        }   
    }
    public int size()
    {
        return count;
    }
    public String maisRecorrente()
    {
        Palavra aux = head;
        int maior = aux.listaOcorrencias.size();
        String aux2 = aux.toString();
        while(aux!=null)
        {
            if(aux.listaOcorrencias.size()>maior)
            {
                maior=aux.listaOcorrencias.size();
                aux2=aux.s+":"+aux.listaOcorrencias.toString();
            }
            aux=aux.next;
        }
        return aux2;
    }
    public String get(int index)
    {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        if (index == count-1) // se for para retornar o ultimo
            return tail.toString();
        
        Palavra aux = head;
        for(int i=0; i<index; i++) {
            aux = aux.next;//caminha na lista
        }
        return aux.toString();
    }
    public Palavra get(String palavra)
    {
        Palavra aux = head;
        while(aux != null) //aux sera null quando o next for null(o ultimo nodo)
        {
            if (aux.s.equals(palavra)) {
                return aux;
            }
            aux = aux.next;
        }
        return null;
    }

    public boolean contains(String palavra)
    {
        Palavra aux = head;
        while(aux != null) //aux sera null quando o next for null(o ultimo nodo)
        {
            if (aux.s.equals(palavra)) {
                return true;
            }
            aux = aux.next;
        }
        return false;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        Palavra aux = head;

        while (aux != null) {
            s.append(aux.s.toString()+":"+aux.listaOcorrencias.toString()+".");
            s.append("\n");
            aux = aux.next;
        }
        return s.toString();
    }
}
