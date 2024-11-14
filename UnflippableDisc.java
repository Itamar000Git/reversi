public class UnflippableDisc implements Disc {
    //private UnflippableDisc Ud;
    private  Player curentplayer;

    public UnflippableDisc(Player cur) {
      //  this.Ud = new UnflippableDisc(cur);
        this.curentplayer=cur;

    }

    @Override
    public Player getOwner() {

        return curentplayer;
    }

    @Override
    public void setOwner(Player player) {
        curentplayer=player;

    }

    @Override
    public String getType() {
        return "⭕";
    }
}
