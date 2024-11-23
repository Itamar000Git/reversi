import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GreedyAI extends AIPlayer{
    ArrayList <Position> arr = new ArrayList<>();
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
        this.isPlayerOne=isPlayerOne;
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {


        compareColluns collComp = new compareColluns();
        compareRows rowComp = new compareRows();
        compareFlips compFlip = new compareFlips();
        Comparator<Position>  mainComp =   compFlip.thenComparing(collComp).thenComparing(rowComp);
        compFlip.game(gameStatus);
        arr.clear();
        arr.addAll(gameStatus.ValidMoves());
        arr.sort(mainComp);

        Disc disc=new SimpleDisc(this);
        Position pos = new Position(arr.getLast().row(),arr.getLast().col());

        Move move = new Move(disc,pos);
        return move;

    }



}
