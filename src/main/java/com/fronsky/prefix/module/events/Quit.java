package com.fronsky.prefix.module.events;

import com.fronsky.prefix.module.PrefixModule;
import com.fronsky.prefix.module.data.Data;
import com.fronsky.prefix.module.models.PPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {
    private final Data data;

    public Quit() {
        this.data = PrefixModule.getData();
    }

    @org.bukkit.event.EventHandler
    public void playerQuitEvent(final PlayerQuitEvent event) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }
}
