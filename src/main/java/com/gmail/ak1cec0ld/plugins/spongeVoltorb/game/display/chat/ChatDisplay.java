package com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.chat;

import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.Game;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.GameDisplay;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;


public class ChatDisplay implements GameDisplay {

    private Game game;
    private Player player;

    private static final Text WHITESPACE = Text.of("                                ");

    public ChatDisplay(Game g, Player p){
        game = g;
        player = p;
    }

    @Override
    public void display() {
        Text.Builder textBuilder = Text.builder()
                .append(Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE,Text.NEW_LINE)
                .append(Text.of("----------------------" +
                "Level:   " + game.getLevel() + "/" + game.getLevelMax() +
                "---------------------"));
        Text.Builder number;
        for(int i = 0; i < game.getGridSize(); i++){
            textBuilder.append(Text.NEW_LINE).append(WHITESPACE);
            for(int j = 0; j < game.getGridSize(); j++) {
                int finalI = i;
                int finalJ = j;
                Game.Slot slot = game.get(finalJ,finalI);
                Text.Builder numberBuilder = Text.builder();
                numberBuilder.append(Text.of(makeChar(slot)))
                        .onHover(TextActions.showText(Text.of("Click!")))
                        .onClick(TextActions.executeCallback(commandSource -> clickAction(finalJ,finalI)));
                textBuilder.append(numberBuilder.build()).append(Text.of(" "));
            }
            number = Text.builder(game.getHorizontalBonus()[i]+"").onHover(TextActions.showText(Text.of("Sum of Bonus Points this row")));
            textBuilder.append(Text.of("  ")).append(number.build());
            number = Text.builder(game.getHorizontalVoltorbs()[i]+"").onHover(TextActions.showText(Text.of("How many Voltorbs this row")));
            textBuilder.append(Text.of(" ")).append(number.build());
        }
        Text.Builder bSuffixBuilder = Text.builder();
        Text.Builder vSuffixBuilder = Text.builder();
        for(int j = 0; j < game.getGridSize(); j++){
            number = Text.builder().append(Text.of(game.getVerticalBonus()[j]+" ")).onHover(TextActions.showText(Text.of("Sum of Bonus Points this column")));
            bSuffixBuilder.append(number.build());
            number = Text.builder().append(Text.of(game.getVerticalVoltorbs()[j]+" ")).onHover(TextActions.showText(Text.of("How many Voltorbs this column")));
            vSuffixBuilder.append(number.build());
        }
        textBuilder
                .append(Text.NEW_LINE).append(WHITESPACE).append(bSuffixBuilder.build())
                .append(Text.NEW_LINE).append(WHITESPACE).append(vSuffixBuilder.build())
                .append(Text.NEW_LINE).append(Text.of("----------------------------------------------------"));
        player.sendMessage(textBuilder.build());
    }

    private String makeChar(Game.Slot slot) {
        if(!slot.used) return "?";
        if(slot.value > 0) return String.valueOf(slot.value);
        return "*";
    }

    private void clickAction(int col, int row){
        if(game==null || game.finished())return; //do nothing if this game is over
        Game.Slot slot = game.get(col, row);
        if(slot.used)return;
        game.markUsed(col,row);
        display();
        if(slot.value == 0) {
            player.sendMessage(Text.of("Boom! Game over!"));
            return;
        }
        if(game.finished()) {
            player.sendMessage(Text.of("Game over! Next!"));
            if(game.getLevel() < game.getLevelMax()){
                int level = game.getLevel()+1;
                game = new Game(game.getGridSize());
                game.initialize(level);
                display();
            }
        }
    }
}
