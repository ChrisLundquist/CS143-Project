package utils;
import java.util.Stack;


public class Cycle<Item> extends Stack<Item>{
    private static final long serialVersionUID = 4163616028113634433L;
    
    public Item recycle(){
        Item element = (Item) this.pop();
        this.add(0, element);
        return element;
    }
}