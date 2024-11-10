public class Move {
    public Move move;
    private static int counter = 0;

    public Move(Move move) {
        this.move = move;
    }
    public static boolean Move_counter(){
        if (counter % 2==0){
            return true;
        }
        return false;
    }


    //counter++; //need to add in any simple move;


}
