package frontend.actorclass;

import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import frontend.main;

public class Foreman extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(Foreman.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof String) {
            if (((String) message).startsWith("open ")) {

                ActorSelection backendMaster
                        = context()
                        .actorSelection(main.backendPath + "/user/master");

                backendMaster.tell(message, getSender());
            }
        } else {
            unhandled(message);
        }
    }
}
