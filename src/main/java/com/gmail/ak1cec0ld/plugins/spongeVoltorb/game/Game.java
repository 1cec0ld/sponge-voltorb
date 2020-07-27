package com.gmail.ak1cec0ld.plugins.spongeVoltorb.game;



import java.util.Random;

public class Game {


    private Slot[][] board;
    private int[] verticalV;
    private int[] horizontalV;
    private int[] verticalB;
    private int[] horizontalB;
    private int level;
    private boolean finished = false;
    private static Random r = new Random();

    private int gridLength;
    //Because the number of voltorbs and bonuses depends on the grid size, the level can only go so high
    //before it makes the add-ons outnumber the grid square availability.
    private int levelMax;

    public Game() {
        this(4);
    }

    public Game(int size){
        gridLength = size;
        levelMax = ((gridLength * gridLength)-(gridLength *2)+2)/2;
        this.board = new Slot[gridLength][gridLength];
        this.verticalV = new int[gridLength];
        this.horizontalV = new int[gridLength];
        this.verticalB = new int[gridLength];
        this.horizontalB = new int[gridLength];
    }


    public void initialize(int l){
        finished = false;
        level = Math.max(1, Math.min(l, levelMax));
        for(int row = 0; row < gridLength; row++){
            for(int col = 0; col < gridLength; col++){
                board[row][col] = new Slot(1);
            }
        }
        int voltorbs = level+ gridLength;
        int bonuses = level+ gridLength -2;
        double roll;
        int counter = 0;

        while(voltorbs > 0){
            roll = r.nextDouble();
            if( roll < 0.5 ) {
                board[(counter%(gridLength * gridLength)/ gridLength)][counter% gridLength].value = 0;
                verticalV[(counter%(gridLength * gridLength)/ gridLength)]++;
                horizontalV[counter% gridLength]++;
                voltorbs--;
            }
            counter++;
        }
        while(bonuses > 0){
            roll = r.nextDouble();
            if( roll < 0.5 && board[(counter%(gridLength * gridLength)/ gridLength)][counter% gridLength].value==1){
                roll = r.nextDouble();
                board[(counter%(gridLength * gridLength)/ gridLength)][counter% gridLength].value = (int)Math.round(roll)+2;
                verticalB[(counter%(gridLength * gridLength)/ gridLength)] += (int)Math.round(roll)+2;
                horizontalB[counter% gridLength] += (int)Math.round(roll)+2;
                bonuses--;
            }
            counter++;
        }
    }

    public int getLevel(){
        return level;
    }
    public int getLevelMax(){
        return levelMax;
    }

    public Slot[][] getBoard() {
        return board;
    }
    public int[] getVerticalVoltorbs(){
        return verticalV;
    }
    public int[] getVerticalBonus(){
        return verticalB;
    }
    public int[] getHorizontalVoltorbs(){
        return horizontalV;
    }
    public int[] getHorizontalBonus(){
        return horizontalB;
    }
    public int getGridSize(){
        return gridLength;
    }

    public Slot get(int row, int col){
        return board[row][col];
    }

    public void markUsed(int row, int col){
        board[row][col].used = true;
        finished = true;
        for(int i = 0; i < gridLength; i++) {
            for (int j = 0; j < gridLength; j++) {
                //if a bonus exists and is not used, not finished
                if (board[i][j].value > 1 && !board[i][j].used) finished = false;
            }
        }
        if(board[row][col].value == 0){
            finished = true;
        }
    }

    public boolean finished(){
        return finished;
    }
    public class Slot{
        public int value;
        public boolean used;
        Slot(int val){
            value = val;
            used = false;
        }
    }
}
