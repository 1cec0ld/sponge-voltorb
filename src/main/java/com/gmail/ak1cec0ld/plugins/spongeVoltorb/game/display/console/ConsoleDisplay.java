package com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.console;

import com.gmail.ak1cec0ld.plugins.spongeVoltorb.SpongeVoltorb;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.Game;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.GameDisplay;

public class ConsoleDisplay implements GameDisplay {
    private Game game;

    public ConsoleDisplay(Game g){
        game = g;
    }


    @Override
    public void display() {

        String out = " \n";
        for(int x = 0; x < game.getGridSize(); x++){
            for(int y = 0; y < game.getGridSize(); y++){
                out = out + game.get(x,y).value + " ";
            }
            out = out + "| " + game.getVerticalBonus()[x] + " " + game.getVerticalVoltorbs()[x] + "\n";
        }
        for(int y = 0; y < game.getGridSize(); y++){
            out = out + game.getHorizontalBonus()[y] + " ";
        }
        out = out + "\n";
        for(int y = 0; y < game.getGridSize(); y++){
            out = out + game.getHorizontalVoltorbs()[y] + " ";
        }
        SpongeVoltorb.instance().getLogger().info(out);
    }
}
