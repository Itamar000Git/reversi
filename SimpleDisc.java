import java.util.Stack;

public class SimpleDisc implements Disc {
    private Player curentplayer;
    private  Stack<Player> owners=new Stack<>();

public SimpleDisc(Player cur){
    this.curentplayer = cur;
    owners.add(cur);
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

    @Override
    public  Player lastOwner(){
    return owners.pop();
    }
}
