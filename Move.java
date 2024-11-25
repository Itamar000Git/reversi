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

    /**
     *The "MakeMove" function place the given disc in the given position.
        1."MakeMove" checks what type of disc is it and according to this reducing number of bombs and unflippable disc,
        and also printing the correct outputs.
     * @param d
     * @param bo
     * @param p
     * @return
     */
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

    /**
     * This function adds to an array list all the position that need to be flip.
     * @param p
     */
    public void addToPos(ArrayList<Position> p){
        posArr.addAll(p);
    }

    /**
     * The "undo" function is a function that help to undo last move, this function preform the "flipping back" part.
     * For each position in "posArr" undo pop out the last owner and set him to be the new present owner.
     * @param board
     * @return board after updated owners.
     */
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
