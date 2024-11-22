import java.util.ArrayList;
import java.util.Stack;

public class Move {
    private Disc disc;
    private Position position;
    private Player cur;

    public ArrayList<Position> posArr=new ArrayList<>();

    public Position position(){
        return position;
    }

    public Disc disc(){
        return disc;
    }

    public Move(Disc disc, Position p) {
        this.disc = disc;
        this.position=p;

    }
    public Move(){

    }


    public boolean MakeMove(Disc d,Disc[][] bo, Position p){
        cur=d.getOwner();
        if(d.getType().equals("⭕")){
            bo[p.row][p.col]=new UnflippableDisc(cur);
            cur.reduce_unflippedable();
            System.out.println("Number of unflippedable is: " +cur.number_of_unflippedable);
        } else if (d.getType().equals("💣")) {
                bo[p.row][p.col]=new BombDisc(cur);
                cur.reduce_bomb();
                System.out.println("number of bombs is: " +cur.number_of_bombs);
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


        return true;
    }

   // public void addToStack(Move m){
   //     move_st.add(m);
   // }
    public void addToPos(ArrayList<Position> p){
        posArr.addAll(p);

    }

    public Disc[][] undo(Disc [][]board){

        for (int i=0;i<posArr.size();i++){
            int x=posArr.get(i).row();
            int y=posArr.get(i).col();
            System.out.println("Undo: flipping back "+board[x][y].getType()+" in ("+x+" , "+y+")");
            board[x][y].setOwner(board[x][y].lastOwner("pop"));
        }
        System.out.println("");
        return board;
    }



}
