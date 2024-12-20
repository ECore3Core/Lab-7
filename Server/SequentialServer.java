package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import common.Matrix;

public class SequentialServer {
    private final int SERVER_PORT = 8081;

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            while(true){
                try(Socket clienSocket = serverSocket.accept()){
                    processClient(clienSocket);
                }
                catch(Exception exception){
                    System.out.println(exception + "в старт внутри");
                }
            }
        }
        catch(IOException exception){
            System.out.println(exception + "в старт");
        }
    }
    private void processClient(Socket clientSocket) throws IOException, ClassNotFoundException{
        try(ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())){

            Matrix matrix = (Matrix)inputStream.readObject();

            double result = Matrix.getOddSum(matrix);
            
            outputStream.writeDouble(result);
            outputStream.flush();

        }
        catch(Exception exception){
            System.out.println(exception + "в процесс клиент");
        }
    }
}
