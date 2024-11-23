import java.util.Stack;

public class UnflippableDisc implements Disc {
    private  Player curentplayer;
    private Stack<Player> owners=new Stack<>();

    public UnflippableDisc(Player cur) {
        if (cur.getNumber_of_unflippedable()==0){
            System.out.println("Number of unflippedable discs is 0 , Please try a different disc");
            throw new RuntimeException("number of unflippable is 0");
        }
        else {
            this.curentplayer=cur;
            owners.add(cur);
        }


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
        return false;
    }
    @Override
    public void setBoom(boolean b){
        return;
    }
}
