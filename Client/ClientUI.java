package Client;

import java.awt.*;
import javax.swing.*;

public class ClientUI extends JFrame{
    private JTextField fieldX, fieldY;
    private JButton createMatrixButton, sendButton;
    private JLabel matrixLabel, oddSumLabel;
    private Matrix matrix;
    private JLabel answerLabel;
    private JPanel footerPanel;

    public ClientUI() {
        super("Приложение");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        fieldX = new JTextField(10);
        fieldY = new JTextField(10);
        createMatrixButton = new JButton("Создать");

        createMatrixButton.setBackground(new Color(60, 179, 113));
        createMatrixButton.setForeground(Color.WHITE);
        createMatrixButton.setFont(new Font("Arial", Font.BOLD, 14));
        createMatrixButton.setFocusPainted(false);
        createMatrixButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        answerLabel = new JLabel("Полученный ответ: ", JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Введите X:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(fieldX, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Введите Y:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(fieldY, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(createMatrixButton, gbc);

        add(inputPanel, BorderLayout.NORTH);
        

        matrixLabel = new JLabel("Матрица не задана", JLabel.CENTER);
        matrixLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        matrixLabel.setBorder(BorderFactory.createTitledBorder("Матрица"));
        add(matrixLabel, BorderLayout.CENTER);

        footerPanel = new JPanel(new BorderLayout());
        footerPanel.add(answerLabel, BorderLayout.SOUTH);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel newPanel = new JPanel(new BorderLayout());
        sendButton = new JButton("Отправить");
        oddSumLabel = new JLabel("", JLabel.CENTER);

        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        newPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newPanel.add(sendButton, BorderLayout.WEST);
        newPanel.add(oddSumLabel, BorderLayout.EAST);

        footerPanel.add(newPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
        
        add(footerPanel, BorderLayout.SOUTH);

        createMatrixButton.addActionListener(e -> {
            try {
                if (tryParse(fieldX.getText()) <= 0 || tryParse(fieldY.getText()) <= 0 
                    || tryParse(fieldX.getText()) > 9 || tryParse(fieldY.getText()) > 9) {
                    fieldX.setText("");
                    fieldY.setText("");
                    answerLabel.setText("Введены неверные значения");
                }
                else {
                    matrix = new Matrix(tryParse(fieldX.getText()), tryParse(fieldY.getText()));
                    updateMatrixLabel();
                    answerLabel.setText("Матрица обновлена.");
                }
            } catch (Exception exception) {
                System.err.println(exception);
            }
        });

        sendButton.addActionListener(e ->{
            if(matrix != null){
                try{
                    ClientService client = new ClientService();
                    double result = client.send(matrix);
                    displayResult(result);
                }
                catch(Exception exception){
                    System.out.println(exception + "в ю ай");
                }
            }
            
        });

        setSize(600, 400);
        setVisible(true);
    }

    private void updateMatrixLabel() {
        if(matrix != null){
            matrixLabel.setText("<html><pre>" + matrix.toString() + "</pre></html>");
        }
    }
    private void displayResult(double oddSum){
        oddSumLabel.setText(String.valueOf(oddSum));
    }

    public int tryParse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
