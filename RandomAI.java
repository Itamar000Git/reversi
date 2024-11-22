import java.util.Random;
public class RandomAI extends AIPlayer{
    private  AIPlayer current;
    private boolean isPlayerOne;
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);

        this.isPlayerOne=isPlayerOne;
        registerAIPlayerType("RandomAI",RandomAI.class);

    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        this.current=new RandomAI(isPlayerOne);
        Random rand = new Random();
        int rendomDisc;
        if (current.number_of_bombs>0){
            if (current.number_of_unflippedable>0){
                 rendomDisc= rand.nextInt(3);
            }
            else {
                 rendomDisc= rand.nextInt(2);
            }
        } else if (current.number_of_unflippedable>0) {
             rendomDisc= rand.nextInt(1,3);
        }
        else {
             rendomDisc= 2;
        }

        int valid = rand.nextInt(gameStatus.ValidMoves().size());
        Disc disc = chooseDisc(rendomDisc);
        Position pos =(gameStatus.ValidMoves().get(valid));

        Move move = new Move(disc,pos);

        return move;
    }
    private Disc chooseDisc(int c){
        Disc d;
        if(c==0){
                d = new BombDisc(current);
                current.reduce_bomb();

        } else if (c==1) {
            d=new SimpleDisc(current);
        }
        else {
            d=new UnflippableDisc(current);
            current.reduce_unflippedable();
        }
        return d;
    }

}
