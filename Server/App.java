package Server;

public class App {
    public static void main(String[] args){
        (new SequentialServer()).start();
        //(new ParallelServer()).start();
    }
}
