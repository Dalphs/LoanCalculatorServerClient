package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;


public class Controller {
    @FXML
    Button submitButton;
    @FXML
    TextField rateTextField;
    @FXML
    TextField yearsTextField;
    @FXML
    TextField amountTextField;
    @FXML
    TextArea logTextArea;

    private static int port = 8000;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;
    private static String host = "localhost";

    @FXML
    public void initialize(){
        try {
            socket = new Socket(host, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitPressed(ActionEvent actionEvent) {
        double rate, years, amount;
        rate = Double.parseDouble(rateTextField.getText());
        years = Double.parseDouble(yearsTextField.getText());
        amount = Double.parseDouble(amountTextField.getText());
        Loan loan = new Loan(rate, years, amount);
        logTextArea.appendText("Annual interest rate: " + loan.getRate() + "\nNumber of years: " + loan.getYears() + "\n" + "\nTotal amount: " + loan.getAmount() + "\n");
        try {
            out.writeObject(loan);
            loan = (Loan) in.readObject();

            logTextArea.appendText("Monthly payment: " + loan.getMonthlyPayment() + "\nTotal payment: " + loan.getTotalPayment() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
