package common;

import java.io.Serializable;
import java.util.Random;


public class Matrix implements Serializable{
    private double[][] matrix;
    public Matrix(int x, int y){
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("Один из аргументов меньше 0");
        }
        Random rnd = new Random();
        matrix = new double[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                matrix[i][j] = rnd.nextInt(21) - 10;
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
                if((int)currentNumber % 2 != 0){
                    result += currentNumber;
                }
            }
        }
        return result;
    }
    @Override
    public String toString(){
        StringBuffer resultString = new StringBuffer();
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getColumns(); j++){
                resultString.append(String.format("%5.1f", getElement(i, j)));
            }
            resultString.append("\n");
        }
        return resultString.toString();
    }
}
