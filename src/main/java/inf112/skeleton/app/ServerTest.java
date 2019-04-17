package inf112.skeleton.app;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String []args){
        try {
            ServerSocket ss= new ServerSocket(5556);
            Socket s= ss.accept();
            System.out.println("client connected!");

            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

            Sprite sprite =new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png"));
            Cards testCard  = new Cards(10,20,CardValues.values()[0].getName(), CardValues.values()[0].getPriority());
            os.writeObject(testCard);

            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
