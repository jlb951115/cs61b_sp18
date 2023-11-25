package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Scanner;

public class MemoryGame {
    private int width;
    private int height;
    private int round = 1;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int seed = sc.nextInt();
        MemoryGame game = new MemoryGame(50, 50,seed);
        game.startGame();
    }

    public MemoryGame(int width, int height,int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String str = "";
        while (n > 0){
            drawFrame("Round:"+round+"     "+"Watch!",1);
            int randnum = RandomUtils.uniform(rand,0,26);
            str = str + CHARACTERS[randnum];
            n -= 1;
        }
        return str;

    }

    public void drawFrame(String s,int top) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        if (top == 0) {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(this.height / 2, this.width / 2, s);
            StdDraw.show();
        }
        else if (top == 1){
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(10, 10, s);
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0;i < letters.length();i += 1){
            char t = letters.charAt(i);
            String ini ="";
            drawFrame( ini+t,0);
            StdDraw.pause(1000);
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String result = "";
        drawFrame("Round:"+round+"     "+"Type!",1);
        while (n > 0){

            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                    result = result + c;
                    drawFrame(result,0);
                    n -= 1;

            }

        }
        return result;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        while (true) {
            drawFrame("Round:" + round, 1);
            StdDraw.pause(5000);
            String s = generateRandomString(round);
            flashSequence(s);
            String actual = solicitNCharsInput(round);
            if (actual.equals(s) == true) {
                round += 1;
            }
            if (actual.equals(s) == false) {
                drawFrame("Game Over! You made it to round:" + round, 1);
                return;
            }
            //TODO: Establish Game loop
        }
    }

}
