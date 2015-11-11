package backend.actorclass;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import backend.main;

public class Master extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(Master.class);
    }

    public Master() {

    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof String) {
            if (((String) message).startsWith("open ")) {
                main.mainwindow.tell(message, getSender());
            }
        } else {
            unhandled(message);
        }

    }
}
