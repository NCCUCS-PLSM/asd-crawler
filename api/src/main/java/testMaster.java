import akka.actor.*;
import akka.event.*;

public class testMaster extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(testMaster.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            log.info(message + " from " + getSender().toString());
            getSender().tell("Ok I receive " + message + " from slave", getSelf());
        } else {
            unhandled(message);
        }
    }
}
