import java.util.ArrayList;

public class Move {
    private Disc disc;
    private Position position;
    //public  static Disc[][] Dboard;
    private static int counter = 0;

    public Position position(){
        return position;
    }

    public Disc disc(){
        return disc;
    }

    public Move(Disc disc) {
        this.disc = disc;
    }


    public static boolean MakeMove(Disc d){
        //Dboard[position().row][position().col]=d;
        System.out.println("the disc dit:" + d.getType());
        return true;
    }

    public static boolean Move_counter(){
        if (counter % 2==0){
            return true;
        }
        return false;
    }


    //counter++; //need to add in any simple move;


}
