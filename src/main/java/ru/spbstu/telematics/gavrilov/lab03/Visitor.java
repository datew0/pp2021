package ru.spbstu.telematics.gavrilov.lab03;

public class Visitor extends Thread{
    TicketMachine tm;
    TicketMachine.Ticket t;

    public Visitor(TicketMachine tm){
        super();
        this.tm = tm;
    }

    @Override
    public void run(){
        t = tm.register();
        Window w = t.getDest();
        w.stayInQueue(t);
        w.service(t);
        tm.leave();
    }
}
