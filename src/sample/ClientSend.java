package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSend implements Runnable {
    private Loan loan;

    private static int port = 8000;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;
    private static String host = "localhost";
    private ClientSendListener listener;

    public interface ClientSendListener{
        void answered(Loan loan);
    }

    public ClientSend(ClientSendListener listener) {
        this.listener = listener;
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.writeObject(loan);
            loan = (Loan) in.readObject();
            listener.answered(loan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
