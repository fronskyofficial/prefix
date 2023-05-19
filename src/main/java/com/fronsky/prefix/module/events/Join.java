package com.fronsky.prefix.module.events;

import com.fronsky.prefix.module.PrefixModule;
import com.fronsky.prefix.module.data.Data;
import com.fronsky.prefix.module.models.PPlayer;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    private final Data data;

    public Join() {
        this.data = PrefixModule.getData();
    }

    @org.bukkit.event.EventHandler
    public void playerJoinEvent(final PlayerJoinEvent event) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }
}
