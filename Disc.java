/**
 * The Disc interface defines the characteristics of a game in a chess-like game.
 * Implementing classes should provide information about the player who owns the Disc.
 */
public interface Disc {

    /**
     * Get the player who owns the Disc.
     *
     * @return The player who is the owner of this game disc.
     */
    Player getOwner();

    /**
     * Set the player who owns the Disc.
     *
     */
    void setOwner(Player player);

    /**
     * Get the type of the disc.
     * use the:
     *          "⬤",         "⭕"                "💣"
     *      Simple Disc | Unflippedable Disc | Bomb Disc |
     * respectively.
     */
    String getType();
    Player lastOwner(String peekORpop); //for getting the last owner
    void addOwner(Player p); //for update owners stack
    boolean getBoom(); //for knowing if this bomb explode
    void setBoom(boolean b); //setting boom variable


}