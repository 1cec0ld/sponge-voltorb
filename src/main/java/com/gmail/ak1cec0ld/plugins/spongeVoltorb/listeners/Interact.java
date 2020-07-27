package com.gmail.ak1cec0ld.plugins.spongeVoltorb.listeners;

import com.gmail.ak1cec0ld.plugins.spongeVoltorb.SpongeVoltorb;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.Game;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.GameDisplay;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.game.display.chat.ChatDisplay;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class Interact {

    private static final Location<World> trigger = new Location<>(Sponge.getServer().getWorld("Japan").get(),-216,59,250);

    public Interact(){
        Sponge.getEventManager().registerListeners(SpongeVoltorb.instance(), this);
    }

    @Listener
    public void onInteract(InteractBlockEvent event) {
        BlockSnapshot blockSnapshot = event.getTargetBlock();
        if(!blockSnapshot.getLocation().isPresent())return;
        if(!blockSnapshot.getLocation().get().equals(trigger))return;
        Optional<Player> playerCause = event.getCause().first(Player.class);
        if (!playerCause.isPresent()) return;
        Player player = playerCause.get();



        Game g = new Game(5);
        g.initialize(1);
        GameDisplay displayMethod = new ChatDisplay(g, player);
        displayMethod.display();

    }


}
