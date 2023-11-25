package byog.Core;


import byog.TileEngine.TETile;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static byog.Core.Game.*;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    
    public static void main(String []args)  {
     if (args.length > 1) {
                System.out.println("Can only have one argument - the input string");
                System.exit(0);
            } else if (args.length == 1) {
             
               Game game2 = loadWorld();
                TETile[][] worldState = game2.playWithInputString("ld");
                if (game2.is_new == 1) {
                    saveWorld(game2);
                }
                    System.out.println(TETile.toString(worldState));

           } else {
                Game game = loadWorld();
                game.playWithKeyboard();
            }
        }


    public static Game loadWorld() {
        File f = new File("./gameworld.ser");
        if (f.exists()){ 
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Game loadWorld = (Game) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return new Game();
    }



    private static void saveWorld(Game w) {
        File f = new File("./gameworld.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

}
