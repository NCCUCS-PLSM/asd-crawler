package practice;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.ArrayList;
import java.util.List;

public class testMain {

    public static int theTotalNumber = 10000;

    public static void main(String args[]) {
        ActorSystem system = ActorSystem.create("testSystem");
        ActorRef master = system.actorOf(testMaster.props(), "theMaster");

        List<ActorRef> slaves = new ArrayList<>();
        for (int i = 0; i < theTotalNumber; i++) {
            slaves.add(system.actorOf(testSlave.props(i + 1, master), "theSlave_" + (i + 1)));
        }

        slaves.forEach(slave -> {
            slave.tell(new testSlave.Initialize(), null);
        });

        system.awaitTermination();
    }
}
