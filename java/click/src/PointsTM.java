import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

public class PointsTM extends AbstractTableModel {
    private LinkedList<MyPoint> l;
    private String[] colunas = { "X", "Y" };

    public PointsTM(LinkedList<MyPoint> l) {
        this.l = l;
    }

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }

    @Override
    public int getRowCount() {
        return l.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return l.get(rowIndex).getX();
            case 1:
                return l.get(rowIndex).getY();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        String s = value.toString();
        if (columnIndex == 0) {
            MyPoint aux = l.get(rowIndex);
            aux.setX(s);
            l.set(rowIndex, aux);
        } else {
            MyPoint aux2 = l.get(rowIndex);
            aux2.setY(s);
            l.set(rowIndex, aux2);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
