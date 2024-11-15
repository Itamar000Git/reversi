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
       // GameLogic.reset=false;
       // GameLogic.flipper.clear();
        cur=d.getOwner();

        for (int i=0; i<GameLogic.flipper.size(); i++){
            System.out.println("( "+GameLogic.flipper.get(i).row+" , "+ GameLogic.flipper.get(i).col+" )" );
            flip(GameLogic.flipper.get(i),bo);
        }

        if(d.getType().equals("⭕")){
            bo[p.row][p.col]=new UnflippableDisc(cur);
        } else if (d.getType().equals("💣")) {
            bo[p.row][p.col]=new BombDisc(cur);
        }
        else {
            bo[p.row][p.col] = new SimpleDisc(cur);
        }
        if (cur.isPlayerOne){
            System.out.println("This is player 1 move:");
        }
        else {
            System.out.println("This is player 2 move:");
        }
       // System.out.println(GameLogic.flipper.size());

//        GameLogic.flipper.clear();





       // GameLogic.flip(p);

        //flip(d,bo,p);

        //board[position().row][position().col]=d;
        System.out.println("the disc is:" + d.getType());
        //GameLogic.firstPlayerTurn=!GameLogic.firstPlayerTurn;
        return true;
    }
    public  boolean flip(Position p,Disc[][] bo){
        bo[p.row()][p.col()].setOwner(cur);

        return true;
    }


    //counter++; //need to add in any simple move;


}
