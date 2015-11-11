package backend.actorclass;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ContactWindow extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(String methodType) {
        return Props.create(ContactWindow.class, methodType);
    }

    public ContactWindow(String methodType) {
        this.methodType = methodType;
    }

    private String methodType;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            switch ((String) message) {

                case "initialize":
                    getSender().tell("contact window", getSelf());
                    break;

                default:
                    getSender().tell("REQ: " + (String) message + " -> SAMPLE " + this.methodType + " RESPONSE", getSelf());
                    break;
            }
        } else {
            unhandled(message);
        }
    }
}
