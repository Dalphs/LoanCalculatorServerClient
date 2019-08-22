package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerController implements ServerConnection.ServerListener {
    public Button startServer;
    @FXML
    TextArea serverLogTextArea;


    public void startServer(ActionEvent actionEvent) {
        new Thread(new ServerConnection(this)).start();
    }

    @Override
    public void updated(Loan loan) {

        serverLogTextArea.appendText("Monthly:" + loan.getMonthlyPayment() + "\nTotal payment: " + loan.getTotalPayment() + "\n");
    }
}
