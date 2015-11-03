import akka.actor.*;
import akka.event.*;

public class testSlave extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(int num) {
        return Props.create(testSlave.class, num);
    }

    public static class Initialize {
    }

    private int num;

    public testSlave(int num) {
        this.num = num;
    }

    private ActorRef master = getContext().actorOf(testMaster.props(), "theMaster");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof testSlave.Initialize) {
            log.info("Let's get started! Send an Integer (" + this.num + ") to Master!");
            master.tell(this.num, getSelf());
        } else if (message instanceof String) {
            log.info((String) message);
        } else {
            unhandled(message);
        }
    }
}
