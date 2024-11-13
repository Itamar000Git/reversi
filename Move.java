import java.util.ArrayList;

public class Move {
    private Disc disc;
    private Position position;
    private Player cur;
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


    public boolean MakeMove(Disc d,Disc[][] bo, Position p){

        cur=d.getOwner();
        bo[p.row][p.col]=new SimpleDisc(cur);
        System.out.println(cur);

        //board[position().row][position().col]=d;
        System.out.println("the disc is:" + d.getType());
        GameLogic.firstPlayerTurn=!GameLogic.firstPlayerTurn;
        return true;
    }

    public boolean Move_counter(){
        if (counter % 2==0){
            return true;
        }
        return false;
    }


    //counter++; //need to add in any simple move;


}
