package backend.actorclass;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Boss extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(Boss.class);
    }

    public static class Initialize {
    }

    public Boss() {

    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof String) {

            log.info("The message is \"" + message + "\"");

            if (((String) message).equals("from remote")) {

                log.info("Got message from remote " + getSender().toString());
                getSender().tell("You go kill yourself", getSelf());

            } else {

                log.info("What did you say, " + getSender().toString() + "?");
                getSender().tell("What?", getSelf());

            }

        } else if (message instanceof Initialize) {

            log.info("Boss is initialized as " + getSelf().toString());

        } else {

            unhandled(message);

        }

    }
}
