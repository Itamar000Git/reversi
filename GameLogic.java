//can be change

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
    public Stack <Move> move_st;

    public GameLogic(){
        this.board =new Disc[8][8];
        this.neighbor=new boolean[8][8];
      //  this.d = new Disc();
        if(isFirstPlayerTurn()){
            curent=player1;
        }
        else curent=player2;


    }
// public GameLogic(Player player1, Player player2) {
////      this.board = new Disc[8][8];
//      this.player1 = player1;
//      this.player2 = player2;
////
////
////
//   }



    /**
     * Check if there is disc on the board
     * @param a The position for locating a new disc on the board.
     * @param disc
     * @return
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {



        if(board[a.row][a.col] != null || !neighbor[a.row][a.col]) {//check if the position available
            System.out.println("this move is invalid");
            return false;
        }

        curent=disc.getOwner();
        countFlips(a);

        Move m = new Move(disc);
        m.MakeMove(disc,board,a);
//        if ( countFlips(a)==0){
//            System.out.println("this move is invalid");
//            return false;
//        }





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
    public List<Position> ValidMoves() {
        // check if there is disc next
        for (int i =0 ;i<board.length;i++){

        }

        return List.of();
    }



    /**
     * when we here the move is valid
     * @param a
     * @return
     */
    @Override
    public int countFlips(Position a) {
        int count=0, tmp_count=0;
        int x=a.row();
        int y=a.col();
        System.out.println("x: "+x +" y: "+y);

        y=y+1;
        tmp_count=count;
        //right
         while (x<8 && y<8 ){
             if(board[x][y]!=null) {
                 if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                     //x++;
                     y++;
                     count++;
                 } else break;
             }
             else{
                 count=tmp_count;
                 break;
             }
        }


        x=a.row()+1;
        y=a.col();
        tmp_count=count;
        //down
        while (x<8 && y<8  ){
            if(board[x][y]!=null) {
                if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                    x++;
                    //y++;
                    count++;
                } else break;
            }
            else{
                count=tmp_count;
                break;
            }

        }


        x=a.row();
        y=a.col()-1;
        tmp_count=count;
        //left
        while (x>0 && y>0 ){
            if(board[x][y]!=null) {
                if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                    // x--;
                    y--;
                    count++;
                } else break;
            }
            else{
                count=tmp_count;
                break;
            }
        }

        tmp_count=count;
        x=a.row()-1;
        y=a.col();
        //up
        while (x>(-1) && y>(-1)  ){
            if(board[x][y]!=null) {
                if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                    x--;
                    //y--;
                    count++;
                } else break;
            }
            else{
                count=tmp_count;
                break;
            }
        }


        tmp_count=count;
        x=a.row()+1;
        y=a.col()+1;
        //right down
        while (x<8 && y<8) {
            if(board[x][y]!=null) {
                if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                    x++;
                    y++;
                    count++;
                } else break;
            }
            else{
                count=tmp_count;
                break;
            }
        }


        tmp_count=count;
        x=a.row()-1;
        y=a.col()-1;
        //left up
        while (x>(-1) && y>(-1)  ){
            if(board[x][y]!=null) {
                if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                    x--;
                    y--;
                    count++;
                } else break;
            }
            else{
                count=tmp_count;
                break;
            }
        }


        tmp_count=count;
        x=a.row()+1;
        y=a.col()-1;
        //left down
        while (x<8 && y>(-1) ){
            if(board[x][y]!=null) {
                if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                    x++;
                    y--;
                    count++;
                } else break;
            }
            else{
                count=tmp_count;
                break;
            }
        }


        tmp_count=count;
        x=a.row()-1;
        y=a.col()+1;
        //right up
        while (x>(-1) && y<8 ){
            if(board[x][y]!=null) {

                if (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne) {
                    x--;
                    y++;
                    count++;
                } else break;
            }
            else{
                count=tmp_count;
                break;
            }

        }

             System.out.println(count);
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
//       player1.isHuman();
//       player2.isHuman();
    }

    @Override
    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;

    }

    @Override
    public boolean isGameFinished() {
       //maybe liss of valid moves == empty
        return false;
    }

    @Override
    public void reset() {
        board =new Disc[8][8];
        board[3][3] = new SimpleDisc(player1);
        board[4][4] = new SimpleDisc(player1);
        board[3][4] =new  SimpleDisc(player2);
        board[4][3] =new  SimpleDisc(player2);
        curent=player1;

        neighbor[2][2]=true;
        neighbor[2][3]=true;
        neighbor[2][4]=true;
        neighbor[2][5]=true;
        neighbor[3][2]=true;
        neighbor[3][5]=true;
        neighbor[4][2]=true;
        neighbor[4][5]=true;
        neighbor[5][2]=true;
        neighbor[5][3]=true;
        neighbor[5][4]=true;
        neighbor[5][5]=true;
    }

    @Override
    public void undoLastMove() {

    }


}


