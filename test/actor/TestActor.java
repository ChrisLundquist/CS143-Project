package actor;

public class TestActor extends Actor {
    private static final long serialVersionUID = -4653206581455126209L;

    public TestActor(math.Vector3f position) {
        super();
        this.position = position;
    }

    public TestActor() {
        super();
    }

    @Override
    public void handleCollision(Actor other) {
    }
}
