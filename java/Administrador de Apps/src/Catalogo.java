import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import java.util.function.Function;

public class Catalogo<T extends TipoGenerico<T>> {//tipo T tem que implementar o metodo toLineFile()
    private List<T> itens;
    private String path;
    private Function<String,T> fromLineFile;

    public Catalogo(String path, Function<String,T> fromLineFile) {
        itens = new LinkedList<>();
        this.path = path;
        this.fromLineFile = fromLineFile;//recebe o fromLineFile do T especifico
    }
    
    public void cadastra(T p) {
        itens.add(p);
    }

    public void remove(int codigo)
    {
        List<T> nova = new LinkedList<>();
        for (T item : itens) {
            if(item.getCodigo()!=codigo)
            {
                nova.add(item);
            }
        }
        itens.clear();
        itens.addAll(nova);
    }
    public void remove(String cpf)
    {
        List<T> nova = new LinkedList<>();
        for (T Cliente : itens) {
            if(!Cliente.getCpf().equals(cpf))
            {
                nova.add(Cliente);
            }
        }
        itens.clear();
        itens.addAll(nova);
    }

    public T getProdutoNaLinha(int linha) {
        if (linha >= itens.size()) {
            return null;
        }
        return itens.get(linha);
    }

    public int getQtdade() {
        return itens.size();
    }

    public Stream<T> getStream() {
        return itens.stream();
    }

    public void loadFromFile() {
        Path appsFilePath = Path.of(path);
        try (Stream<String> appsStream = Files.lines(appsFilePath)) {
            appsStream.forEach(linha->itens.add(this.fromLineFile.apply(linha)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        Path appsFilePath = Path.of(path);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (T item : itens) {
                writer.println(item.toLineFile());//chama o toLineFile() do tipo especifico em questao
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
