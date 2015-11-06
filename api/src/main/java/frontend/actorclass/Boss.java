package frontend.actorclass;

import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Boss extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(Boss.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof String) {

            if (((String) message).equals("Look up remote!")) {

                ActorSelection backendBoss
                        = context()
                        .actorSelection("akka.tcp://asd-crawler-backend@noel.plsm.cs.nccu.edu.tw:1688/user/Boss");

                backendBoss.tell("from remote", getSelf());

            } else if (((String) message).startsWith("What")) {

                log.info("Remote Boss didn't know what I was saying ...");

            } else if (((String) message).equals("You go kill yourself")) {

                getContext().system().shutdown();

            } else {

                log.info("Not available message");

            }

        } else {
            unhandled(message);
        }
    }

    @Override
    public void postStop() {
        log.info("I am stopped by remote Boss ...");
    }
}
