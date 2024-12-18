package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientService {
    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 8080;

    
    public double send(Matrix matrix) throws IOException{
        try{
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject(matrix);
            outputStream.flush();
            
            double result = inputStream.readDouble();

            socket.close();

            return result;
        }
        catch(IOException e){
            throw new IOException();
        }
    }
}
