package ru.spbstu.telematics.gavrilov.lab04;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.concurrent.*;

public class MatrixCalc {

    public static class Parallel {
        public static RealMatrix multiply(final RealMatrix A, final RealMatrix B){
            ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            int M = A.getRowDimension();
            int N = B.getColumnDimension();
            final Future<double[]>[] F = new Future[M];
            double[][] R = new double[M][N];

            for (int i = 0; i<M; ++i){
                int processingRow = i;
                F[i] = es.submit(new Callable<double[]>() {
                    @Override
                    public double[] call() throws Exception {
                        double[] r = A.getRow(processingRow);
                        double[] result = new double[N];
                        for (int i=0; i<N; ++i) {
                            double[] c = B.getColumn(i);
                            for (int k=0; k<r.length; ++k)
                                result[i] += r[k]*c[k];
                        }
                        return result;
                    }
                });
            }

            for(int i=0; i<M; ++i)
                try {
                    R[i] = F[i].get();
                } catch (ExecutionException | InterruptedException iee){iee.printStackTrace();}
            es.shutdown();
            return new Array2DRowRealMatrix(R);
        }
    }

    public static class Serial{
        public static RealMatrix multiply(final RealMatrix A, final RealMatrix B){
            int M = A.getRowDimension();
            int N = B.getColumnDimension();
            double[][] R = new double[M][N];

            for (int i = 0; i<M; ++i) {
                double[] curRow = A.getRow(i);
                for (int j = 0; j < N; ++j) {
                    double[] curCol = B.getColumn(j);
                    for (int k=0; k < curRow.length; ++k)
                        R[i][j] += curRow[k] * curCol[k];
                }
            }

            return new Array2DRowRealMatrix(R);
        }
    }
}
