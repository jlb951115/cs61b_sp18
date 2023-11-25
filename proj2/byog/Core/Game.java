package byog.Core;


import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


import java.io.*;
import java.util.*;

import static byog.Core.Main.loadWorld;

class position {
    int x;
    int y;
    int hi;
    int wi;
    position(int x,int y,int wi,int hi){
        this.x = x;
        this.y = y;
        this.wi = wi;
        this.hi = hi;
    }
}
class Po{
    int x;
    int y;
    public Po(int x,int y){
        this.x = x;
        this.y = y;
    }
}
public class Game implements Serializable{
    private static final long serialVersionUID = 1110230413363401157L;
    static Comparator<position> cmp = new Comparator<position>() {
        public int compare(position e1, position e2) {
            return e1.x - e2.x;
        }
    };
    public static int is_load = 0;
    public  int is_new ;
    public long seed;
    public  int WIDTH = 50;
    public  int HEIGHT = 50;
    TETile[][] tiles;
    Random a;
    transient Queue<position> q;
    transient Queue<Po> p;
    String str ;
    public Game(){
        this.tiles = new TETile[WIDTH][HEIGHT];
        this.q= new PriorityQueue<>(cmp);
        this.seed = 0;
        this.is_new = 0;
        this.str = "";
        this.a = new Random(seed);
        this.p= new LinkedList<Po>();
    }

    public int ax;
    public int ay;

    public  void addrandomroom() {
        int high = RandomUtils.uniform(a, 2, 6);
        int wide = RandomUtils.uniform(a, 2, 6);
        int start1 = RandomUtils.uniform(a, 1, HEIGHT - 1);;
        int start2 = RandomUtils.uniform(a, 1, WIDTH - 1);
        if (start1 + high < WIDTH && start2 + wide < HEIGHT) {
            if (tiles[start1][start2] == Tileset.NOTHING && tiles[start1 + high - 1][start2] == Tileset.NOTHING &&
                    tiles[start1][start2 + wide - 1] == Tileset.NOTHING && tiles[start1 + high - 1][start2 + wide - 1] == Tileset.NOTHING) {
                for (int i = start1; i < start1 + high; i += 1) {
                    for (int j = start2; j < start2 + wide; j += 1) {
                        tiles[i][j] = Tileset.FLOOR;
                    }
                }
                for (int i = start1 - 1; i <= start1 + high; i += 1) {
                    for (int j = start2 - 1; j <= start2 + wide; j += 1) {
                        if (tiles[i][j] == Tileset.NOTHING)
                            tiles[i][j] = Tileset.WALL;
                    }
                }
                position temp = new position(start1,start2,wide,high);
                q.add(temp);
            }
        }
    }
    public  void addhallways(){
        position b = q.poll();
        int x1,y1;
        int x2,y2;
        x1 = RandomUtils.uniform(a, b.x , b.x + b.hi);
        y1 = RandomUtils.uniform(a, b.y , b.y + b.wi);
        position c = q.poll();
        x2 = RandomUtils.uniform(a, c.x , c.x + c.hi);
        y2 = RandomUtils.uniform(a, c.y , c.y + c.wi);
        tiles[x2][y2] = Tileset.FLOOR;
        tiles[x1][y1] = Tileset.FLOOR;
        int minx = x1 <= x2 ? x1 : x2;
        int miny = y1 <= y2 ? y1 : y2;
        for (int i = minx;i <= (x1 + x2) - minx; i += 1){
            tiles[i][y2] = Tileset.FLOOR;
        }
        for (int i = miny;i <= (y2 + y1) - miny; i += 1){
            tiles[x1][i] = Tileset.FLOOR;
        }

        q.add(c);
    }
    public void addwall(){

        for (int i = 0;i < HEIGHT ;i += 1){
            for (int j = 0;j < WIDTH ;j += 1) {
                if (i > 0 && i < HEIGHT - 1) {
                    if (tiles[i + 1][j] == Tileset.FLOOR || tiles[i - 1][j] == Tileset.FLOOR) {
                        if (tiles[i][j] == Tileset.NOTHING)
                            tiles[i][j] = Tileset.WALL;

                    }

                }
                else if (i == 0){
                    if(tiles[i + 1][j] == Tileset.FLOOR) {
                        if (tiles[i][j] == Tileset.NOTHING)
                            tiles[i][j] = Tileset.WALL;
                        Po t = new Po(i,j);
                        p.add(t);

                    }
                }
                else if (i == HEIGHT - 1){
                    if(tiles[i - 1][j] == Tileset.FLOOR) {
                        if (tiles[i][j] == Tileset.NOTHING)
                            tiles[i][j] = Tileset.WALL;
                        Po t = new Po(i,j);
                        p.add(t);

                    }
                }
                if (j > 0 && j < HEIGHT - 1) {
                    if (tiles[i][j + 1] == Tileset.FLOOR || tiles[i][j - 1] == Tileset.FLOOR) {
                        if (tiles[i][j] == Tileset.NOTHING)
                            tiles[i][j] = Tileset.WALL;
                        Po t = new Po(i,j);
                        p.add(t);
                    }

                }
                else if (j == 0){
                    if(tiles[i][j + 1] == Tileset.FLOOR) {
                        if (tiles[i][j] == Tileset.NOTHING)
                            tiles[i][j] = Tileset.WALL;
                        Po t = new Po(i,j);
                        p.add(t);
                    }
                }
                else if (j == HEIGHT - 1){
                    if(tiles[i][j - 1] == Tileset.FLOOR) {
                        if (tiles[i][j] == Tileset.NOTHING)
                            tiles[i][j] = Tileset.WALL;
                        Po t = new Po(i,j);
                        p.add(t);

                    }
                }

            }

        }

    }


