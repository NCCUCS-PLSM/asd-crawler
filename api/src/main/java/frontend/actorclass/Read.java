package frontend.actorclass;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import frontend.main;
import frontend.taskclass.Task;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class Read extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(String request, Task task) {
        return Props.create(Read.class, request, task);
    }

    public Read(String request, Task task) {
        this.methodType = "READ";
        this.futureContainer = task;
        this.request = request;
    }

    private String methodType;
    private Task futureContainer;
    private String request;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {

            switch ((String) message) {
                case "initialize":
                    main.foreman.tell("open " + this.methodType, getSelf());

                    break;

                case "contact window":
                    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
                    Future<Object> answer = Patterns.ask(getSender(), this.request, timeout);
                    futureContainer.setFuture(answer);

                    break;

                default:

                    log.info((String) message);
                    break;
            }

        } else {
            unhandled(message);
        }
    }
}
