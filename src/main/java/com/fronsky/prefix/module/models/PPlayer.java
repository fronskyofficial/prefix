package com.fronsky.prefix.module.models;

import com.fronsky.prefix.logic.utils.Result;
import com.fronsky.prefix.module.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PPlayer {
    private final Data data;
    private Player player;
    private String groupName;

    public PPlayer(final Player player, final Data data) {
        this.player = player;
        this.data = data;
        final FileConfiguration players = data.getPlayers().get();
        final String uuid = player.getUniqueId().toString();
        if (!players.contains(uuid)) {
            players.set(uuid + ".group", (Object)"");
            data.getPlayers().save();
            this.groupName = "";
        }
        else {
            this.groupName = players.getString(uuid + ".group");
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public Result<PGroup> getGroup() {
        if (!this.data.getGroups().get().contains(this.groupName)) {
            return Result.Fail(new Exception("The group does not exist."));
        }
        return Result.Ok(new PGroup(this.groupName, this.data));
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public void setGroup(final String groupName) {
        this.groupName = groupName;
        this.data.getPlayers().get().set(this.player.getUniqueId() + ".group", groupName);
        this.data.getPlayers().save();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }
}
