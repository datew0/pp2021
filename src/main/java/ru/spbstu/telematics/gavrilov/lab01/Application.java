package ru.spbstu.telematics.gavrilov.lab01;

import java.util.Scanner;

class Application {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("How much UUIDs do You need?");
        int uuidCount = s.nextInt();
        while (uuidCount-- > 0) {
            System.out.println(Uuid.generateUuid());
        }
    }
}
