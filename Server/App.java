package Server;

public class App {
    public static void main(String[] args){
        try{
            (new SequentialServer()).start();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }
}
