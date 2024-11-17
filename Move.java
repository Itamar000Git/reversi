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



        if(d.getType().equals("⭕")){
            bo[p.row][p.col]=new UnflippableDisc(cur);
            cur.reduce_unflippedable();
        } else if (d.getType().equals("💣")) {
            bo[p.row][p.col]=new BombDisc(cur);
            cur.reduce_bomb();
        }
        else {
            bo[p.row][p.col] = new SimpleDisc(cur);
        }


        if (cur.isPlayerOne){
            System.out.println("Player 1 placed a " + d.getType()+ " in: (" + p.row() + " , " + p.col() + ")");
        }
        else {
            System.out.println("Player 2 placed a " + d.getType()+ " in: (" + p.row() + " , " + p.col() + ")");
        }

        for (int i=0; i<GameLogic.flipper.size(); i++){
            //System.out.println("("+GameLogic.flipper.get(i).row+" , "+ GameLogic.flipper.get(i).col+")" );
            flip(GameLogic.flipper.get(i),bo);
        }

        //System.out.println("the disc is:" + d.getType());
        return true;
    }
    public  boolean flip(Position p,Disc[][] bo){
        if (bo[p.row()][p.col()].getType().equals("⭕")){
            return true;
        }
        bo[p.row()][p.col()].setOwner(cur);

        return true;
    }


    //counter++; //need to add in any simple move;


}
