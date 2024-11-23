import java.util.Stack;

public class BombDisc implements Disc {
    private Player current;
    private boolean boom;
    private Stack<Player> owners=new Stack<>();

    public BombDisc(Player cur){
        this.boom=false;
        if (cur.getNumber_of_bombs()==0){
            System.out.println("Number of bomb discs is 0 , Please try a different disc");
            throw new RuntimeException("number of bombs is 0");
        }else {
            this.current = cur;
            owners.add(cur);
        }
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
    @Override
    public Player lastOwner(String peekORpop){
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
        return boom;
    }
    @Override
    public void setBoom(boolean b){
        boom=b;
    }

}
