package ru.spbstu.telematics.gavrilov.lab03;

import java.util.Random;

public class Window {
    Display display = new Display();

    private class Display{
        private volatile long nowProcessing = 1;

        private synchronized void inviteNext(){
            if (++nowProcessing > Application.MT)
                nowProcessing = 1;
            notifyAll();
        }
    }

    public boolean service(TicketMachine.Ticket t){
        if (t.getId() != display.nowProcessing || t.getDest() != this) {
            System.out.println(Thread.currentThread().getName() + ": wrong number or window " + t.getId());
            return false;
        }
        Random rand = new Random();
        try {
            Thread.sleep(rand.nextInt(200)); // Service delay
        } catch (InterruptedException ie) {ie.printStackTrace();}
        System.out.println(Thread.currentThread().getName() + " successfully serviced, id: " + t.getId());
        display.inviteNext();
        return true;
    }

    public boolean stayInQueue(TicketMachine.Ticket t){
        if (t.getDest() != this) return false;
        synchronized (display){
            while (display.nowProcessing != t.getId()){
                try {
                    display.wait();
                } catch (InterruptedException ie) {ie.printStackTrace();}
            }
        }
        return true;
    }
}
