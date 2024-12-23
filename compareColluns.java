import java.util.Comparator;

public class compareColluns implements Comparator<Position> {
    /**
     * Compare between two position columns.
     * @param p1 the first object to be compared.
     * @param p2 the second object to be compared.
     * @return 1 if the first one bigger, -1 if the second one bigger, 0 if equal.
     */
    @Override
    public int compare(Position p1, Position p2) {
        if(p1.col()>p2.col()){return 1;}
        else if (p1.col()<p2.col()) {return -1;}
        else  return 0;
    }
}
