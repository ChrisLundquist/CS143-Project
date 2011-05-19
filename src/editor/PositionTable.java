package editor;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import math.Vector3f;

public class PositionTable extends AbstractTableModel {
    private static final long serialVersionUID = 3522933175518718194L;
    private static final String[] COLUMN_NAMES = {"id", "x", "y", "z"};
    private Vector<Vector3f> positions;
    
    public PositionTable(Vector<Vector3f> positions) {
        this.positions = positions;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public int getRowCount() {
        return positions.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vector3f p = positions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex;
            case 1:
                return p.x;
            case 2:
                return p.y;
            case 3:
                return p.z;
            default:
                return "INVALID";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            default:
                return false;
        }
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        float value;
        if (aValue instanceof String)
            value = Float.parseFloat((String)aValue);
         else
            return;
        
        Vector3f p = positions.get(rowIndex);
        switch (columnIndex) {
            case 1:
                p.x = value;
                break;
            case 2:
                p.y = value;
                break;
            case 3:
                p.z = value;
                break;
            default:
                return;
        }


    }
}
