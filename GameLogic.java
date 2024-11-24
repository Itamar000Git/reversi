//can be change

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.HashSet;

public class GameLogic implements PlayableLogic {


    public Disc[][] board;  // Represents the game board
    private boolean[][] neighbor; //Represent the possible next moves
    private Player player1;
    private Player player2;
    private Player curent;
    public static boolean firstPlayerTurn = true;
    public static ArrayList<Position> flipper=new ArrayList<>();
    public static ArrayList<Position> tmpflipper=new ArrayList<>();
    public ArrayList <Position> allValid = new ArrayList<>(); //Represent all valid moves for current player
    public Stack<Disc[][]> boardSt; //for undo
    public Stack<Move> move_st= new Stack<>(); //for undo

    public int winnerAI_wins_AsP1=0;
    public int randomAI_wins_AsP2=0;



    /**
     * Initial game board and neighbor arrays.
     * Creat a new stack for Undo moves.
     * initialized allValid array list with all options without reset positions.
     */
    public GameLogic(){
        this.board =new Disc[8][8];
        this.neighbor=new boolean[8][8];
        this.boardSt=new Stack<>();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(!(i==3&j==3)&!(i==4&j==4)&!(i==3&j==4)&!(i==4&j==3)) {
                    allValid.add(new Position(i, j));
                }
            }
        }


    }

    /**
     * The function locate_disc needs to locate a disc on the board,
     * The function needs to check if the move is valid, create the move, flip the correct discs, and switch turns.

     1. Locate_disc First make sure that the position we choose to add our disc is valid,
        "Locate_disc" doing it by checking if the board already has a disc at the same position,
        also, check if that position is someone's neighbor and make sure that if we added that disc to this position at least one of the opponent discs will flip.
     2. When we are sure this move is valid we can create a Move object with disc and position,
        and send it to the "makeMove" function that is located in the Move class.
     3. After the move has been made we are adding that move to move stack (move_st).
         At that point, we know which discs need to be flipped (flipper ArrayList that updates in a different function),
         and we send each position to the "flip" function.
     4. In that part we are switching turns and printing information on the last move.
        Update board stack and neighbor array with the new board and new neighbors.

     * @param a The position for locating a new disc on the board.
     * @param disc
     * @return
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        flipper.clear();
        tmpflipper.clear();
        updateStack();
        int c=countFlips(a);
        if(board[a.row][a.col] != null || !neighbor[a.row][a.col] || (c==0)  ) {//check if the position available
            System.out.println("this move is invalid");
            return false;
        }

        Move m = new Move(disc,a);

        if(m.MakeMove(disc,board,a)) {
            for (int i = 0; i < flipper.size(); i++) {

                flip(GameLogic.flipper.get(i));
            }
            m.addToPos(flipper);
            move_st.add(m);
        }


       // board[a.row()][a.col()].setBoom(false);


        firstPlayerTurn=!firstPlayerTurn;
        if(isFirstPlayerTurn()){
            curent=player1;
            for (int i=0; i<flipper.size(); i++){

                System.out.println("Player 1 flipped the: "+disc.getType()+" in: ("+flipper.get(i).row+" , "+flipper.get(i).col+")" );
            }
        }
        else {
            curent = player2;
            for (int i=0; i<flipper.size(); i++){

                System.out.println("Player 2 flipped the: "+disc.getType()+" in: ("+flipper.get(i).row+" , "+flipper.get(i).col+")" );
            }
        }
        updateStack();
        neighbor_Update(a);
        System.out.println("");

      return true;

    }

    /**
     *The "flip" function role is to simply flip the disc located in the given position.

     1. First we make sure that the flip in the given position is not an "Unflippable" disc.
     2. Then we flip the disc by setting a new owner (the opponent).
     3. We are also keeping a record of the new owner for undo moves.
     * @param p
     * @return
     */
    public  boolean flip(Position p){
        if (board[p.row()][p.col()].getType().equals("⭕")){
            return true;
        }
        board[p.row()][p.col()].setOwner(curent);
        board[p.row()][p.col()].addOwner(curent);


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

    /**
     * The "ValidMoves" function adds to a new list just the valid moves, by checking 3 parameters:
     1. If that position has a true value in the neighbor array.
     2. If the board in that position doesn't already have a disc.
     3. If that position going to flip an opponent's discs, by sending that position to the "countFlips" function.
     * @return
     */
    @Override
    public  List<Position> ValidMoves() {
        List <Position> my_L = new ArrayList<>();
        flipper.clear();
        tmpflipper.clear();

        for (int i=0;i<allValid.size();i++) {
            int x= allValid.get(i).row();
            int y=allValid.get(i).col();
            Position p = new Position(x,y);
            if(neighbor[x][y] && board[x][y]==null && countFlips(p)>0) {
                my_L.add(p);
            }
        }
        return my_L;
    }

    /**
     *The "countHelper" function responsible to count all flips for a given position.
     1.The function checking all direction one by one, until we have position that tells us that we can't flip anything this way, or we can stop counting this way.
     2. There is a few ways to invalid flip count: null position in the way, first position is disc from my type,
        unflippable position with the opponent type or end of board.
     3.We are using a temporary variable and array list for count the flips because we need to check the end of that direction to understand if this move should flip something.
     4. If in the way we notice bomb disc we count that number of flips in a different function named "bombCounter".
     * @param a
     * @param p
     * @return
     */
      private int countHelper(int [][] a, Position p){
          flipper.clear();
        int count=0, tmp_count=0;
        for (int i=0 ; i<8 ;i++) {
            int x = p.row() + a[i][0], y = p.col() + a[i][1];
            if(x!=(-1) & x!=8 & y!=(-1)& y!=8) {
                tmp_count=0;
                tmpflipper.clear();
                while (isInBounds(p) & board[x][y] != null) {

                        if (board[x][y].getOwner() != curent & !board[x][y].getType().equals("⭕")) {
                            if (avoidDup(new Position(x,y))){
                                tmp_count++;
                                tmpflipper.add(new Position(x,y));
                            }


                        }
                        if (board[x][y].getType().equals("💣") && (board[x][y].getOwner().isPlayerOne != curent.isPlayerOne)) {
                            tmpflipper.add(new Position(x,y));//
                            tmp_count += bombCounter(x, y);
                        }
                        if (board[x][y].getOwner() == curent & tmp_count>0) {

                            copyFlipper();
                            count+=tmp_count;
                            break;
                        }else if(board[x][y].getOwner()==curent){
                            tmpflipper.clear();
                            break;
                        }
                        x += a[i][0];
                        y += a[i][1];
                        if (isInBounds(new Position(x, y))) {
                            if (board[x][y] == null) {
                                tmpflipper.clear();
                                break;
                            }
                        }
                        else {
                            tmp_count=0;
                            tmpflipper.clear();
                            break;
                        }
                }
            }
        }
        return count;
    }

   private void copyFlipper(){
        for (int i=0; i<tmpflipper.size();i++){
          addFlipper(tmpflipper.get(i));
        }
   }


    /**
     * The "countFlips" function role is to return the number of flips each position can flip, if a disc will add in that position.
        1. Creat an array that includes all "directions" in a bord.
        2. Call the "countHelper" function with the array and a deep copy of the given position.
     * when we here the move is valid
     * @param a
     * @return
     */
    @Override
    public int countFlips(Position a) {
        int[][] array = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        Position p = new Position(a.row(),a.col());
        return  countHelper(array,p);
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

    /**
     *
     * @return
     */
    @Override
    public boolean isGameFinished() {
       if(ValidMoves().isEmpty()){
           int discs_1=0, discs_2=0;
           for(int i=0; i<8; i++){
               for(int j=0; j<8; j++){
                   if (board[i][j]!=null){
                       if(board[i][j].getOwner().isPlayerOne){
                           discs_1++;
                       }
                       else discs_2++;
                   }
               }
           }
           if (discs_1>discs_2){
               System.out.println("Player 1 wins with: " + discs_1 + " discs! Player 2 had: " + discs_2 + " discs." );

               player1.addWin();
           } else if (discs_2>discs_1) {
               System.out.println("Player 2 wins with: " + discs_2 + " discs! Player 1 had: " + discs_1 + " discs." );

               player2.addWin();
           }
           System.out.println("Total:");
           System.out.println("\tPlayer1 - RandomAI with: "+player1.wins);
           System.out.println("\tPlayer2 - WinnerAI with: "+ player2.wins);
           System.out.println("");


           return true;
       }

        return false;
    }

    /**
     *
     */
    @Override
    public void reset() {

        board =new Disc[8][8];
        board[3][3] = new SimpleDisc(player1);
        board[4][4] = new SimpleDisc(player1);
        board[3][4] =new  SimpleDisc(player2);
        board[4][3] =new  SimpleDisc(player2);
        updateStack();
        curent=player1;
        firstPlayerTurn=true;

        player1.number_of_bombs = Player.initial_number_of_bombs;
        player2.number_of_bombs = Player.initial_number_of_bombs;
        player1.number_of_unflippedable = Player.initial_number_of_unflippedable;
        player2.number_of_unflippedable = Player.initial_number_of_unflippedable;

        for (int i=2; i<6;i++){
            for( int j=2; j<6;j++){
                neighbor[i][j]=true;
            }
        }

    }

    /**
     *
     * @param A
     * @param B
     * @return
     */
    private boolean updateHelper(Disc[][] A,Disc[][] B){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (A[i][j]!=B[i][j]){
                    return false;
                }
            }
            }
        return true;
    }

    /**
     *
     */
    private void updateStack(){
        if(boardSt.empty()){
            boardSt.add(new Disc[8][8]);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardSt.getLast()[i][j]= board[i][j];
                }
            }
        }
       else if(!updateHelper(boardSt.getLast(),board)) {
            boardSt.add(new Disc[8][8]);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardSt.getLast()[i][j]= board[i][j];
                }
            }

        }
    }
