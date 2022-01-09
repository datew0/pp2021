package ru.spbstu.telematics.gavrilov.lab04;


import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class MatrixCalcTest {
    RealMatrix randomA, randomB;
    RealMatrix concreteA, concreteB;

    @Before
    public void init(){
        concreteA = new Array2DRowRealMatrix(new double[][]{
                {1.5,2.6,3.2,9.9},
                {6.9,7,3,4.5},
                {4,1.2,5.6,5.5}
        });
        concreteB = new Array2DRowRealMatrix(new double[][]{
                {4.5,3.1},
                {6.7,7.7},
                {1.4,1.9},
                {7,4.9}
        });

        Random rand = new Random();
        int sumLength = rand.nextInt(1000);
        double[][] a = new double[rand.nextInt(1000)][sumLength];
        double[][] b = new double[sumLength][rand.nextInt(1000)];

        for(int i=0;i<sumLength; ++i){
            for (int j=0; j<a.length;++j)
                a[j][i] = rand.nextDouble();
            for (int j=0; j<b[0].length; ++j)
                b[i][j] = rand.nextDouble();
        }

        randomA = new Array2DRowRealMatrix(a);
        randomB = new Array2DRowRealMatrix(b);
    }
    @Test
    public void testParallel(){
        RealMatrix expected = randomA.multiply(randomB);
        RealMatrix actual = MatrixCalc.Parallel.multiply(randomA,randomB);

        Assert.assertEquals("Wrong result for random matrices",expected,actual);

        expected = concreteA.multiply(concreteB);
        actual = MatrixCalc.Parallel.multiply(concreteA, concreteB);

        Assert.assertEquals("Wrong result for concrete matrices",expected,actual);
    }

    @Test
    public void testSerial(){
        RealMatrix expected = randomA.multiply(randomB);
        RealMatrix actual = MatrixCalc.Serial.multiply(randomA,randomB);

        Assert.assertEquals("Wrong result for random matrices",expected,actual);

        expected = concreteA.multiply(concreteB);
        actual = MatrixCalc.Serial.multiply(concreteA, concreteB);

        Assert.assertEquals("Wrong result for concrete matrices",expected,actual);
    }

    @Test
    public void testPerformance(){
        System.out.println("Rows in result matrix: " + randomA.getColumnDimension());

        Instant start = Instant.now();
        RealMatrix mParallel = MatrixCalc.Parallel.multiply(randomA,randomB);
        Instant end = Instant.now();
        System.out.println("Parallel calculation time: " + Duration.between(start, end).toMillis() + " ms");

        start = Instant.now();
        RealMatrix mSerial = MatrixCalc.Serial.multiply(randomA,randomB);
        end = Instant.now();
        System.out.println("Serial calculation time: " + Duration.between(start, end).toMillis() + " ms");
    }
}
