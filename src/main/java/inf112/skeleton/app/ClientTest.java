package inf112.skeleton.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientTest {
    public static void main(String []args){
        try {
            Socket s = new Socket("localhost", 5556);

            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            try {
                Cards testCard= (Cards) is.readObject();
                System.out.println(testCard.getPosX()+ " " + testCard.getPosY());
                s.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
