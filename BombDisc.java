public class BombDisc implements Disc {
    private Player current;

    public BombDisc(Player cur){

        this.current= cur;
    }

    @Override
    public Player getOwner() {
        return current;
    }

    @Override
    public void setOwner(Player player) {
    current=player;
    }

    @Override
    public String getType() {
        return  "💣";
    }
}
