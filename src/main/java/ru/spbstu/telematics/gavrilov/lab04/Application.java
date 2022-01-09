package ru.spbstu.telematics.gavrilov.lab04;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Random rand = new Random();
        System.out.println("Dim\tSer\tPar\tDelta\tK");
        for (int d=10; d<1000; d+=10){
            double[][] m = new double[d][d];
            for (int i=0; i<d; ++i) {
                for (int j = 0; j < d; ++j)
                    m[i][j] = rand.nextDouble();
            }
            RealMatrix M = new Array2DRowRealMatrix(m);

            Instant serialStart = Instant.now();
            RealMatrix S = MatrixCalc.Serial.multiply(M,M);
            Instant serialEnd = Instant.now();

            Instant parallelStart = Instant.now();
            RealMatrix P = MatrixCalc.Parallel.multiply(M,M);
            Instant parallelEnd = Instant.now();

            long dS = Duration.between(serialStart,serialEnd).toMillis();
            long dP = Duration.between(parallelStart,parallelEnd).toMillis();

            System.out.println(d+"\t"+ dS + "\t" + dP + "\t" + (dS-dP) +"\t" + ((dP != 0)? String.format("%.2f",(double)dS/dP):"-"));
        }
    }
}
