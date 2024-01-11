import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe que inicializa a execução da aplicacao.
 * @author Isabel H. Manssour
 */
public class Main 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        ArquivoTexto arquivo = new ArquivoTexto(); // objeto que gerencia o arquivo
        LinhaTexto linha = new LinhaTexto(); // objeto que gerencia uma linha
        String l;

        arquivo.open("StopWords-EN.txt");
        ListaOrdenadaDePalavras stopWords = new ListaOrdenadaDePalavras();//lista que contem as stopwords
        do  // laco que passa em cada linha do arquivo
        {
            l = arquivo.getNextLine();
            if (l==null) // acabou o arquivo?
            break;
            linha.setLine(l); // define o texto da linha
            do // laco que passa em cada palavra de uma linha
            {
                String palavra = linha.getNextWord();// obtem a proxima palavra da linha
                if (palavra == null)// acabou a linha
                {
                    break;
                }
                stopWords.add(palavra, 0);
            } while (true);
        } while (true);
        
        arquivo.close();

        ListaOrdenadaDePalavras lodp = new ListaOrdenadaDePalavras(); 
        
        int totalPalavras=0;
        int totalPalavrasStop=0;
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo");
        String arq = scan.nextLine();

        arquivo.open(arq);
        int nLinha = 0;
        int nPagina = 0;
        do  // laco que passa em cada linha do arquivo
        {
            l = arquivo.getNextLine();
            if (l==null)// acabou o arquivo?
            {
                break; 
            } 
            nLinha++; // conta mais uma linha lida do arquivo
            if (nLinha == 40) // chegou ao fim da pagina?
            {
                nLinha = 0;
                nPagina++;
            }
            linha.setLine(l); // define o texto da linha
            do // laco que passa em cada palavra de uma linha
            {
                String palavra = linha.getNextWord();// obtem a proxima palavra da linha
                if (palavra == null)// acabou a linha
                {
                    break;
                }
                if(!stopWords.contains(palavra.toLowerCase()))
                { 
                    lodp.add(palavra.toLowerCase(), nPagina);
                }
                if(stopWords.contains(palavra.toLowerCase()))
                {
                    totalPalavrasStop++;
                }
                totalPalavras++;
            } while (true);
        } while (true);
        arquivo.close();
        
        double porcentStopWords=((double)totalPalavrasStop/totalPalavras)*100; 
        
        int op=0;
        do 
        {
            System.out.println("[1] Indice remissivo");
            System.out.println("[2] Percentual de StopWords");
            System.out.println("[3] Palavra mais frequente"); 
            System.out.println("[4] Pesquisar palavra"); 
            System.out.println("[5] Encerrar"); 
            op = scan.nextInt();
            switch(op)
            {
                case 1:
                System.out.println(lodp);
                break;
                case 2:
                System.out.println(porcentStopWords);
                break;
                case 3:
                System.out.println(lodp.maisRecorrente());
                break;
                case 4:
                System.out.println("Digite a palavra");
                scan.nextLine();
                System.out.println();
                String p = scan.nextLine();
                System.out.println(lodp.get(p));
                break;
            }
        } 
        while (op!=5);
    }
}