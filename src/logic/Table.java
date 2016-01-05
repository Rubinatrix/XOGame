package logic;

public class Table {
    
    final private int size;
    final private CellValue[][] values;
    private int emptyCellsCount;

    public Table(int size) {
        this.size = size;
        this.values = new CellValue[size][size];
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                this.values[i][j] = CellValue.NONE;
            }
        }
        emptyCellsCount = size * size;
    }
    
    public void setCellValue(CellValue value, int posHorizontal, int posVertical) throws XOGameException{
        if ((value == null) || (value == CellValue.NONE)) {
            throw new XOGameException("Unexpected cell value");
        } else if ((posHorizontal >= size) || (posVertical >= size) || (posHorizontal <0) || (posVertical < 0)) {
            throw new XOGameException("Wrong cell position");
        } else if (values[posHorizontal][posVertical] != CellValue.NONE) {
            throw new XOGameException("Cell position (" + (posHorizontal+1) + "," + (posVertical+1) + ") is already used");
        }
        values[posHorizontal][posVertical] = value;
        emptyCellsCount--;
    }
    
    public CellValue getCellValue(int posHorizontal, int posVertical) {
        return values[posHorizontal][posVertical];
    }

    public CellValue[][] getValues() {
        return values;
    }

    public int getSize() {
        return size;
    }
    
    public int getEmptyCellsCount() {
        return emptyCellsCount;
    }
    
}
