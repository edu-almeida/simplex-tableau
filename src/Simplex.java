public class Simplex {
    private final int linhas;
    private final int colunas; 
    private final float[][] tableau; // tabela simplex
    private boolean solutionIsUnbounded = false;

    public static enum ERROR{
        NOT_OPTIMAL,
        IS_OPTIMAL,
        UNBOUNDED
    };

    public Simplex(int numOfConstraints, int numOfUnknowns){
        linhas = numOfConstraints; // número da linha
        colunas = numOfUnknowns;   // número da coluna
        tableau = new float[linhas][]; // cria um vetor em 2d

        // inicializando as referências para o vetor
        for(int i = 0; i < linhas; i++){
            tableau[i] = new float[colunas];
        }
    }

    // imprime o tableau simplex
    public void print(){
        for(int i = 0; i < linhas; i++){
            for(int j = 0; j < colunas; j++){
                String value = String.format("%.2f", tableau[i][j]);
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // preenche o tableau simplex com coeficientes
    public void fillTable(float[][] data){
        for(int i = 0; i < tableau.length; i++){
            System.arraycopy(data[i], 0, this.tableau[i], 0, data[i].length);
        }
    }

    // calcula os valores do tableau simplex
    // deve ser usado em um loop para computar continuamente até que
    // uma solução ótima ser encontrada
    public ERROR compute(){
        // passo 1
        if(checkOptimality()){
            return ERROR.IS_OPTIMAL; // solução é ótima
        }

        // passo 2
        // encontra a coluna de entrada
        int pivotColumn = findEnteringColumn();
        System.out.println("Pivot Column: "+pivotColumn);

        // passo 3
        // encontra o valor de partida
        float[] ratios = calculateRatios(pivotColumn);
        if(solutionIsUnbounded)
            return ERROR.UNBOUNDED;
        int pivotRow = findSmallestValue(ratios);

        // passo 4
        // forma o próximo tableau
        formNextTableau(pivotRow, pivotColumn);

        // já que formamos uma nova tabela então retorne NOT_OPTIMAL
        return ERROR.NOT_OPTIMAL;
    }

    // Forma um novo tableau a partir de valores pré-comutados.
    private void formNextTableau(int pivotRow, int pivotColumn){
        float pivotValue = tableau[pivotRow][pivotColumn];
        float[] pivotRowVals = new float[colunas];
        float[] pivotColumnVals = new float[colunas];
        float[] rowNew = new float[colunas];

        // divide todas as entradas na linha pivô pela coluna pivô de entrada
        // obtém entrada na linha pivô
        System.arraycopy(tableau[pivotRow], 0, pivotRowVals, 0, colunas);

        // obtém a coluna pivô de entrada
        for(int i = 0; i < linhas; i++)
            pivotColumnVals[i] = tableau[i][pivotColumn];

        // divide os valores na linha pivô pelo valor pivô
        for(int i = 0; i < colunas; i++)
            rowNew[i] =  pivotRowVals[i] / pivotValue;

        // subtrai de cada uma das outras linhas
        for(int i = 0; i < linhas; i++){
            if(i != pivotRow){
                for(int j = 0; j < colunas; j++){
                    float c = pivotColumnVals[i];
                    tableau[i][j] = tableau[i][j] - (c * rowNew[j]);
                }
            }
        }

        // substitui a linha
        System.arraycopy(rowNew, 0, tableau[pivotRow], 0, rowNew.length);
    }

    // calcula os valores da linha pivô
    private float[] calculateRatios(int column){
        float[] positiveEntries = new float[linhas];
        float[] res = new float[linhas];
        int allNegativeCount = 0;
        for(int i = 0; i < linhas; i++){
            if(tableau[i][column] > 0){
                positiveEntries[i] = tableau[i][column];
            }
            else{
                positiveEntries[i] = 0;
                allNegativeCount++;
            }
        }

        if(allNegativeCount == linhas)
            this.solutionIsUnbounded = true;
        else{
            for(int i = 0; i < linhas; i++){
                float val = positiveEntries[i];
                if(val > 0){
                    res[i] = tableau[i][colunas -1] / val;
                }
            }
        }

        return res;
    }

    // encontra a próxima coluna de entrada
    private int findEnteringColumn(){
        float[] values = new float[colunas];
        int location = 0;

        int pos, count = 0;
        for(pos = 0; pos < colunas -1; pos++){
            if(tableau[linhas -1][pos] < 0){
                count++;
            }
        }

        if(count > 1){
            for(int i = 0; i < colunas -1; i++)
                values[i] = Math.abs(tableau[linhas -1][i]);
            location = findLargestValue(values);
        } else location = count - 1;

        return location;
    }


    // encontra o menor valor em um array
    private int findSmallestValue(float[] data){
        float minimum ;
        int c, location = 0;
        minimum = data[0];

        for(c = 1; c < data.length; c++){
            if(data[c] > 0){
                if(Float.compare(data[c], minimum) < 0){
                    minimum = data[c];
                    location  = c;
                }
            }
        }

        return location;
    }

    // encontra o maior valor em um array
    private int findLargestValue(float[] data){
        float maximum = 0;
        int c, location = 0;
        maximum = data[0];

        for(c = 1; c < data.length; c++){
            if(Float.compare(data[c], maximum) > 0){
                maximum = data[c];
                location  = c;
            }
        }

        return location;
    }

    // verifica se a tabela está ótima
    public boolean checkOptimality(){
        boolean isOptimal = false;
        int vCount = 0;

        for(int i = 0; i < colunas -1; i++){
            float val = tableau[linhas -1][i];
            if(val >= 0){
                vCount++;
            }
        }

        if(vCount == colunas -1){
            isOptimal = true;
        }

        return isOptimal;
    }

    // retorna o tableau simplex
    public float[][] getTableau() {
        return tableau;
    }
}