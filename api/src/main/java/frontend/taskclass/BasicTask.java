package frontend.taskclass;

import scala.concurrent.Future;

public class BasicTask {

    protected Future<Object> future;
    protected boolean ready;

    public BasicTask() {
        this.future = null;
        this.ready = false;
    }

    private void isReady() throws InterruptedException {
        while (!this.ready) {
            Thread.sleep(0);
        }
    }

    public Future getFuture() throws InterruptedException {
        this.isReady();
        return this.future;
    }

    public void setFuture(Future<Object> future) {
        this.future = future;
        this.ready = true;
    }
}
