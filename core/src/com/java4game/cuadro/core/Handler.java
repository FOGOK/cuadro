package com.java4game.cuadro.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.java4game.cuadro.IServices;
import com.java4game.cuadro.core.uiwidgets.ButtonActions;
import com.java4game.cuadro.core.uiwidgets.StageButton;
import com.java4game.cuadro.core.usie.BlockCounter;
import com.java4game.cuadro.core.usie.GPauseUI;
import com.java4game.cuadro.core.usie.MenuUI;
import com.java4game.cuadro.core.usie.TypeGameBottomBar;
import com.java4game.cuadro.utils.Localization;

/**
 * Created by java4game and FOGOK on 10.09.2016 23:15.
 * Если ты это читаешь, то знай, что этот код хуже
 * кожи разлагающегося бомжа лежащего на гнилой
 * лавочке возле остановки автобуса номер 985
 */
public class Handler {

    /**Класс, который отвечает за обработку всей игры
     *
     * */

    public static boolean isBackPressed;

    //game
    private LevelGen levelGen;
    private GPauseUI pauseUI;

    public static boolean ISPAUSE;
    public static boolean ISRESTART;
    public static boolean REFRESHLANG;
    ///

    //ui
//    GameUI gameUi;

    private BlockCounter blockCounter;
    private MenuUI menuUI;
    private IServices iServices;
    ///

    public static State state;

    public enum State{
        Game, Pause, Menu
    }

    public static String TEST_STRING;

    public Handler(){
        ISPAUSE = ISRESTART = false;

        DialogSystem.LEARNING_PART = 0;


        isBackPressed = false;
        menuUI = new MenuUI();
        blockCounter = new BlockCounter();
        blockCounter.setCountBlocks(20);
        if (!MenuUI.TEST)
            state = State.Menu;
        else{
            TypeGameBottomBar.SELECTED_BTN = TypeGameBottomBar.TYPE_STEPS;
            state = State.Game;
            StageButton.LEVEL = 2;
            levelGen = new LevelGen(menuUI);
            pauseUI = new GPauseUI(levelGen.getFieldBounds().getY());
        }

//        state = State.Game;
//        StageButton.LEVEL = StageButton.ARKADE_LEVEL;
//        TypeGameButton.TOUCHED_ARK = 2;
//        levelGen = new LevelGen(menuUI);
//        pauseUI = new GPauseUI(levelGen.getFieldBounds().getHeight());
    }

    public void setiServices(IServices iServices) {
        this.iServices = iServices;
    }

    public void draw(SpriteBatch batch){
        if (state == State.Game || state == State.Pause){
            levelGen.draw(batch);
//            gameUi.draw(batch);
            if (state == State.Pause)
               pauseUI.draw(batch);

        }else if (state == State.Menu){
            menuUI.draw(batch);
        }

        if (REFRESHLANG){
            REFRESHLANG = false;
            Localization.initEng();
            menuUI = new MenuUI();
        }

        if (ISRESTART) restart();


        if (MenuUI.MENUSTATE != MenuUI.SELECTSTAGE || state == State.Game)
            blockCounter.draw(batch, state == State.Game);


        if (isBackPressed || Gdx.input.isKeyJustPressed(Input.Keys.P)){
            isBackPressed = false;

            if (Handler.state == Handler.State.Pause)
                Handler.state = State.Game;
            else
                Handler.state = State.Pause;

            ISPAUSE = !ISPAUSE;
        }

    }

    public void restart(){
        levelGen = new LevelGen(menuUI);
        levelGen.setiServices(iServices);

        if (pauseUI == null)
            pauseUI = new GPauseUI(levelGen.getFieldBounds().getY());
        pauseUI.setRestartAction(DialogSystem.ISLEARNING ? ButtonActions.All.OPEN_LEARNING_INTERACTIVE : ButtonActions.All.RESTART_PAUSE_ACTION);

        blockCounter.setyPauseBtn(levelGen.getPauseBtnY());

        ISRESTART = false;
    }

    public void dispose() {
        menuUI.dispose();
//        gameUi.dispose();
        if (levelGen != null)
            levelGen.dispose();
        if (pauseUI != null)
            pauseUI.dispose();
    }
}
