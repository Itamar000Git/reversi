import java.util.Random;
public class RandomAI extends AIPlayer{
    private  AIPlayer current;
    private boolean isPlayerOne;
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);

        this.isPlayerOne=isPlayerOne;
        //this.reset_bombs_and_unflippedable();
        //registerAIPlayerType("RandomAI",RandomAI.class);


    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
       // this=new RandomAI(isPlayerOne);
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
             rendomDisc= rand.nextInt(2)+1;
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
               // this.reduce_bomb();

        } else if (c==1) {
            d=new SimpleDisc(this);
        }
        else {
            d=new UnflippableDisc(this);
           // this.reduce_unflippedable();
        }
        return d;
    }

}
