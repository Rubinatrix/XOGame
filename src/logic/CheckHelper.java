package logic;

// contains static methods for checking game conditions
public class CheckHelper {
    
    public static boolean hasFullLine(Table table){
        
        CellValue[][] values = table.getValues();
        int size = table.getSize();
        
        // check horizontals and verticals
        for (int i = 0; i < size; i++) {
            boolean HorizontalWin = true;
            boolean VerticalWin = true;
            for (int j = 0; j < size-1; j++) {
                HorizontalWin &= ((values[i][j] == values[i][j+1]) & (values[i][j]!=CellValue.NONE));
                VerticalWin &= ((values[j][i] == values[j+1][i]) & (values[j][i]!=CellValue.NONE));
            }
            if (HorizontalWin || VerticalWin) {
                return true;
            }
        }

        // check diagonals
        boolean diag1 = true;
        boolean diag2 = true;
        for (int i=0; i<size-1; i++) {
            diag1 &= ((values[i][i] == values[i+1][i+1]) & (values[i][i]!=CellValue.NONE));
            diag2 &= ((values[i][size-i-1] == values[i+1][size-i-2]) & (values[i][size-i-1]!=CellValue.NONE));
        }
        if (diag1 || diag2) {
            return true;
        }
        
        // game is still active
        return false;
        
    }
    
}
