package fun.gengzi.codecopy.business.problemsolve;

public class Task {

    private Thread t;
    private Runnable r;

    public Task(Thread t, Runnable r) {
        this.t = t;
        this.r = r;
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public Runnable getR() {
        return r;
    }

    public void setR(Runnable r) {
        this.r = r;
    }
}
