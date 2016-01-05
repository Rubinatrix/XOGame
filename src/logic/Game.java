package logic;

import java.util.AbstractMap.SimpleImmutableEntry;

public class Game {
    
    final private Table table;
    final private AI ai;
    private CellValue userValue;
    private CellValue computerValue;
    private CellValue winnerValue;
    private boolean started;
    private boolean finished;

    public Game(CellValue userValue, CellValue computerValue) {
        this.table = new Table(3);
        this.userValue = userValue;
        this.computerValue = computerValue;
        this.started = false;
        this.finished = false;
        this.ai = new AI(this);
    }

    public void start() throws XOGameException{
        this.started = true;
        this.finished = false;
        this.winnerValue = CellValue.NONE;
        if (this.userValue == CellValue.VALUE_O){
            doComputerStep();
        }
    }
    
    public void finish(CellValue winnerValue){
        this.started = false;
        this.finished = true;
        this.winnerValue = winnerValue;
    }
    
    //The game started, but not finished
    public boolean isActive(){
        return started && !finished;
    }

    public CellValue getUserValue() {
        return userValue;
    }

    public void setUserValue(CellValue userValue) {
        this.userValue = userValue;
    }

    public CellValue getComputerValue() {
        return computerValue;
    }

    public void setComputerValue(CellValue computerValue) {
        this.computerValue = computerValue;
    }

    public Table getTable() {
        return table;
    }
    
    public void doUserStep(int posHorizontal, int posVertical) throws XOGameException{
        doStep(userValue, posHorizontal, posVertical);
    }
    
    public void doComputerStep() throws XOGameException{
        
        SimpleImmutableEntry position = ai.getCurrentComputerStep();
        if (position != null) {
            doStep(computerValue, (int) position.getKey(), (int) position.getValue());
        } else {
            throw new XOGameException("Unknown error. Unable to compute the next computer step. Game aborted");
        }
  
    }
    
    public String getGameStatus(){
        String textStatus;
        if (started) {
            textStatus = "Game started. You symbol is " + userValue.getTextValue();
        } else if (finished) {
            if (winnerValue == userValue) {
                textStatus = "Game finished: you have won!";
            } else if (winnerValue == computerValue) {
                textStatus = "Game finished: computer have won!";
            } else {
                textStatus = "Game finished: draw";
            }
        } else {
            textStatus = "Press 'New Game' to start playing";
        }
        return textStatus;
    }
    
    private void doStep(CellValue value, int posHorizontal, int posVertical) throws XOGameException{
        
        if (!isActive()) {
            throw new XOGameException("Unable to do steps if game isn't active");
        } 
        
        table.setCellValue(value, posHorizontal, posVertical);
        
        if (CheckHelper.hasFullLine(table)) {
            finish(value);
        } else if (table.getEmptyCellsCount() == 0) {
            finish(CellValue.NONE);
        }
        
    }
    
}
