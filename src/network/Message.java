package network;

import java.io.Serializable;
import java.util.Collection;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = -7784455849848939000L;
   

    void apply(Collection<actor.Actor> actors) {
         
    }
}