/**
 *
 */
    @Override
    public void undoLastMove() {

        System.out.println("Undoing last move:");

       // updateStack();
        if(!boardSt.empty()) {
            boardSt.pop();//take out this move

            if (!boardSt.empty()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                        board[i][j] = boardSt.peek()[i][j]; //reload the last move
                }
            }
                Move m= move_st.pop();
                if (m.disc().getType()=="💣"){
                    if (curent.isPlayerOne()){
                        player2.number_of_bombs++;
                    }
                    else {
                        player1.number_of_bombs++;
                    }
                }
                if (m.disc().getType()=="⭕"){
                    if (curent.isPlayerOne()){
                        player2.number_of_unflippedable++;
                    }
                    else {
                        player1.number_of_unflippedable++;
                    }
                }
                System.out.println("Undo: removing "+m.disc().getType()+" from: ("+m.position().row()+" , "+m.position().col()+")");
                m.undo(board);

                firstPlayerTurn=!firstPlayerTurn;
                if(isFirstPlayerTurn()) {
                    curent = player1;
                }
                else{
                    curent=player2;
                }

             //   ValidMoves();

        }
            else {
                System.out.println("\tNo previous move available to undo.");


               // reset();
            }
        }


    }

    /**
     *
     * @param p
     * @return
     */
    private boolean avoidDup(Position p){
        int x=p.row(),y=p.col();
        for(int j=0; j<tmpflipper.size();j++){
            if(tmpflipper.get(j).col()==y && tmpflipper.get(j).row()==x){
                return false;
            }
        }

        for (int i=0;i<flipper.size();i++){
            if(flipper.get(i).col()==y && flipper.get(i).row()==x){
                return false;
            }
        }
        return true;

    }

    /**
     *
     * @param p
     * @return
     */
    private static boolean addFlipper(Position p){
        int x =p.row();
        int y= p.col();
        for (int i=0; i<flipper.size();i++){
            if (flipper.get(i).row()==x &&flipper.get(i).col()==y){
                return false;
            }
        }
            flipper.add(p);
        return true;
    }

    /**
     *
     * @param p
     * @return
     */
    public static boolean isInBounds(Position p){
        int x = p.row(), y = p.col();
        if(x<8 & x>=0 & y<8 & y>=0){
            return true;
        }
        else return false;
    }

    /**
     *
     * @param a
     * @param p
     * @return
     */
    private int bcHelper(int [][] a, Position p){
        int b_C=0;
        p.setBoom(true); //say that the bomb in this position bomb in this turn
        for (int i=0 ; i<8 ;i++) {
            int x = p.row() + a[i][0], y = p.col() + a[i][1];
            Position p1 = new Position(x, y);

            if ( isInBounds(p1)) {


            if (board[x][y] != null ) {
                if (board[x][y].getOwner() != curent & !board[x][y].getType().equals("⭕") & avoidDup(p1)&!board[x][y].getBoom()) {
                   //
                    if (board[x][y].getType().equals("💣")) {

                           // System.out.println("We have another bomb in line");
                            b_C+=bombCounter(x,y);
                            //board[x][y].setBoom(true);
                        }
                        b_C++;
                        tmpflipper.add(p1);



                }
            }
        }


        }

        return b_C;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public int bombCounter(int x, int y) {
        int b_C = 0;
        int[][] array =  {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};


            Position p = new Position(x,y);
            board[x][y].setBoom(true);
            b_C=bcHelper(array,p);
            board[x][y].setBoom(false);

        return b_C;
    }

    /**
     *
     * @param a
     */
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

}
