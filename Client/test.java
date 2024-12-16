package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;
import java.awt.*;

public class test extends JFrame{
    private JTextField inputX, inputY;
    private JButton sendButton;
    private Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private BufferedReader in = null;
    private Matrix matrix = null;
    private JLabel labelX, labelY, answerLabel, matrixLabel, headerLabel;
    public test(){
        super("LABA7 Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        headerLabel = new JLabel("Клиент", JLabel.CENTER);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        add(headerLabel, BorderLayout.NORTH);

        JPanel Panel = new JPanel(new GridBagLayout());
        Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        labelX = new JLabel("Введите X:");
        inputX = new JTextField(5);

        labelY = new JLabel("Введите Y:");
        inputY = new JTextField(5);

        sendButton = new JButton("Отправить");

        gbc.gridx = 0; gbc.gridy = 0;
        Panel.add(labelX, gbc);

        gbc.gridx = 1;
        Panel.add(inputX, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        Panel.add(labelY, gbc);

        gbc.gridx = 1;
        Panel.add(inputY, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        Panel.add(sendButton, gbc);

        add(Panel, BorderLayout.CENTER);

        JPanel underPanel = new JPanel(new BorderLayout());
        underPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        matrixLabel = new JLabel("", JLabel.CENTER);
        matrixLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        matrixLabel.setForeground(Color.CYAN);

        answerLabel = new JLabel("Cумма нечётных элементов матрицы: ", JLabel.CENTER);
        answerLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        answerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        underPanel.add(matrixLabel, BorderLayout.NORTH);
        underPanel.add(answerLabel, BorderLayout.SOUTH);

        add(underPanel, BorderLayout.SOUTH);


        sendButton.addActionListener(e ->  {
                try{
                    if(tryIntParse(inputX.getText()) <= 0 || tryIntParse(inputY.getText()) <= 0){
                        inputX.setText("");
                        inputY.setText("");
                        matrixLabel.setText("Введены неверные значения");
                    }
                    else{
                        matrix = new Matrix(tryIntParse(inputX.getText()), tryIntParse(inputY.getText()));
                        matrixLabel.setText(matrix.toString());
                        sendMatrixToServer(matrix);
                    }
                }
                catch (Exception exception){
                    System.err.println(exception);
                }
        });
        setSize(600,400);
        setVisible(true);
    }
    public int tryIntParse(String value){
        try{
            return Integer.parseInt(value);
        }
        catch(NumberFormatException e){
            return -1;
        }
    }
    public void sendMatrixToServer(Matrix matrix) throws IOException{
        try{
            clientSocket = new Socket("localhost", 4000);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.writeObject(matrix);
            String odd = in.readLine();
            System.out.println(odd);
            answerLabel.setText("Cумма нечётных элементов матрицы: " + odd);
        }
        catch (IOException exception){
            System.err.println(exception);
            System.exit(1);
        }
        finally {
            clientSocket.close();
            out.close();
            in.close();
        }
    }
}
