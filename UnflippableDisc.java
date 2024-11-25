import java.util.Stack;

public class UnflippableDisc implements Disc {
    private  Player curentplayer;
    private Stack<Player> owners=new Stack<>();

    /**
     * This constructor create a unflippable disc.
     * It's check first if there is available unflippable discs for the current player, If not throw an exeption.
     * @param cur
     */
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

    /**
     * The "lastOwner" function return the last owner for given position.
     * This function gives the option to peek or pop the stack.
     * @param peekORpop
     * @return
     */
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
