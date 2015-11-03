import akka.actor.*;
import akka.event.*;

public class testSlave extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(int num, ActorRef master) {
        return Props.create(testSlave.class, num, master);
    }

    public static class Initialize {
    }

    private int num;
    private ActorRef master;

    public testSlave(int num, ActorRef master) {
        this.num = num;
        this.master = master;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof testSlave.Initialize) {
            log.info("Let's get started! Send an Integer (" + this.num + ") to Master!");
            master.tell(this.num, getSelf());
        } else if (message instanceof String) {
            log.info("My master said : \"" + message + "\"");
            getContext().stop(getSelf());
        } else {
            unhandled(message);
        }
    }
}
