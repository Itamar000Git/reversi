import java.util.Comparator;

public class compareFlips implements Comparator<Position>{
    public PlayableLogic gameLogic;

    public void game(PlayableLogic gameStat){
        this.gameLogic=gameStat;
    }
    @Override
    public int compare(Position p1, Position p2) {

       // GameLogic gameLogic = new GameLogic();
        int c1=gameLogic.countFlips(p1);
        int c2=gameLogic.countFlips(p2);

        if (c1>c2){return 1;}
        else if (c1<c2) {return -1;}
        else return 0;
    }
    public int co (){

        return 0;
    }

}
