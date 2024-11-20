
public class Move {
    private Disc disc;
    private Position position;
    private Player cur;

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
        if(d.getType().equals("⭕")){
            bo[p.row][p.col]=new UnflippableDisc(cur);
            cur.reduce_unflippedable();
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

        for (int i=0; i<GameLogic.flipper.size(); i++){

            flip(GameLogic.flipper.get(i),bo);
        }
        return true;
    }

    public  boolean flip(Position p,Disc[][] bo){
        if (bo[p.row()][p.col()].getType().equals("⭕")){
            return true;
        }
        bo[p.row()][p.col()].setOwner(cur);

        return true;
    }

}
