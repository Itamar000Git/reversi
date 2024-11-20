
public class SimpleDisc implements Disc {
    private Player curentplayer;

public SimpleDisc(Player cur){
    this.curentplayer = cur;
}

    @Override
    public Player getOwner() {
    return curentplayer;
    }

    @Override
    public void setOwner(Player player) {
        curentplayer =player;
    }

    @Override
    public String getType() {
        return "⬤";
    }
}
