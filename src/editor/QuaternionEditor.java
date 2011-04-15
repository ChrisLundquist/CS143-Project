package editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import math.Quaternion;

public class QuaternionEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private static final long serialVersionUID = 1026524672232357031L;
    protected static final String EDIT = "edit";

    Quaternion quaternion;
    JButton button;

    public QuaternionEditor() {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            System.err.println("Editing cell");
            
            //The user has clicked the cell, so
            //bring up the dialog.
            
            // dialog.setVisible(true);
            String rollDegrees_string, pitchDegreess_string, yawDegrees_string;
            
            rollDegrees_string =  JOptionPane.showInputDialog("Roll Degrees");
            pitchDegreess_string = JOptionPane.showInputDialog("Pitch Degrees");
            yawDegrees_string = JOptionPane.showInputDialog("Yaw Degrees");
            
            Float rollDegrees = new Float(rollDegrees_string);
            Float pitchDegrees = new Float(pitchDegreess_string);
            Float yawDegrees = new Float(yawDegrees_string);
            
            quaternion = new Quaternion();
            quaternion.timesEquals(new Quaternion(quaternion.rollAxis(), rollDegrees));
            quaternion.timesEquals(new Quaternion(quaternion.pitchAxis(), pitchDegrees));
            quaternion.timesEquals(new Quaternion(quaternion.yawAxis(), yawDegrees));
            
            fireEditingStopped(); //Make the renderer reappear.
        } else { //User pressed dialog's "OK" button.
            // currentColor = colorChooser.getColor();
        }
    }

    public Object getCellEditorValue() {
        return quaternion;
    }

    public Component getTableCellEditorComponent(JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {
        
        quaternion = (Quaternion)value;
        button.setText(quaternion.toString());
        return button;
    }
}
