package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    public  static void addHexagon(TETile[][] tiles,int a){
        int t = 3;
        for (int x = a; x < 7 + a; x += 1) {
            int i = 0;
            if (x != 2 && x != 3) {
                for ( i = 0; i < (7 - t) / 2; i += 1) {
                    tiles[x][i] = Tileset.NOTHING;
                }
            }
            for (int y = i; y < t + i; y += 1) {
                tiles[x][y] = Tileset.GRASS;
            }
            if (x < 2) {
                t += 2;
            }
            else if (x > 3){
                t -= 2;
            }
        }

    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(60, 30);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon(world,0);
        ter.renderFrame(world);

    }
}
