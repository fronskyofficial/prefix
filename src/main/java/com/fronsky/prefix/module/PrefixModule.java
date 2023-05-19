package com.fronsky.prefix.module;

import com.fronsky.prefix.Main;
import com.fronsky.prefix.formats.file.YmlFile;
import com.fronsky.prefix.logic.file.IFile;
import com.fronsky.prefix.logic.module.Module;
import com.fronsky.prefix.module.commands.Prefix;
import com.fronsky.prefix.module.data.Data;
import com.fronsky.prefix.module.events.Chat;
import com.fronsky.prefix.module.events.Join;
import com.fronsky.prefix.module.events.Quit;
import com.fronsky.prefix.module.models.PPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PrefixModule extends Module<Main> {
    private static Data data;

    public PrefixModule(Main mainClass) {
        super(mainClass);
        final IFile<FileConfiguration> groups = new YmlFile("groups", mainClass);
        final IFile<FileConfiguration> players = new YmlFile("players", mainClass);
        setData(new Data(mainClass, groups, players));
    }

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        PrefixModule.data = data;
    }

    @Override
    public void onLoad() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            data.getTablist().update(new PPlayer(player, data));
        }
    }

    @Override
    public void onEnable() {
        command(Prefix::new);
        event(Chat::new);
        event(Join::new);
        event(Quit::new);
    }

    @Override
    public void onDisable() {}
}
