//can be change

import java.util.List;

public class GameLogic implements PlayableLogic {


    private Disc[][] board;  // Represents the game board
    private Player player1;
    private Player player2;
    private boolean firstPlayerTurn = true;
    public GameLogic(){
        new GameLogic(player1,player2);
    }
  public GameLogic(Player player1, Player player2) {
      this.board = new Disc[8][8];
      this.player1 = player1;
      this.player2 = player2;

     initboard();
      this.firstPlayerTurn = true;
   }


   private void initboard() {
      int mid = board.length / 2;
       board[mid - 1][mid - 1] = new SimpleDisc(player1);
       board[mid][mid] = new SimpleDisc(player1);
       board[mid - 1][mid] =new  SimpleDisc(player2);
       board[mid][mid - 1] =new  SimpleDisc(player2);


   }



    /**
     * Check if there is disc on the board
     * @param a The position for locating a new disc on the board.
     * @param disc
     * @return
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
      if(a.board[a.row][a.col]) {
          System.out.println("This place is invalid");
          return false;
      }

      a.board[a.row][a.col]=true;
        System.out.println("new disc in:"+ a.board[a.row][a.col]);
      Move.MakeMove(disc);
      return true;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return null;
    }

    @Override
    public int getBoardSize() {

        return 64;
    }

    @Override
    public List<Position> ValidMoves() {
        return List.of();
    }

    @Override
    public int countFlips(Position a) {
        return 0;
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
        return Move.Move_counter();
    }

    @Override
    public boolean isGameFinished() {
       //maybe liss of valid moves == empty
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void undoLastMove() {

    }


}


