package frontend.actorclass;

import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class rootActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(rootActor.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof String) {

            if (((String) message).equals("Look up remote!")) {

                ActorSelection remoteRoot
                        = context()
                        .actorSelection("akka.tcp://asd-crawler-backend@noel.plsm.cs.nccu.edu.tw:1688/user/back-root");

                remoteRoot.tell("from remote", getSelf());

            } else if (((String) message).startsWith("What did you say, ")) {

                log.info("Remote root didn't know what I was saying ...");

            } else if (((String) message).equals("stop")) {

                getContext().stop(getSelf());
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
        log.info("I am stopped by remote root ...");
    }
}
