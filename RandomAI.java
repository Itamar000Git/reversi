import java.util.Random;
public class RandomAI extends AIPlayer{
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);

        this.isPlayerOne=isPlayerOne;
    }

    /**
     * The makeMove function preform a random move.
     1. For choosing a random disc we need to chack first which discs are available,
        when we know that we can know what bound should the random choose.
     2. For the position the bound equal to the valid move size.
     * @param gameStatus
     * @return return the created random move.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random rand = new Random();
        int rendomDisc;
        if (this.number_of_bombs>0){
            if (this.number_of_unflippedable>0){
                 rendomDisc= rand.nextInt(3);
            }
            else {
                rendomDisc= rand.nextInt(2);
            }
        } else if (this.number_of_unflippedable>0) {
            rendomDisc=rand.nextInt(2)+1;
        }
        else {
             rendomDisc= 1;
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
            d = new BombDisc(this);

        } else if (c==1) {
            d=new SimpleDisc(this);
        }
        else {
            d=new UnflippableDisc(this);
        }
        return d;
    }

}
