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
    /**
     * The "lastOwner" function return the last owner for given position.
     * This function gives the option to peek or pop the stack.
     * @param peekORpop
     * @return
     */
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
    /**
     * The "addOwner" function adding an owner to the disc owners stack.
     * @param p
     */
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
