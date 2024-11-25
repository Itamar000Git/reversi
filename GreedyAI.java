import java.util.ArrayList;
import java.util.Comparator;

public class GreedyAI extends AIPlayer{
    ArrayList <Position> arr = new ArrayList<>();
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
        this.isPlayerOne=isPlayerOne;
    }

    /**
     * The greedy player needs to choose the position that flips the most discs.
        1.First make a move use few compares to choose witch position,
        first the most flips, after that highest columns and apter that the highest row.
        2."arr" array list contain all valid moves, we are soring arr with that compares and taking the last one, and creating the move
     * @param gameStatus
     * @return
     */
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
