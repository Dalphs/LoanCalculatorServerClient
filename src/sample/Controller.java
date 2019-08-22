package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;


public class Controller implements ClientSend.ClientSendListener {
    public Button submitButton1;
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
    private ClientSend clientSend;


    public void submitPressed(ActionEvent actionEvent) {
        double rate, years, amount;
        rate = Double.parseDouble(rateTextField.getText());
        years = Double.parseDouble(yearsTextField.getText());
        amount = Double.parseDouble(amountTextField.getText());
        Loan loan = new Loan(rate, years, amount);
        logTextArea.appendText("Annual interest rate: " + loan.getRate() + "\nNumber of years: " + loan.getYears() + "\nTotal amount: " + loan.getAmount() + "\n");
        clientSend.setLoan(loan);
        Thread thread = new Thread(clientSend);
        thread.start();
    }

    public void startConnection(ActionEvent actionEvent) {
        clientSend = new ClientSend(this);
    }

    @Override
    public void answered(Loan loan) {
        logTextArea.appendText("Monthly payment: " + loan.getTotalPayment() + "\nTotal Payment: " + loan.getTotalPayment() + "\n\n");
    }
}
