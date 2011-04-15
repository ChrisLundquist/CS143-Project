
package editor;

import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import actor.Actor;

public class ActorTable extends AbstractTableModel {
    /**
     * 
     */
    private static final long serialVersionUID = 6316570941372364566L;

    public ActorTable(Vector<Actor> actors) {
        // TODO Auto-generated constructor stub
    }

    public void addTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub

    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getColumnName(int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub

    }

}
