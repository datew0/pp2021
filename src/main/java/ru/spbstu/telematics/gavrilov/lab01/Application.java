package ru.spbstu.telematics.gavrilov.lab01;

import java.util.Scanner;

class Application {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int uuidCount = 0;
        while(uuidCount <= 0) {
            System.out.println("How much UUIDs do You need?");
            uuidCount = s.nextInt();
            System.out.println(uuidCount>0 ? "[INFO] Generating " + uuidCount + " UUIDs...":"[WARNING] Value must be positive");
        }
        while (uuidCount-- > 0) {
                System.out.println(Uuid.generateUuid());
        }
    }
}
