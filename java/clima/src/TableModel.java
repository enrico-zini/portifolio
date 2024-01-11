import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel{
    
    private ArrayList<Dia> previsoes = new ArrayList<>();
    private String[] colunas;

    public TableModel(ArrayList<Dia>previsoes) throws IOException {
        this.previsoes = previsoes;
        
        LinkedList<String> aux = new LinkedList<>();
        previsoes.stream().forEach(p -> aux.add(p.getNome()));
        this.colunas = aux.toArray(new String[0]);
    }

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }

    @Override
    public int getRowCount() {
        return 4;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (rowIndex) {
            case 0 : return previsoes.get(columnIndex).getNome();
            case 1 : return previsoes.get(columnIndex).getMax();
            case 2 : return previsoes.get(columnIndex).getMin();
            case 3 : return previsoes.get(columnIndex).getClima();
            default : return null;
        }
    }
    
}
