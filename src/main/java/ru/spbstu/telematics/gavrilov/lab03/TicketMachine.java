package ru.spbstu.telematics.gavrilov.lab03;

public class TicketMachine {
    private final Window window;

    private volatile long nextId = 1;
    private volatile long queueLength = 0;
    private final Object tapeLock = new Object();

    public class Ticket {
        final private Window dest;
        final private long id;

        public Ticket(long id, Window destination) {
            this.id = id;
            this.dest = destination;
        }

        public Window getDest(){
            return this.dest;
        }
        public long getId(){
            return this.id;
        }
    }

    public TicketMachine(Window w){
        window = w;
    }

    public Ticket register(){
        long id;
        synchronized (tapeLock){
            while (queueLength >= Application.MT) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting for ticket...");
                    tapeLock.wait();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            id = nextId;
            if (++nextId > Application.MT)
                nextId = 1;
            ++queueLength;
        }
        System.out.println(Thread.currentThread().getName() + " registered with id: " + id);
        return new Ticket(id,window);
    }

    public void leave(){
        System.out.println(Thread.currentThread().getName() + " left the hall");
        synchronized (tapeLock){
            --queueLength;
            tapeLock.notify();
        }
    }
}
