import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class WinnerAI extends AIPlayer{
    ArrayList <Position> arr = new ArrayList<>();
    ArrayList<Position> corners_Arr = new ArrayList<>();
    public int move_counter;
    public WinnerAI(boolean isPlayerOne) {
        super(isPlayerOne);
        this.isPlayerOne=isPlayerOne;
        this.move_counter=12;
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
    //take the corners and slide around
    //start with small flips 10 first moves
    //to be centered around opponent discs.

        //if there is unflippable in this round allready

        compareFlips compFlip = new compareFlips();
        compareRows rowComp = new compareRows();
        compareColluns collComp = new compareColluns();
        Comparator<Position> myComp = compFlip.thenComparing(rowComp).thenComparing(collComp);
        compFlip.game(gameStatus);
//        compareRows rowComp = new compareRows();
//        compareColluns collComp = new compareColluns();


        arr.clear();
        arr.addAll(gameStatus.ValidMoves());
        arr.sort(myComp);

        Position corner =openCorner(gameStatus.ValidMoves()); //check if there is an available corner
        Position round_end=roundEnd(gameStatus.ValidMoves());
        Position p=cornerStrategy(gameStatus.ValidMoves());
        if (corner!=null){
            corners_Arr.add(corner);
            Disc disc = new SimpleDisc(this);
            Move move = new Move(disc,corner);
            move_counter--;
            return move;
        } else if (!corners_Arr.isEmpty()& p!= null) {
                Disc disc = new SimpleDisc(this);
                Move move = new Move(disc,p);
                move_counter--;
                return move;
//        }  else if (round_end!=null) {
//            Disc disc;
//            if (this.number_of_unflippedable>0) {
//                disc = new UnflippableDisc(this);
//                Move move = new Move(disc, round_end);
//                move_counter--;
//                return move;
//            }
        } else if (move_counter>0) {
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

    public Position roundEnd(List <Position> arr){
        for ( int i=0;i<arr.size();i++){ //in this point we cant ce on the corner
            int x=arr.get(i).row();
            int y=arr.get(i).col();
            if(x==0||x==7||y==0||y==7 ){
                return arr.get(i);
            }
        }
        return null;

    }
    public Position cornerStrategy(List <Position> arr){
        int[][] array =  {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
//        for (int i=0; i<corners_Arr.size(); i++){
//            Position p=corners_Arr.get(i);
//            for (int j=0 ; j<8 ;j++) {
//                int x = p.row() + array[j][0], y = p.col() + array[j][1];
//                Position p1 = new Position(x, y);
//
//                if ( GameLogic.isInBounds(p1)) {
//                    for (int k=0;k<arr.size();k++){
//                        if (arr.get(k).row()==x & arr.get(k).col()==y){
//                            corners_Arr.add(p1);
//                            return p1;
//                        }
//                    }
//                }
//            }
//        }
        return null;
    }
}
