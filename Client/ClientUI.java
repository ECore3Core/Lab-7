package Client;

import java.awt.*;
import javax.swing.*;

public class ClientUI extends JFrame{
    private JTextField fieldX, fieldY;
    private JButton sendButton;
    private JLabel matrixLabel;
    private Matrix matrix;
    private JLabel answerLabel;
    private JPanel footerPanel;

    public ClientUI() {
        super("Приложение");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель для ввода данных
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        fieldX = new JTextField(10);
        fieldY = new JTextField(10);
        sendButton = new JButton("Отправить");

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
        inputPanel.add(sendButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        matrixLabel = new JLabel("Матрица не задана", JLabel.CENTER);
        matrixLabel.setFont(new Font("Serif", Font.BOLD, 16));
        matrixLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(matrixLabel, BorderLayout.CENTER);

        footerPanel = new JPanel(new BorderLayout());
        footerPanel.add(answerLabel, BorderLayout.SOUTH);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(footerPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> {
            try {
                if (tryParse(fieldX.getText()) <= 0 || tryParse(fieldY.getText()) <= 0) {
                    fieldX.setText("");
                    fieldY.setText("");
                    answerLabel.setText("Введены неверные значения");
                } else {
                    matrix = new Matrix(tryParse(fieldX.getText()), tryParse(fieldY.getText()));
                    updateMatrixLabel();
                    answerLabel.setText("Матрица обновлена.");
                }
            } catch (Exception exception) {
                System.err.println(exception);
            }
        });

        setSize(600, 400);
        setVisible(true);
    }

    private void updateMatrixLabel() {
        if(matrix != null){
            matrixLabel.setText("<html>" + matrix.toString().replaceAll("\n", "<br>") + "</html>");
        }
    }

    public int tryParse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
