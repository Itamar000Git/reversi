//can be change

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.HashSet;

public class GameLogic implements PlayableLogic {


    public Disc[][] board;  // Represents the game board
    private boolean[][] neighbor;
    private Player player1;
    private Player player2;
    private Player curent;
    private Disc d;
    public static boolean firstPlayerTurn = true;
    public static ArrayList<Position> flipper=new ArrayList<>();
    public static ArrayList<Position> tmpflipper=new ArrayList<>();

    public static boolean reset;
    public ArrayList <Position> allValid = new ArrayList<>();
    public Stack<Disc[][]> boardSt;
    public Stack<Move> move_st= new Stack<>();


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
     * Check if there is disc on the board
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
        if(board[a.row][a.col] != null || !neighbor[a.row][a.col] || ((c==0) && !reset) ) {//check if the position available
            System.out.println("this move is invalid");
            return false;
        }


        Move m = new Move(disc,a);
       move_st.add(m);
        if(m.MakeMove(disc,board,a)) {
            for (int i = 0; i < flipper.size(); i++) {

                flip(GameLogic.flipper.get(i));
            }
            m.addToPos(flipper);
        }


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

    @Override
    public  List<Position> ValidMoves() {
        List <Position> my_L = new ArrayList<>();
        flipper.clear();
        tmpflipper.clear();



        for (int i=0;i<allValid.size();i++) {
            int x= allValid.get(i).row();
            int y=allValid.get(i).col();
            Position p = new Position(x,y);
           // if (neighbor[i][j] && board[i][j] == null && countFlips(p) > 0) {
            if(neighbor[x][y] && board[x][y]==null && countFlips(p)>0) {
                my_L.add(p);
            }

        }
        return my_L;
    }

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
                            //addFlipper(x, y);
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
                                //count += tmp_count;
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
     * when we here the move is valid
     * @param a
     * @return
     */
    @Override
    public int countFlips(Position a) {
        int count;
        int[][] array = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};


        Position p = new Position(a.row(),a.col());
        count=countHelper(array,p);

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
               System.out.println("Player 2 wins with: " + discs_1 + " discs! Player 1 had: " + discs_2 + " discs." );
               player2.addWin();
           }


           return true;
       }

        return false;
    }

    @Override
    public void reset() {
       // reset=true;
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
//                    if(board[i][j]!=null) {
//                        if (board[i][j].lastOwner("peek") != null) {
//                            board[i][j].setOwner(board[i][j].lastOwner("pop"));
//                        }
//                    }
                }
            }
                Move m= move_st.pop();
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
    private boolean isInBounds(Position p){
        int x = p.row(), y = p.col();
        if(x<8 & x>=0 & y<8 & y>=0){
            return true;
        }
        else return false;
    }

    private int bcHelper(int [][] a, Position p){
        int b_C=0;
        p.setBoom(true); //say that the bomb in this position bomb in this turn
        for (int i=0 ; i<8 ;i++) {
            int x = p.row() + a[i][0], y = p.col() + a[i][1];
            Position p1 = new Position(x, y);

            if ( isInBounds(p1)) {


            if (board[x][y] != null ) {
                if (board[x][y].getOwner() != curent & !board[x][y].getType().equals("⭕") & avoidDup(p1) ) {
                        if (board[x][y].getType().equals("💣")) {
                           // System.out.println("We have another bomb in line");
                            b_C+=bombCounter(x,y);
                        }
                        b_C++;
                        tmpflipper.add(p1);



                }
            }
        }


        }

        return b_C;
    }

    public int bombCounter(int x, int y) {
        int b_C = 0;
        int[][] array =  {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};


            Position p = new Position(x,y);
            b_C=bcHelper(array,p);

        return b_C;
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

}
