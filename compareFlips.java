import java.util.Comparator;

public class compareFlips implements Comparator<Position>{
    public PlayableLogic gameLogic;
    public void game(PlayableLogic gameStat){
        this.gameLogic=gameStat;
    }

    /**
     * Compare between two positions countFlips.
     * @param p1 the first object to be compared.
     * @param p2 the second object to be compared.
     * @return 1 if the first one supposed to flip more discs , -1 if the second one supposed to flip more and 0 if equal.
     */
    @Override
    public int compare(Position p1, Position p2) {
        int c1=gameLogic.countFlips(p1);
        int c2=gameLogic.countFlips(p2);

        if (c1>c2){return 1;}
        else if (c1<c2) {return -1;}
        else return 0;
    }

}

