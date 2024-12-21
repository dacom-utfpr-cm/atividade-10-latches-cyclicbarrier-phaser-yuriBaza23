package com.yb.progconc.threads;

public class Sequencial {
    public static void main(String[] args) {
        int N = 10; // Tamanho do vetor
        double[] vetor = new double[N];
        double[] resultado = new double[N];

        // Inicializando o vetor com alguns valores
        for (int i = 0; i < N; i++) {
            vetor[i] = i + 1;
        }

        // Realiza 10 iterações
        for (int iter = 0; iter < 10; iter++) {
            // Atualiza o vetor de resultados baseado no stencil
            for (int i = 1; i < N - 1; i++) {
                resultado[i] = (vetor[i - 1] + vetor[i + 1]) / 2.0;
            }

            // Troca os vetores para a próxima iteração
            System.arraycopy(resultado, 0, vetor, 0, N);
        }

        // Exibe o resultado final
        for (int i = 0; i < N; i++) {
            System.out.println(vetor[i]);
        }
    }
}

