import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class minMaxAI extends AIPlayer{
    ArrayList <Position> arr = new ArrayList<>();
    ArrayList<Position> corners_Arr = new ArrayList<>();
    public int move_counter;
    public minMaxAI(boolean isPlayerOne) {
        super(isPlayerOne);
        this.isPlayerOne=isPlayerOne;
        this.move_counter=8;
    }

    /**
     * The "makeMove" of "minMaxAI" preform smart player that supposed to win the random player 10:1.
     * After read few strategy's the decision order is:
        1. Take the corners - it's the bast spots.
        2. The edges are dangerous - placed unflippable when it's possible.
        3. Stay centered - flip small amounts of disc in the first rounds.
        4. Use the greedy strategy until the end.
     (I didn't use prediction's but still I got pretty good ratio - around 1.5:10. hope that good enough)
     * @param gameStatus
     * @return
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random rand = new Random();
        int test=rand.nextInt(3);
        System.out.println("Winner: "+ test);

        compareFlips compFlip = new compareFlips();
        compareRows rowComp = new compareRows();
        compareColluns collComp = new compareColluns();
        Comparator<Position> myComp = compFlip.thenComparing(rowComp).thenComparing(collComp);
        compFlip.game(gameStatus);


        arr.clear();
        arr.addAll(gameStatus.ValidMoves());
        arr.sort(myComp);

        Position corner =openCorner(gameStatus.ValidMoves()); //check if there is an available corner
        Position round_end=roundEnd(gameStatus.ValidMoves());

        if (corner!=null){              //take the corner
            corners_Arr.add(corner);
            Disc disc = new SimpleDisc(this);
            Move move = new Move(disc,corner);
            move_counter--;
            return move;
        }   else if (round_end!=null & this.number_of_unflippedable>0) { //unflippable in the sides
            Disc disc;
                disc = new UnflippableDisc(this);
                Move move = new Move(disc, round_end);
                move_counter--;
                return move;
        } else if (move_counter>=3) {  //close to center
            Position pos=new Position(arr.getFirst().row(),arr.getFirst().col());
            Disc disc = new SimpleDisc(this);
            Move move = new Move(disc,pos);
            move_counter--;
            return move;
        } else if (this.number_of_bombs>0&move_counter>=0) {
                Position pos = new Position(arr.getFirst().row(), arr.getFirst().col());
                Disc disc = new BombDisc(this);
                Move move = new Move(disc, pos);
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
           //
            if(x==0||x==7||y==0||(y==7)){
                return arr.get(i);
            }
        }
        return null;

    }
}
