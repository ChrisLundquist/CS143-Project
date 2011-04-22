package sound.gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

class MutableList extends JList {
    private static final long serialVersionUID = 9016699698540913020L;
    MutableList() {
    super(new DefaultListModel());
    }
    DefaultListModel getContents() {
    return (DefaultListModel)getModel();
    }
}