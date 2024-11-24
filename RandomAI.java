import java.util.Random;
public class RandomAI extends AIPlayer{
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);

        this.isPlayerOne=isPlayerOne;
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random rand = new Random();
       // int test=rand.nextInt(3);
        // System.out.println("Random: "+ test);
        //rendom rand = new rendom();
        int rendomDisc;
        if (this.number_of_bombs>0){
            if (this.number_of_unflippedable>0){
                 rendomDisc= rand.nextInt(3);
                //rendomDisc=rand.ren_num(3);

            }
            else {
                 //rendomDisc=rand.ren_num(2);
                rendomDisc= rand.nextInt(2);
            }
        } else if (this.number_of_unflippedable>0) {
            // rendomDisc= r.ren_num(2)+1;
            rendomDisc=rand.nextInt(2)+1;
        }
        else {
             rendomDisc= 1;
        }

        //int valid = r.ren_num(gameStatus.ValidMoves().size());
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
