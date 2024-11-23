import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class WinnerAI extends AIPlayer{
    ArrayList <Position> arr = new ArrayList<>();
    public int move_counter;
    public WinnerAI(boolean isPlayerOne) {
        super(isPlayerOne);
        this.isPlayerOne=isPlayerOne;
        this.move_counter=10;
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
    //take the corners and slide around
    //start with small flips 10 first moves
    //to be centered around opponent discs
        Random rand = new Random();
        int rendomDisc;
        compareFlips compFlip = new compareFlips();
        Comparator<Position> myComp =   compFlip;
        compFlip.game(gameStatus);
//        compareRows rowComp = new compareRows();
//        compareColluns collComp = new compareColluns();


        arr.clear();
        arr.addAll(gameStatus.ValidMoves());
        arr.sort(myComp);

        Position p =openCorner(gameStatus.ValidMoves()); //check if there is an available corner
        if (p!=null){
            Disc disc = new SimpleDisc(this);
            Move move = new Move(disc,p);
            move_counter--;
            return move;
        }else if(move_counter>0){
            Position pos=new Position(arr.getFirst().row(),arr.getFirst().col());
            Disc disc = new SimpleDisc(this);
            Move move = new Move(disc,pos);
            move_counter--;
            return move;
        }
            Position greedyPos=new Position(arr.getLast().row(),arr.getLast().col());
            Disc disc = new SimpleDisc(this);
            Move move = new Move(disc,greedyPos);
        return move;
    }

    public Position openCorner(List <Position> arr){
        Position cor1 = new Position(0,0);
        Position cor2 = new Position(0,7);
        Position cor3 = new Position(7,0);
        Position cor4 = new Position(7,7);
        for (int i=0;i<arr.size();i++){
            if (arr.get(i).row()==cor1.row() & arr.get(i).col()==cor1.col()){
                return cor1;
            }
            if (arr.get(i).row()==cor2.row() & arr.get(i).col()==cor2.col()){
                return cor2;
            }
            if (arr.get(i).row()==cor3.row() & arr.get(i).col()==cor3.col()){
                return cor3;
            }
            if (arr.get(i).row()==cor4.row() & arr.get(i).col()==cor4.col()){
                return cor4;
            }
        }
    return null;
    }
}
