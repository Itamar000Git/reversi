import java.util.Stack;

public class SimpleDisc implements Disc {
    private Player curentplayer;
    private  Stack<Player> owners=new Stack<>();

public SimpleDisc(Player cur){
    this.curentplayer = cur;
    owners.add(cur);
//    owners.add(new HumanPlayer(cur.isPlayerOne()));
//    owners.add(new HumanPlayer(curentplayer.isPlayerOne()));
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
    public  Player lastOwner(String peekORpop){
    if(owners.size()<2){
        return null;
    }
    if(peekORpop.equals("peek")){
        return owners.peek();
    }else{
        owners.pop();
        return owners.peek();
    }

    }
    @Override
   public void addOwner(Player p){
    owners.add(p);

    }
    @Override
   public boolean getBoom(){
    return false;
   }
    @Override
    public void setBoom(boolean b){
    return;
    }

}
