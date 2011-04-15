
package editor;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import math.Quaternion;
import actor.Actor;

public class ActorTable extends AbstractTableModel {
    private static final long serialVersionUID = 6316570941372364566L;
    private static String[] COLUMN_NAMES = {"Type", "Position", "Velocity", "Rotation", "Angular Velocity", "Model"};
    
    private Vector<Actor> actors;

    public ActorTable(Vector<Actor> actors) {
        this.actors = actors;
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    public int getRowCount() {
        return actors.size();
    }
    
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 3:
            case 4:
                return Quaternion.class;
            case 5:
                // return a.getModel();
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Actor a = actors.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                return a.getClass().toString();
            case 1:
                return a.getPosition();
            case 2:
                return a.getVelocity();
            case 3:
                return a.getRotation();
            case 4:
                return a.getAngularVelocity();
            case 5:
                // return a.getModel();
            default:
                return "UNKNOWN";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return false;
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub

    }

}
