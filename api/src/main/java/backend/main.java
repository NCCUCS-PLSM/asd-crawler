package backend;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import backend.actorclass.MainWindow;
import com.typesafe.config.ConfigFactory;

public class main {

    public static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("asd-crawler-backend", ConfigFactory.load("backend"));
//        final ActorRef boss = system.actorOf(Boss.props(), "boss");

        final ActorRef mainWindow = system.actorOf(MainWindow.props(), "main-window");

//        boss.tell(new Boss.Initialize(), null);

        system.awaitTermination();
    }
}
