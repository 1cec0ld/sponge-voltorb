package com.gmail.ak1cec0ld.plugins.spongeVoltorb;


import com.gmail.ak1cec0ld.plugins.spongeVoltorb.commands.VoltorbCommand;
import com.gmail.ak1cec0ld.plugins.spongeVoltorb.listeners.Interact;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id="spongevoltorb", name = "Sponge Voltorb", version = "1.0", description = "Example Sponge Voltorb")
public class SpongeVoltorb{

    private static SpongeVoltorb instance;


    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event){
        instance = this;
        new VoltorbCommand();
        new Interact();
    }

    public static SpongeVoltorb instance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}
