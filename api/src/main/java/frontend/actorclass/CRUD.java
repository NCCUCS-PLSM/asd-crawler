package frontend.actorclass;

import akka.actor.ActorRef;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

public interface CRUD {

    public String backendMainWindow = "akka.tcp://asd-crawler-backend@noel.plsm.cs.nccu.edu.tw:1688/user/main-window";

    public Timeout timeout = new Timeout(Duration.create(10, "seconds"));

    public ActorRef getBackendContactWindow();
}
