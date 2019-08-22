package sample;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerConnection implements Runnable {
    private static int port = 8000;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;
    private static ServerSocket serverSocket;
    private ServerListener listener;

    public interface ServerListener{
        void updated(Loan loan);
        void displayMessage(String message);
    }

    public ServerConnection(ServerListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            listener.displayMessage(new Date() + ": Server started");
            socket = serverSocket.accept();
            listener.displayMessage(new Date() + ": Connected to client");
            System.out.println("Forbindelse med client succesfuld");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(true){
                Loan loan = (Loan) in.readObject();
                double rate = loan.getRate(), years = loan.getYears(), amount = loan.getAmount(), monthly, total;
                monthly = amount * (rate / 12 /100 * Math.pow(1 + rate/12/100, years * 12))/(Math.pow(1 + rate/12/100, years * 12) - 1);
                total = monthly * years * 12;
                loan.setMonthlyPayment(monthly);
                loan.setTotalPayment(total);
                out.writeObject(loan);
                listener.updated(loan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
