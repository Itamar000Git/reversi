
public class SimpleDisc implements Disc {
    public SimpleDisc Sd;
    private Player curentplayer;



public SimpleDisc(Player cur){
    this.curentplayer = cur;
    //this.Sd=Sd;
}
//    Player player1;
//    Player player2;

    @Override
    public Player getOwner() {
//    if (new GameLogic(player1,player2).isFirstPlayerTurn())
//        return player1;
//    return player2;
    return curentplayer;
    }

    @Override
    public void setOwner(Player player) {
//       if( new GameLogic(player1,player2).isFirstPlayerTurn()){
//           new GameLogic(player1,player2).firstPlayerTurn=true;
//
//       }
        curentplayer =player;
////maybe add 1 to the move counter.
    }

    @Override
    public String getType() {
        return "⬤";
    }
}
