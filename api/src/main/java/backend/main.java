package backend;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import backend.actorclass.MainWindow;
import backend.actorclass.Master;
import com.typesafe.config.ConfigFactory;

public class main {

    public static ActorRef mainwindow;

    public static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("asd-crawler-backend", ConfigFactory.load("backend"));

        final ActorRef master = system.actorOf(Master.props(), "master");
        mainwindow = system.actorOf(MainWindow.props(), "main-window");

        system.awaitTermination();
    }
}
