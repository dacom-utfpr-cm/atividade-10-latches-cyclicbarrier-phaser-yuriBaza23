package com.yb.progconc.threads;

import java.util.concurrent.*;

public class Latch {
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

        // Realiza 10 iterações. A cada iteração há
        // a criação do latch. Após a criação do latch,
        // acontece a divisão do vetor em K partes para a
        // criação de K tarefas. Ao fim, há a notificação
        // de que a tarefa foi concluida, a espera
        // até as tarefas terminarem e a troca dos vetores.
        for (int iter = 0; iter < 10; iter++) {
            CountDownLatch latch = new CountDownLatch(K);

            for (int i = 0; i < K; i++) {
                final int start = (i * N) / K;
                final int end = ((i + 1) * N) / K;

                pool.submit(() -> {
                    for (int j = start + 1; j < end - 1; j++) {
                        resultado[j] = (vetor[j - 1] + vetor[j + 1]) / 2.0;
                    }
                    latch.countDown();
                });
            }

            latch.await();

            System.arraycopy(resultado, 0, vetor, 0, N);
        }

        // Exibe o resultado final
        for (int i = 0; i < N; i++) {
            System.out.println(vetor[i]);
        }

        pool.shutdown();
    }
}
