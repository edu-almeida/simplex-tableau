public class Main {

    public static void main(String[] args) {

        boolean sair = false;

        // Exemplo do problema:
        // max        z = 2x1 + 3x2
        // sujeito a  x1 + x2 + x3 = 6
        //            2x1 + x2 + x4 = 10
        //            -x1 + x2 + x5 = 4
        float[][] standardized = {
                {1, 1, 1, 0, 0, 6},
                {2, 1, 0, 1, 0, 10},
                {-1, 1, 0, 0, 1, 4},
                {-2, -3, 0, 0, 0, 0}
        };

        // Criando estrutura da matriz no Simplex
        Simplex simplex = new Simplex(4, 6);

        simplex.preencheTableau(standardized);

        // Imprimindo matriz original
        System.out.println("---Matriz Inicial---");
        simplex.print();

        // Verifica se a solução é ilimitada
        while (!sair) {
            Simplex.ERROR err = simplex.calcula();

            if (err == Simplex.ERROR.OTIMO) {
                simplex.print();
                sair = true;
            } else if (err == Simplex.ERROR.ILIMITADO) {
                System.out.println("---A solução é ilimitada---");
                sair = true;
            }
        }
    }
}