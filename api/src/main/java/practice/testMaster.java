package practice;

import akka.actor.*;
import akka.event.*;

public class testMaster extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(testMaster.class);
    }

    private int num = 0;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {

            this.num++;

            log.info("Me master received " + message + " from " + getSender().toString());
            getSender().tell("Ok I receive " + message + " from slave", getSelf());

            if (this.num >= testMain.theTotalNumber) {
                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
