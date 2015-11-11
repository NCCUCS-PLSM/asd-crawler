package backend.actorclass;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(MainWindow.class);
    }

    public MainWindow() {
        this.createContactWindows = new ArrayList<>();
        this.readContactWindows = new ArrayList<>();
        this.updateContactWindows = new ArrayList<>();
        this.deleteContactWindows = new ArrayList<>();
    }

    final private List<ActorRef> createContactWindows;
    final private List<ActorRef> readContactWindows;
    final private List<ActorRef> updateContactWindows;
    final private List<ActorRef> deleteContactWindows;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {

            log.info("Got " + (String) message);

            switch ((String) message) {

                case "open CREATE": {
                    ActorRef tmp = getContext().actorOf(ContactWindow.props("CREATE"));
                    createContactWindows.add(tmp);
                    tmp.tell("initialize", getSender());
                }
                break;

                case "open READ": {
                    ActorRef tmp = getContext().actorOf(ContactWindow.props("READ"));
                    readContactWindows.add(tmp);
                    tmp.tell("initialize", getSender());
                }
                break;

                case "open UPDATE": {
                    ActorRef tmp = getContext().actorOf(ContactWindow.props("UPDATE"));
                    updateContactWindows.add(tmp);
                    tmp.tell("initialize", getSender());
                }
                break;

                case "open DELETE": {
                    ActorRef tmp = getContext().actorOf(ContactWindow.props("DELETE"));
                    deleteContactWindows.add(tmp);
                    tmp.tell("initialize", getSender());
                }
                break;

                default:
                    break;
            }
        }
    }
}
