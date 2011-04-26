package sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class Recycler<Type> extends ArrayList<Type> {

    private static final long serialVersionUID = -1765026184825462457L;
    
    public Type grab(){
        Type pulled = this.get(0);
        this.add(pulled);
        this.remove(0);
        return pulled;
    }
}
