
public class SimpleDisc implements Disc{
    public SimpleDisc Sd;

public SimpleDisc(Player cur){
    this.Sd = new SimpleDisc(cur);
}
    Player player1= new GameLogic().player1;
    Player player2=new GameLogic().player2;

    @Override
    public Player getOwner() {
    if (new GameLogic().isFirstPlayerTurn())

        return player1;

    return player2;
    }

    @Override
    public void setOwner(Player player) {
//maybe add 1 to the move counter.
    }

    @Override
    public String getType() {
        return "Simple";
    }
}
