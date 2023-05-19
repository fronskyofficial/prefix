package com.fronsky.prefix.module.data;

import com.fronsky.prefix.formats.logging.BasicLogger;
import com.fronsky.prefix.logic.file.IFile;
import com.fronsky.prefix.logic.logging.ILogger;
import com.fronsky.prefix.module.chatcolor.PChatColor;
import com.fronsky.prefix.module.objects.Tablist;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Data {
    private final Plugin plugin;
    private final IFile<FileConfiguration> groups;
    private final IFile<FileConfiguration> players;
    private final Tablist tablist;
    private final PChatColor chatColor;
    private final ILogger logger;

    public Data(Plugin plugin, IFile<FileConfiguration> groups, IFile<FileConfiguration> players) {
        this.plugin = plugin;
        this.groups = groups;
        this.players = players;
        this.tablist = new Tablist();
        this.chatColor = new PChatColor();
        this.logger = new BasicLogger();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public IFile<FileConfiguration> getGroups() {
        return groups;
    }

    public IFile<FileConfiguration> getPlayers() {
        return players;
    }

    public Tablist getTablist() {
        return tablist;
    }

    public PChatColor getChatColor() {
        return chatColor;
    }

    public ILogger getLogger() {
        return logger;
    }
}
