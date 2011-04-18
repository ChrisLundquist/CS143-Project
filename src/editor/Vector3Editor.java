package editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import math.Vector3;

public class Vector3Editor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private static final long serialVersionUID = 1026524672232357031L;
    protected static final String EDIT = "edit";

    Vector3 vector3;
    JButton button;

    public Vector3Editor() {
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
            String x_string, y_string, z_string;

            x_string = JOptionPane.showInputDialog("X");
            y_string = JOptionPane.showInputDialog("Y");
            z_string = JOptionPane.showInputDialog("Z");

            Float x = new Float(x_string);
            Float y = new Float(y_string);
            Float z = new Float(z_string);

            vector3 = new Vector3();
            vector3.x = x;
            vector3.y = y;
            vector3.z = z;

            /*
            vector3.timesEquals(new Quaternion(quaternion.rollAxis(), rollDegrees));
            quaternion.timesEquals(new Quaternion(quaternion.pitchAxis(), pitchDegrees));
            quaternion.timesEquals(new Quaternion(quaternion.yawAxis(), yawDegrees));*/

            //  fireEditingStopped(); //Make the renderer reappear.
        } else { //User pressed dialog's "OK" button.
            // currentColor = colorChooser.getColor();
        }
    }

    public Object getCellEditorValue() {
        return vector3;
    }

    public Component getTableCellEditorComponent(JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {

        vector3 = (Vector3)value;
        button.setText(vector3.toString());
        return button;
    }
}
