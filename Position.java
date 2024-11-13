//can be change




public class Position {
   // public boolean [][] board=new boolean[8][8];
    public static int[] position= new int [2];
    public int row;
    public int col;

public Position(int row, int col) {
    this.row=row;
    this.col=col;
    position[0]=row;
    position[1]=col;
}

//public void fill(Position pos) {
//   board[pos.row][pos.col]=true;
//}


public int row(){
    return row;
}
public int col(){
    return col;
}








}
