import java.util.ArrayList;

public class Move {
    public Disc disc;
    //public  static Disc[][] Dboard;
    private static int counter = 0;

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
    public static Position position(){//??????

        return new Position(1,1);

    }

    public Disc disc(){ //??????
    return disc;
    }


    //counter++; //need to add in any simple move;


}
