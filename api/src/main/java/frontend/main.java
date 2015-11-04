package frontend;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import frontend.actorclass.rootActor;

public class main {

    public static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("asd-crawler-frontend", ConfigFactory.load("frontend"));
        ActorRef root = system.actorOf(rootActor.props(), "front-root");

        root.tell("Look up remote!", null);

        system.awaitTermination();
    }
}
