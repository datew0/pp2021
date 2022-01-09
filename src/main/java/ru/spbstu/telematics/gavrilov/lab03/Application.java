package ru.spbstu.telematics.gavrilov.lab03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Random;

public class Application {
    public static final long MT = 100;

    public static void main(String[] args) {
        String[] names = {"Joe", "Bob"};
        try {
            names = Files.readString(Paths.get("resources","names.txt")).split("\\r?\\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        final Window w = new Window();
        final TicketMachine tm = new TicketMachine(w);

        final Random rand = new Random();
        final LinkedList<Visitor> vList = new LinkedList<>();
        for(int i=0; i<120; ++i){
            Visitor v = new Visitor(tm);
            vList.add(v);
            v.setName(names[rand.nextInt(names.length - 1)]);
            v.start();
        }

        for (Visitor v: vList){
            try {
                v.join();
            } catch (InterruptedException ie){ie.printStackTrace();}
        }
    }
}
