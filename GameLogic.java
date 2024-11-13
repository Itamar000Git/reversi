//can be change

import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic {


    private Disc[][] board;  // Represents the game board
    private Player player1;
    private Player player2;
    private Player curent;
    private Disc d;
    public boolean firstPlayerTurn = true;
    public Stack <Move> move_st;

    public GameLogic(){
        this.board =new Disc[8][8];


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
        if(board[a.row][a.col] != null)       //check if the position available
            return false;
        if (countFlips(a)==0)
            return false;

        Player p = disc.getOwner();
        //if null continue else check how mutch flips?



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
        return List.of();
    }

    @Override
    public int countFlips(Position a) {
        return 1;
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
    }

    @Override
    public void undoLastMove() {

    }


}


