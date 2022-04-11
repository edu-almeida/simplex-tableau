public class Simplex {
    private final int linhas;
    private final int colunas;
    private final float[][] tableau; // tabela simplex
    private boolean solucaoIlimitada = false;

    // Esse método auxilia
    public static enum ERROR {
        NAO_OTIMO,
        OTIMO,
        ILIMITADO
    };

    public Simplex(int numDeLinhas, int numDeColunas) {
        linhas = numDeLinhas; // número de linhas
        colunas = numDeColunas; // número de colunas
        tableau = new float[linhas][]; // cria um vetor em 2d

        // inicializando as referências para o vetor
        for (int i = 0; i < linhas; i++) {
            tableau[i] = new float[colunas];
        }
    }

    // imprime o tableau simplex
    public void print() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                String value = String.format("%.2f", tableau[i][j]);
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // preenche o tableau simplex com coeficientes
    public void preencheTableau(float[][] data) {
        for (int i = 0; i < tableau.length; i++) {
            System.arraycopy(data[i], 0, this.tableau[i], 0, data[i].length);
        }
    }

    // calcula os valores do tableau simplex
    // deve ser usado em um loop para computar continuamente até que
    // uma solução ótima ser encontrada
    public ERROR calcula() {
        // passo 1
        if (verificaOtimizacao()) {
            return ERROR.OTIMO; // solução é ótima
        }

        // passo 2
        // encontra a coluna de entrada
        int colunaPivo = encColunEntrada();
        System.out.println("Coluna Pivo: " + colunaPivo);

        // passo 3
        // encontra o valor de partida
        float[] indice = calcValoresLinhaPivo(colunaPivo);
        if (solucaoIlimitada)
            return ERROR.ILIMITADO;
        int linhaPivo = encMenorValor(indice);

        // passo 4
        // forma o próximo tableau
        formaNovoTableau(linhaPivo, colunaPivo);

        // já que formamos uma nova tabela então retorne NAO_OTIMO
        return ERROR.NAO_OTIMO;
    }

    // Forma um novo tableau a partir de valores pré-comutados.
    private void formaNovoTableau(int linhaPivo, int colunaPivo) {
        float valorPivo = tableau[linhaPivo][colunaPivo];
        float[] linhaPivoVals = new float[colunas];
        float[] colunaPivoVals = new float[colunas];
        float[] linhaNova = new float[colunas];

        // divide todas as entradas na linha pivô pela coluna pivô de entrada
        // obtém entrada na linha pivô
        System.arraycopy(tableau[linhaPivo], 0, linhaPivoVals, 0, colunas);

        // obtém a coluna pivô de entrada
        for (int i = 0; i < linhas; i++)
            colunaPivoVals[i] = tableau[i][colunaPivo];

        // divide os valores na linha pivô pelo valor pivô
        for (int i = 0; i < colunas; i++)
            linhaNova[i] = linhaPivoVals[i] / valorPivo;

        // subtrai de cada uma das outras linhas
        for (int i = 0; i < linhas; i++) {
            if (i != linhaPivo) {
                for (int j = 0; j < colunas; j++) {
                    float c = colunaPivoVals[i];
                    tableau[i][j] = tableau[i][j] - (c * linhaNova[j]);
                }
            }
        }

        // substitui a linha
        System.arraycopy(linhaNova, 0, tableau[linhaPivo], 0, linhaNova.length);
    }

    // calcula os valores da linha pivô
    private float[] calcValoresLinhaPivo(int column) {
        float[] entradasPositivas = new float[linhas];
        float[] res = new float[linhas];
        int todaContagemNegativa = 0;

        for (int i = 0; i < linhas; i++) {
            if (tableau[i][column] > 0) {
                entradasPositivas[i] = tableau[i][column];
            } else {
                entradasPositivas[i] = 0;
                todaContagemNegativa++;
            }
        }

        if (todaContagemNegativa == linhas)
            this.solucaoIlimitada = true;
        else {
            for (int i = 0; i < linhas; i++) {
                float val = entradasPositivas[i];
                if (val > 0) {
                    res[i] = tableau[i][colunas - 1] / val;
                }
            }
        }

        return res;
    }

    // encontra a próxima coluna de entrada
    private int encColunEntrada() {
        float[] valores = new float[colunas];
        int localizacao = 0;
        int pos, count = 0;

        for (pos = 0; pos < colunas - 1; pos++) {
            if (tableau[linhas - 1][pos] < 0) {
                count++;
            }
        }

        if (count > 1) {
            for (int i = 0; i < colunas - 1; i++)
                valores[i] = Math.abs(tableau[linhas - 1][i]);
            localizacao = encMaiorValorNoVetor(valores);
        } else
            localizacao = count - 1;

        return localizacao;
    }

    // encontra o menor valor em um array
    private int encMenorValor(float[] data) {
        float minimo;
        int c, localizacao = 0;
        minimo = data[0];

        for (c = 1; c < data.length; c++) {
            if (data[c] > 0) {
                if (Float.compare(data[c], minimo) < 0) {
                    minimo = data[c];
                    localizacao = c;
                }
            }
        }

        return localizacao;
    }

    // encontra o maior valor em um array
    private int encMaiorValorNoVetor(float[] data) {
        float maximo = 0;
        int c, localizacao = 0;
        maximo = data[0];

        for (c = 1; c < data.length; c++) {
            if (Float.compare(data[c], maximo) > 0) {
                maximo = data[c];
                localizacao = c;
            }
        }

        return localizacao;
    }

    // verifica se a tabela está ótima
    public boolean verificaOtimizacao() {
        boolean eOtima = false;
        int vCount = 0;

        for (int i = 0; i < colunas - 1; i++) {
            float val = tableau[linhas - 1][i];
            if (val >= 0) {
                vCount++;
            }
        }

        if (vCount == colunas - 1) {
            eOtima = true;
        }

        return eOtima;
    }

    // retorna o tableau simplex
    public float[][] getTableau() {
        return tableau;
    }
}