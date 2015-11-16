package frontend;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.OnSuccess;
import com.typesafe.config.ConfigFactory;
import frontend.actorclass.Create;
import frontend.actorclass.Foreman;
import frontend.actorclass.Read;
import frontend.taskclass.CreateTask;
import frontend.taskclass.ReadTask;
import frontend.taskclass.BasicTask;

public class main {

    public static ActorRef foreman;

    public static String backendPath;

    public static class PrintResult<T> extends OnSuccess<T> {
        @Override
        public void onSuccess(T t) {
            System.out.println(t + "");
        }
    }

    public static void main(String[] args) throws Exception {

        backendPath = "akka.tcp://asd-crawler-backend@127.0.0.1:1688";

        final ActorSystem system = ActorSystem.create("asd-crawler-frontend", ConfigFactory.load("frontend"));
        foreman = system.actorOf(Foreman.props(), "foreman");

        BasicTask createTask = new CreateTask();
        ActorRef create = system.actorOf(Create.props("THIS IS A REQ", createTask), "create");
        create.tell("initialize", null);

        BasicTask readTask = new ReadTask();
        ActorRef read = system.actorOf(Read.props("THIS IS A READ REQ", readTask), "read");
        read.tell("initialize", null);

//        createTask.getFuture()
//                .onSuccess(new PrintResult<>(), system.dispatcher());
//
//        readTask.getFuture()
//                .onSuccess(new PrintResult<>(), system.dispatcher());

        system.awaitTermination();
    }
}
