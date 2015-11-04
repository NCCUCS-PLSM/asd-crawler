package backend;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import backend.actorclass.rootActor;
import com.typesafe.config.ConfigFactory;

public class main {

    public static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("asd-crawler-backend", ConfigFactory.load("backend"));
        final ActorRef root = system.actorOf(rootActor.props(), "back-root");

        root.tell(new rootActor.Initialize(), null);

        system.awaitTermination();
    }
}
