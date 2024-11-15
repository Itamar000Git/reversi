//can be change

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic {


    private Disc[][] board;  // Represents the game board
    private boolean[][] neighbor;
    private Player player1;
    private Player player2;
    private Player curent;
    private Disc d;
    public static boolean firstPlayerTurn = true;
    public static ArrayList<Position> flipper=new ArrayList<>();
    public Stack <Move> move_st;
    public static boolean reset;


    public GameLogic(){
        this.board =new Disc[8][8];
        this.neighbor=new boolean[8][8];

        if(isFirstPlayerTurn()){
            curent=player1;
        }
        else curent=player2;


    }

    /**
     * Check if there is disc on the board
     * @param a The position for locating a new disc on the board.
     * @param disc
     * @return
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        flipper.clear();
    int c=countFlips(a);
        if(board[a.row][a.col] != null || !neighbor[a.row][a.col] || ((c==0) && !reset)) {//check if the position available
            System.out.println("this move is invalid");
            return false;
        }

        Move m = new Move(disc);
        m.MakeMove(disc,board,a);
        firstPlayerTurn=!firstPlayerTurn;

        if(isFirstPlayerTurn()){
            curent=player1;
        }
        else curent=player2;

        neighbor_Update(a);
        //ValidMoves();
        System.out.println("The amount of dist to flip is: "+c+"\ntheir position is: ");
        //countFlips(a);
        for (int i=0; i<flipper.size(); i++){
            System.out.println("( "+flipper.get(i).row+" , "+flipper.get(i).col+" )" );
        }


      return true;

    }



    @Override
    public Disc getDiscAtPosition(Position position) {

        return board[position.row()][position.col()];
    }

    @Override
    public int getBoardSize() {

        return board.length;
    }

    @Override
    public  List<Position> ValidMoves() {
        List <Position> my_L = new ArrayList<>();


        for (int i=0; i< 8; i++){
            for (int j=0; j<8;j++){
                Position p=new Position(i,j);
                if (neighbor[i][j] && board[i][j]==null && countFlips(p)>0){
                    my_L.add(p);
                   // System.out.println(my_L.getFirst().row +" "+ my_L.getFirst().col);
                }
            }

        }


        return my_L;
    }

    private int countHelper(boolean left, boolean right, boolean up, boolean down, boolean leftUp, boolean rightUp, boolean rightDown, boolean leftDown, Position p){
        int x=p.row();
        int y=p.col();
        int count=0, tmp_count=0;


        while ((x<=7) & (x>=0) & (y<=7) & (y>=0)) {
            if (left){
                y--;
            } else if (right) {
                y++;
            } else if(up){
                x--;
            } else if(down){
                x++;
            } else if(leftUp){
                x--;
                y--;
            } else if(rightUp){
                x--;
                y++;
            } else if(rightDown){
                x++;
                y++;
            } else if(leftDown){
                x++;
                y--;
            }
            if (y==8 || y==(-1) || x==8 || x ==(-1)){ //if the while loop reached to that spot the last dist belong to the opponent and there is no flips

                count=0;
                break;
            }

            if (board[x][y] == null) {  //if there is a null spot we need to stop and delete the last count

                        count=0;
                break;
            }


            if ((board[x][y].getOwner().isPlayerOne != curent.isPlayerOne)){ //checking if that this is the opponent disc


                count++;
            }else if (count==0){
                break;
            }
            else {
                //this spot means that we count few position and we can add them to fliiper, and update tmp_counter
               // tmp_count=count;
                return count;
            }



        }





        return count;
    }


    /**
     * when we here the move is valid
     * @param a
     * @return
     */
    @Override
    public int countFlips(Position a) {
        int count;
      //  if (!reset){

            int co_L= countHelper(true,false,false,false,false,false,false,false,a);
            int co_R= countHelper(false,true,false,false,false,false,false,false,a);
            int co_U= countHelper(false,false,true,false,false,false,false,false,a);
            int co_D= countHelper(false,false,false,true,false,false,false,false,a);
            int co_LU= countHelper(false,false,false,false,true,false,false,false,a);
            int co_RU= countHelper(false,false,false,false,false,true,false,false,a);
            int co_RD= countHelper(false,false,false,false,false,false,true,false,a);
            int co_LD= countHelper(false,false,false,false,false,false,false,true,a);
            count=co_L+co_R+co_U+co_D+co_LU+co_RU+co_RD+co_LD;
            //System.out.println(count);
      //  }

        if (co_L>0){
            to_Flip(co_L,"left",a);
        }
        if (co_R>0){
            to_Flip(co_R,"right",a);
        }
        if (co_U>0){
            to_Flip(co_U,"up",a);
        }
        if (co_D>0){
            to_Flip(co_D,"down",a);
        }
        if (co_LU>0){
            to_Flip(co_LU,"left_up",a);
        }
        if (co_RU>0){
            to_Flip(co_RU,"right_up",a);
        }
        if (co_RD>0){
            to_Flip(co_RD,"right_down",a);
        }
        if (co_LD>0){
            to_Flip(co_LD,"left_down",a);
        }


        // in this point i have number of flipps for each side

        return count;


    }





    @Override
    public Player getFirstPlayer() {

        return player1 ;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {

       this.player1=player1;
       this.player2=player2;

    }

    @Override
    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;

    }

    @Override
    public boolean isGameFinished() {
       if(ValidMoves().isEmpty())
           return true;
        return false;
    }

    @Override
    public void reset() {
       // reset=true;
        board =new Disc[8][8];
        board[3][3] = new SimpleDisc(player2);
        board[4][4] = new SimpleDisc(player2);
        board[3][4] =new  SimpleDisc(player1);
        board[4][3] =new  SimpleDisc(player1);
        curent=player1;
        firstPlayerTurn=true;

        for (int i=2; i<6;i++){
            for( int j=2; j<6;j++){
                neighbor[i][j]=true;
            }
        }



    }

    @Override
    public void undoLastMove() {

    }

    private  void neighbor_Update(Position a){
        int r=a.row, c=a.col;

        if(r < 7 && r>0 && c<7 && c>0){
            neighbor[r+1][c]=true;
            neighbor[r+1][c+1]=true;
            neighbor[r][c+1]=true;
            neighbor[r-1][c+1]=true;
            neighbor[r-1][c]=true;
            neighbor[r-1][c-1]=true;
            neighbor[r][c-1]=true;
            neighbor[r+1][c-1]=true;
        }

        if(r==0){
            if(c==0){
                neighbor[r][c+1]=true;
                neighbor[r+1][c+1]=true;
                neighbor[r+1][c]=true;
            }
            else if (c==7){
                neighbor[r][c-1]=true;
                neighbor[r+1][c-1]=true;
                neighbor[r+1][c]=true;
            }
            else{
                neighbor[r][c-1]=true;
                neighbor[r+1][c-1]=true;
                neighbor[r+1][c]=true;
                neighbor[r+1][c+1]=true;
                neighbor[r][c+1]=true;
            }


        }
        else if (r==7){
            if (c==0){
                neighbor[r-1][c]=true;
                neighbor[r-1][c+1]=true;
                neighbor[r][c+1]=true;
            }
            else if (c==7){
                neighbor[r-1][c-1]=true;
                neighbor[r-1][c]=true;
                neighbor[r][c-1]=true;
            }
            else {
                neighbor[r][c-1]=true;
                neighbor[r-1][c-1]=true;
                neighbor[r-1][c]=true;
                neighbor[r-1][c+1]=true;
                neighbor[r][c+1]=true;
            }


        }
        else {
            if(c==0){
                neighbor[r-1][c]=true;
                neighbor[r-1][c+1]=true;
                neighbor[r+1][c]=true;
                neighbor[r+1][c+1]=true;
                neighbor[r+1][c]=true;
            }
            if(c==7){
                neighbor[r-1][c]=true;
                neighbor[r-1][c-1]=true;
                neighbor[r][c-1]=true;
                neighbor[r+1][c-1]=true;
                neighbor[r+1][c]=true;
            }

        }
    }

    public static void to_Flip(int co , String side, Position p){
        int x = p.row();
        int y = p.col();
        if(side.equals("left")){
            for (int i=0; i<co;i++){
                 y= y-1;
                flipper.add(new Position(x,y));
            }
        } else if (side.equals("right")) {
            for (int i=0; i<co;i++){
                y= y+1;
                flipper.add(new Position(x,y));
            }
        }
        else if (side.equals("up")) {
            for (int i=0; i<co;i++){
                x=x-1;
                flipper.add(new Position(x,y));
            }
        }
        else if (side.equals("down")) {
            for (int i=0; i<co;i++){
                x=x+1;
                flipper.add(new Position(x,y));
            }
        }
        else if (side.equals("up")) {
            for (int i=0; i<co;i++){
                x=x-1;
                flipper.add(new Position(x,y));
            }
        }
        else if (side.equals("left_up")) {
            for (int i=0; i<co;i++){
                x=x-1;
                y=y-1;
                flipper.add(new Position(x,y));
            }
        }
        else if (side.equals("right_up")) {
            for (int i=0; i<co;i++){
                x=x-1;
                y=y+1;
                flipper.add(new Position(x,y));
            }
        }
        else if (side.equals("right_down")) {
            for (int i=0; i<co;i++){
                x=x+1;
                y=y+1;
                flipper.add(new Position(x,y));
            }
        }
        else if (side.equals("left_down")) {
            for (int i=0; i<co;i++){
                x=x+1;
                y=y-1;
                flipper.add(new Position(x,y));
            }
        }
    }


}
