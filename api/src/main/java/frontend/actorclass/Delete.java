package frontend.actorclass;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.Await;
import scala.concurrent.Future;

import static akka.pattern.Patterns.ask;

public class Delete extends UntypedActor implements CRUD {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(String request) {
        return Props.create(Delete.class, request);
    }

    public Delete(String request) {
        this.methodType = "DELETE";
        this.request = request;
        this.response = null;
        this.contactWindow = null;
    }

    private String methodType;

    private String request;
    private Future response;

    private ActorRef contactWindow;

    public String getMethodType() {
        return this.methodType;
    }

    public String getRequest() {
        return this.request;
    }

    public Future getResponseFuture() {
        return this.response;
    }

    public String getResponseContent() throws Exception {
        return (String) Await.result(this.response, this.timeout.duration());
    }

    @Override
    public ActorRef getBackendContactWindow() {
        return this.contactWindow;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {

            switch ((String) message) {
                case "call backend main window":
                    ActorSelection backendMainWindow
                            = context()
                            .actorSelection(this.backendMainWindow);

                    backendMainWindow.tell("open " + this.methodType, getSelf());

                    break;

                case "here comes the contact window":
                    this.contactWindow = getSender();
                    this.response = ask(this.contactWindow, this.request, this.timeout);

                    log.info("got contact window " + this.contactWindow.toString());

                    break;

                default:

                    break;
            }

        } else {
            unhandled(message);
        }
    }
}
