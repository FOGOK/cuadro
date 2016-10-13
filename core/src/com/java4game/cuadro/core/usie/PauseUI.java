package com.java4game.cuadro.core.usie;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.java4game.cuadro.Gm;
import com.java4game.cuadro.core.TextureGen;
import com.java4game.cuadro.core.uiwidgets.Button;
import com.java4game.cuadro.core.uiwidgets.ButtonActions;
import com.java4game.cuadro.utils.Atalas;
import com.java4game.cuadro.utils.GameUtils;
import com.java4game.cuadro.utils.Localization;

/**
 * Created by FOGOK on 12.10.2016 16:24.
 * Если ты это читаешь, то знай, что этот код хуже
 * кожи разлагающегося бомжа лежащего на гнилой
 * лавочке возле остановки автобуса номер 985
 */
public class PauseUI {

    Sprite blackScreen;

    Button continueB, restartB, settB, inMainMenu;

    public PauseUI(TextureGen textureGen) {
        blackScreen = new Sprite(GameUtils.generateBlackPic());
        blackScreen.setSize(Gm.WIDTH, Gm.HEIGHT);
        blackScreen.setAlpha(0.6f);

        addButtons(textureGen);
    }

    private void addButtons(TextureGen textureGen){
        continueB = new Button(textureGen, Atalas.startB, Atalas.startBAct, Gm.WIDTH / 2f, 13f, ButtonActions.CONTINUE_PAUSE_ACTION);
        continueB.setText(Localization.ENG.CONTINUEPAUSETEXT);

        restartB = new Button(textureGen, Atalas.startB, Atalas.startBAct, Gm.WIDTH / 2f, 11f, ButtonActions.RESTART_PAUSE_ACTION);
        restartB.setText(Localization.ENG.RESTARTPAUSETEXT);

        settB = new Button(textureGen, Atalas.startB, Atalas.startBAct, Gm.WIDTH / 2f, 9f, ButtonActions.OPTION_PAUSE_ACTION);
        settB.setText(Localization.ENG.OPTIONPAUSETEXT);

        inMainMenu = new Button(textureGen, Atalas.startB, Atalas.startBAct, Gm.WIDTH / 2f, 7f, ButtonActions.TOMAINMENU_PAUSE_ACTION);
        inMainMenu.setText(Localization.ENG.EXITMENUPAUSETEXT);
    }

    private void drawButtons(SpriteBatch batch){
        continueB.draw(batch);
        restartB.draw(batch);
        settB.draw(batch);
        inMainMenu.draw(batch);
    }

    public void draw(SpriteBatch batch){
        blackScreen.draw(batch);
        drawButtons(batch);
    }

    public void dispose() {
        blackScreen.getTexture().dispose();
    }
}
