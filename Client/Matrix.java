package Client;

import java.io.Serializable;
import java.util.Random;


public class Matrix implements Serializable{
    private double[][] matrix;
    public Matrix(int x, int y){
        matrix = new double[x][y];
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("Один из аргументов меньше 0");
        }
        Random rnd = new Random();
        double min = -10;
        double max = 11;
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                matrix[i][j] = min + (max - min) * rnd.nextDouble();
            }
        }
    }
    public int getRows(){
        return matrix.length;
    }
    public int getColumns(){
        return matrix[0].length;
    }
    public double getElement(int x, int y){
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("Один из аргументов меньше 0");
        }
        if(x > getRows() || y > getColumns()){
            throw new IllegalArgumentException("Индексы выходят за границы допустимых значений");
        }
        return matrix[x][y];
    }
    public void setElement(int x, int y, double value){
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("Один из аргументов меньше 0");
        }
        if(x > getRows() || y > getColumns()){
            throw new IllegalArgumentException("Индексы выходят за границы допустимых значений");
        }
        matrix[x][y] = value;
    }
    public static int getOddSum(Matrix matrix){
        int result = 0;
        for(int i = 0; i < matrix.getRows(); i++){
            for(int j = 0; j < matrix.getColumns(); j++){
                double currentNumber = matrix.getElement(i, j);
                if((int)currentNumber % 2 == 0){
                    result += currentNumber;
                }
            }
        }
        return result;
    }
}
