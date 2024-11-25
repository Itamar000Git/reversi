import java.util.Stack;

public class BombDisc implements Disc {
    private Player current;
    private boolean boom; //true if the bomb exploded in this turn
    private Stack<Player> owners=new Stack<>(); //keeps all owners record

    /**
     * This constructor create a bomb disc.
     * It's check first if there is available bombs for the current player, If not throw an exeption.
     * * @param cur
     */
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

    /**
     * Say if the bomb explode already.
     * @return
     */
    @Override
    public boolean getBoom(){
        return boom;
    }

    /**
     * Set the boom boolean to be true or false as needed.
     * @param b
     */
    @Override
    public void setBoom(boolean b){
        boom=b;
    }

}
