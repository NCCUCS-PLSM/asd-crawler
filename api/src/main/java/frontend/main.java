package frontend;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import frontend.actorclass.*;

public class main {

    public static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("asd-crawler-frontend", ConfigFactory.load("frontend"));
        final ActorRef boss = system.actorOf(Boss.props(), "Boss");

//        Boss.tell("Look up remote!", null);

        final ActorRef create = system.actorOf(Create.props("req create"), "createActor");
        final ActorRef read = system.actorOf(Read.props("req read"), "readActor");
        final ActorRef update = system.actorOf(Update.props("req update"), "updateActor");
        final ActorRef delete = system.actorOf(Delete.props("req delete"), "deleteActor");

        create.tell("call backend main window", null);
        read.tell("call backend main window", null);
        update.tell("call backend main window", null);
        delete.tell("call backend main window", null);

        system.awaitTermination();
    }
}