    public  void adddour(){
        int x = 0;
        int y= 0;
        while (p.isEmpty() == false) {
            Po k = p.poll();
            x = k.x;
            y = k.y;
            if (x > 0 && y > 0 && y < WIDTH - 1 && x < HEIGHT - 1) {
                if (tiles[x][y - 1] == Tileset.NOTHING && tiles[x][y + 1] == Tileset.FLOOR)
                    break;
                if (tiles[x][y - 1] == Tileset.FLOOR && tiles[x][y + 1] == Tileset.NOTHING)
                    break;
                if (tiles[x - 1][y] == Tileset.FLOOR && tiles[x + 1][y] == Tileset.NOTHING)
                    break;
                if (tiles[x - 1][y] == Tileset.NOTHING && tiles[x + 1][y] == Tileset.FLOOR)
                    break;
            }
        }
        tiles[x][y] = Tileset.LOCKED_DOOR;

    }
    public  void init(){
        for (int x = 0; x < HEIGHT; x += 1) {
            for (int y = 0; y < WIDTH; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }

    }
    /* Feel free to change the width and height. */
    public void startplay () {
        int tx,ty;
        while (true) {
            tx = RandomUtils.uniform(a, 0, WIDTH - 1);
            ty = RandomUtils.uniform(a, 0, HEIGHT - 1);
            if (tiles[tx][ty] == Tileset.FLOOR)
                break;
        }
        tiles[tx][ty] = Tileset.PLAYER;
        this.ax = tx;
        this.ay = ty;
    }
    public void playString() {
        for (int i = 0; i < str.length(); i += 1){
            char c = str.charAt(i);
            if (c == 'a' && tiles[ax - 1][ay].character() != Tileset.WALL.character()) {
                tiles[ax - 1][ay] = Tileset.PLAYER;
                tiles[ax][ay] = Tileset.FLOOR;
                ax -= 1;
            }
            else if (c == 'd' && tiles[ax + 1][ay].character() != Tileset.WALL.character()) {
                tiles[ax + 1][ay] = Tileset.PLAYER;
                tiles[ax][ay] = Tileset.FLOOR;
                ax += 1;
            }
            else if (c == 's' && tiles[ax][ay - 1].equals(Tileset.WALL) == false) {
                tiles[ax][ay - 1] = Tileset.PLAYER;
                tiles[ax][ay] = Tileset.FLOOR;
                ay -= 1;
            }
            else if (c == 'w' && tiles[ax][ay + 1].character() != Tileset.WALL.character()) {
                tiles[ax][ay + 1] = Tileset.PLAYER;
                tiles[ax][ay] = Tileset.FLOOR;
                ay += 1;
            }


        }
    }
    public  void createworld(){
        init();

        int n = RandomUtils.uniform(a, 5, 50);

        while (n > 0){
            addrandomroom();
            n -= 1;
        }
        //System.out.println("test3");
        while (q.size() >= 2)
            addhallways();
        addwall();
        // System.out.println("test4");
        adddour();
        startplay();
        playString();

    }


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     *
     * @return
     */
    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        int i = 1;
        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            long sum = 0;
            for (i = 1; i < input.length(); i += 1) {
                if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                    sum = sum * 10 + input.charAt(i) - '0';
                } else {
                    this.seed = sum;
                    this.a = new Random(seed);
                    break;
                }

            }
        }


        for (int j = i; j < input.length(); j += 1) {
            if (input.charAt(j) >= 'a' && input.charAt(j) <= 'z') {
                this.str = this.str + input.charAt(j);
            }
            if (input.charAt(j) == ':') {
                is_new = 1;
                break;
            }
        }
        if (input.charAt(0) == 'l'){
            playString();

        }
        if (input.charAt(0) == 'n')
        createworld();
        return this.tiles;


    }
    public void playWithKeyboard(){
        return;
    }



}
