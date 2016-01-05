package logic;

import java.util.AbstractMap.SimpleImmutableEntry;

public class AI {

    private final Game game;
    
    public AI(Game game) {
        this.game = game;
    }
    
    public SimpleImmutableEntry getCurrentComputerStep(){
        
        CellValue userValue = game.getUserValue();
        CellValue computerValue = game.getComputerValue();
        SimpleImmutableEntry position;
        
        // if there is a possibility to win - computer has to win
        position = getFinishOrAvoidDangerStep(computerValue);
        if (position != null) {
            return position;
        }
       
        // if there is danger of losing game - computer has try to defend itself
        position = getFinishOrAvoidDangerStep(userValue);
        if (position != null) {
            return position;
        }
            
        // random selection from all free cells
        return getRandomStep();
        
    }
 
    private SimpleImmutableEntry getFinishOrAvoidDangerStep(CellValue attentionValue){
        
        Table table = game.getTable();
        int size = table.getSize();
        
        // check horizontals and verticals
        for (int i=0; i<size; i++) {
            int horizontalCount = 0;
            int verticalEmptyPosition = 0;
            int verticalCount = 0;
            int horizontalEmptyPosition = 0;
            for (int j=0; j<size; j++) {
                CellValue cellValueIJ = table.getCellValue(i, j);
                CellValue cellValueJI = table.getCellValue(j, i);
                if (cellValueIJ == attentionValue) {
                    horizontalCount++;
                } else if (cellValueIJ == CellValue.NONE) {
                    verticalEmptyPosition = j;
                }
                if (cellValueJI == attentionValue) {
                    verticalCount++;
                } else if (cellValueJI == CellValue.NONE) {
                    horizontalEmptyPosition = j;
                }
            }
            if ((horizontalCount == size-1) && (table.getCellValue(i, verticalEmptyPosition) == CellValue.NONE)) {
                return new SimpleImmutableEntry<>(i, verticalEmptyPosition);
            }
            if ((verticalCount == size-1) && (table.getCellValue(horizontalEmptyPosition, i) == CellValue.NONE)) {
                return new SimpleImmutableEntry<>(horizontalEmptyPosition, i);
            }
        }
        
        // check diagonals
        int diag1Count = 0;
        int diag1EmptyPosition = 0;
        int diag2Count = 0;
        int diag2EmptyPosition = 0;
        for (int i=0; i<size; i++) {
            CellValue cellValue1 = table.getCellValue(i, i);
            CellValue cellValue2 = table.getCellValue(i, size-i-1);
            if (cellValue1 == attentionValue) {
                diag1Count++;
            } else if (cellValue1 == CellValue.NONE) {
                diag1EmptyPosition = i;
            }
            if (cellValue2 == attentionValue) {
                diag2Count++;
            } else if (cellValue2 == CellValue.NONE) {
                diag2EmptyPosition = i;
            }
        }
        if ((diag1Count == size-1) && (table.getCellValue(diag1EmptyPosition, diag1EmptyPosition) == CellValue.NONE)) {
            return new SimpleImmutableEntry<>(diag1EmptyPosition, diag1EmptyPosition);
        }
        if ((diag2Count == size-1) && (table.getCellValue(diag2EmptyPosition, size-diag2EmptyPosition-1) == CellValue.NONE)) {
            return new SimpleImmutableEntry<>(diag2EmptyPosition, size-diag2EmptyPosition-1);
        }
        
        return null;
    }
    
    private SimpleImmutableEntry getRandomStep(){
        
        Table table = game.getTable();
        int size = table.getSize();
        
        int count = 0;
        int selected = (int) (Math.random()*table.getEmptyCellsCount()) + 1;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (table.getCellValue(i, j) == CellValue.NONE){
                    count = count + 1;
                    if (count == selected) {
                        return new SimpleImmutableEntry<>(i, j);
                    }
                }
            }
        }
        
        return null;
        
    }
    
}
