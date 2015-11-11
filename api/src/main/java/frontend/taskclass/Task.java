package frontend.taskclass;

import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

import scala.concurrent.duration.Duration;

public class Task {

    protected Future<Object> future;
    protected boolean ready;

    public Task() {
        this.future = null;
        this.ready = false;
    }

    public void checkidle() throws InterruptedException {
        while (!this.ready) {
            Thread.sleep(0);
        }
    }

    public Future getFuture() throws InterruptedException {
        checkidle();
        return this.future;
    }

    public Object getResult() throws Exception {
        checkidle();
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        return Await.result(future, timeout.duration());
    }

    public void setFuture(Future<Object> future) {
        this.future = future;
        this.ready = true;
    }
}
