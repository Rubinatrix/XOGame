package main;

import form.MainGameForm;
import logic.CellValue;
import logic.Game;
import logic.XOGameException;

public class GameController {
    
    private final MainGameForm form;
    private Game game;

    public GameController(Game game, MainGameForm form) {
        this.game = game;
        this.form = form;
    }
    
    public void onPressStartGame(boolean radioXSelected){
        
        if (radioXSelected) {
            game = new Game(CellValue.VALUE_X, CellValue.VALUE_O);
        } else {
            game = new Game(CellValue.VALUE_O, CellValue.VALUE_X);
        }
        
        try {
            game.start();
            form.refreshView(game.getTable(), game.getGameStatus());
        } catch (XOGameException e) {
            game.finish(CellValue.NONE);
            form.showError(e.getMessage());
        }
        
    }
    
    public void onPressTableButton(int posHorizontal, int PosVertical){
        if (ExecuteUserStep(posHorizontal, PosVertical)) {
            ExecuteComputerStep();
        }
    }
    
    private boolean ExecuteUserStep(int posHorizontal, int PosVertical){
        if (game!=null) {
            try {
                game.doUserStep(posHorizontal, PosVertical);
                form.refreshView(game.getTable(), game.getGameStatus());
                return true;
            } catch (XOGameException e) {
                form.showError(e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }
    
    private void ExecuteComputerStep(){
        if ((game!=null) && game.isActive()) {
            try {
                game.doComputerStep();
                form.refreshView(game.getTable(), game.getGameStatus());
            } catch (XOGameException e) {
                game.finish(CellValue.NONE);
                form.showError(e.getMessage());
            }
        }
    }
    
}
