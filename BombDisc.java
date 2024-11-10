public class BombDisc implements Disc {
    public BombDisc Bd;

    public BombDisc(Player cur){
        this.Bd = new BombDisc(cur);
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public void setOwner(Player player) {

    }

    @Override
    public String getType() {
        return "";
    }
}
