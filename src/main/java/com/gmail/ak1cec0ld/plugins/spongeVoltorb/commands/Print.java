package com.gmail.ak1cec0ld.plugins.spongeVoltorb.commands;

import com.gmail.ak1cec0ld.plugins.spongeVoltorb.SpongeVoltorb;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.Game;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.GameDisplay;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.chat.ChatDisplay;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.console.ConsoleDisplay;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

public class Print implements CommandExecutor {


    Print(){
        CommandSpec myCommandSpec = CommandSpec.builder()
                .description(Text.of("starts a game"))
                .permission("voltorb.commands.vconsole")
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("type"))), GenericArguments.onlyOne(GenericArguments.integer(Text.of("level"))))
                .executor(this)
                .build();
        Sponge.getCommandManager().register(SpongeVoltorb.instance(), myCommandSpec, "vprint");
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        GameDisplay displayMethod;
        try {
            int level = args.<Integer>getOne("level").isPresent() ? args.<Integer>getOne("level").get() : 1;
            String type = args.<String>getOne("type").isPresent() ? args.<String>getOne("type").get() : "console";
            Game g = new Game(!type.equals("inventory") ? 5 : 4);
            g.initialize(level);
            displayMethod = makeDisplay(type, g, src);
            displayMethod.display();
            return CommandResult.success();
        } catch (Exception e){
            throw new CommandException(Text.of("Error in Voltorb-console command"));
        }
    }
    private GameDisplay makeDisplay(String type, Game game, MessageReceiver target){
        switch(type){
            case "inventory":
                //todo: return new InventoryDisplay();
                break;
            case "chat":
                if(target instanceof Player) {
                    return new ChatDisplay(game, (Player)target);
                }
        }
        return new ConsoleDisplay(game);
    }
}
