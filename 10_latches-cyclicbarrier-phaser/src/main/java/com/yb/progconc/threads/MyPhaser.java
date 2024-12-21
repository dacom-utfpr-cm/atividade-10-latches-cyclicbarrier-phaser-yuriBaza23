package com.yb.progconc.threads;

import java.util.concurrent.*;

public class MyPhaser {
    public static void main(String[] args) throws InterruptedException {
        int N = 10;  // Tamanho do vetor
        int K = 4;   // Número de tarefas (threads)
        double[] vetor = new double[N];
        double[] resultado = new double[N];

        // Inicializando o vetor com alguns valores
        for (int i = 0; i < N; i++) {
            vetor[i] = i + 1;
        }

        ExecutorService pool = Executors.newFixedThreadPool(K);
        Phaser phaser = new Phaser(K);  // Phaser com K participantes (threads)

        // Realiza 10 iterações
        for (int iter = 0; iter < 10; iter++) {
            // Divide o vetor em K partes e cria K tarefas
            for (int i = 0; i < K; i++) {
                final int start = (i * N) / K;
                final int end = ((i + 1) * N) / K;

                pool.submit(() -> {
                    for (int j = start + 1; j < end - 1; j++) {
                        resultado[j] = (vetor[j - 1] + vetor[j + 1]) / 2.0;
                    }

                    // Espera pelas threads adjacentes
                    phaser.arriveAndAwaitAdvance();
                });
            }

            // A thread principal espera até que todas as threads completem a iteração
            phaser.arriveAndAwaitAdvance();

            // Troca os vetores para a próxima iteração
            System.arraycopy(resultado, 0, vetor, 0, N);
        }

        // Exibe o resultado final
        for (int i = 0; i < N; i++) {
            System.out.println(vetor[i]);
        }

        pool.shutdown();
    }
}
