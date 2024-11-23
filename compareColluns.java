import java.util.Comparator;

public class compareColluns implements Comparator<Position> {

    @Override
    public int compare(Position p1, Position p2) {
        if(p1.col()>p2.col()){return 1;}
        else if (p1.col()<p2.col()) {return -1;}
        else  return 0;
    }
}
