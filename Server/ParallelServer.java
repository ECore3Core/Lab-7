package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.Matrix;

public class ParallelServer {
    private ExecutorService threadPool = Executors.newFixedThreadPool(20);
    private final int SERVER_PORT = 8081;

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            while(true){
                try{
                    Socket clientSocket = serverSocket.accept();
                    threadPool.execute(() -> processClient(clientSocket));
                }
                catch(Exception exception){
                    System.out.println(exception + "в старт внутри");
                }
            }
        }
        catch(IOException exception){
            System.out.println(exception + "ошбибка при запуске сервера");
        }
        finally{
            threadPool.shutdown();
        }
    }
    private void processClient(Socket clientSocket){
        try(ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())){

            Matrix matrix = (Matrix)inputStream.readObject();

            double result = Matrix.getOddSum(matrix);
            
            outputStream.writeDouble(result);
            outputStream.flush();
            System.out.println("результат отпарвлен клиенту");

        }
        catch(Exception exception){
            System.out.println(exception + "в процесс клиент");
        }
    }
}
