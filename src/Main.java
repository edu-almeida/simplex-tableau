import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean verificaIlimitada = false;
        float[][] valoresTableauUser;
        Scanner scanner = new Scanner(System.in);
        short numeroLinha, numeroColuna;
        System.out.println();

        System.out.println("""
                Este programa requer a função  MAX / Z na sua forma tableau,
                com seus valores já convertidos e com as atribuições das variáveis auxiliares.
                Isso também vale as restrições, que devem estar no seu formato tableau.
                """);

        System.out.println("Número de linhas do seu tableau, incluindo a coluna B (valores) e sem a coluna Z: ");
        numeroLinha = scanner.nextShort();
        System.out.println("Número de colunas do seu tableau, incluindo a coluna B (valores) e sem a coluna Z: ");
        numeroColuna = scanner.nextShort();
        System.out.println();
        valoresTableauUser = new float[numeroLinha][numeroColuna];

        System.out.println("Insira os valores das restrições:");
        for (int i = 0; i < numeroLinha; i++) {
            if (i != numeroLinha - 1) {
                System.out.println("\nRESTRIÇÃO " + (i + 1));
            } else {
                System.out.println("\nVALORES DA RESTRIÇÃO Z:");
            }
            for (int j = 0; j < numeroColuna; j++) {
                System.out.println("Valor " + (j + 1) + " de " + numeroColuna + ":");
                valoresTableauUser[i][j] = scanner.nextFloat();
            }
        }

        // Criando estrutura da matriz no Simplex
        Simplex simplex = new Simplex(numeroLinha, numeroColuna);
        simplex.preencheTableau(valoresTableauUser);

        // Imprimindo matriz original
        System.out.println("---Matriz Inicial---");
        simplex.print();

        // Verifica se a solução é ilimitada
        while (!verificaIlimitada) {
            Simplex.ERROR err = simplex.calcula();

            if (err == Simplex.ERROR.OTIMO) {
                simplex.print();
                verificaIlimitada = true;
            } else if (err == Simplex.ERROR.ILIMITADO) {
                System.out.println("---A solução é ilimitada---");
                verificaIlimitada = true;
            }
        }
    }
}
