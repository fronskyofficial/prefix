package com.fronsky.prefix.module.models;

import com.fronsky.prefix.logic.utils.Result;
import com.fronsky.prefix.module.data.Data;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;

public class PGroup {
    private final Data data;
    private String name;
    private String chatPrefix;
    private String tabPrefix;
    private ChatColor chatNameColor;
    private ChatColor tabNameColor;
    private ChatColor chatColor;
    private int tabWeight;

    public PGroup(final String name, final Data data) {
        this.name = name;
        this.data = data;
        final FileConfiguration groups = data.getGroups().get();
        if (!groups.contains(name)) {
            groups.set(name + ".chatPrefix", (Object)"");
            groups.set(name + ".tabPrefix", (Object)"");
            groups.set(name + ".chatNameColor", (Object)"&7");
            groups.set(name + ".tabNameColor", (Object)"&7");
            groups.set(name + ".chatColor", (Object)"&7");
            groups.set(name + ".tabWeight", (Object)0);
            data.getGroups().save();
        }
        this.chatPrefix = groups.getString(name + ".chatPrefix");
        this.tabPrefix = groups.getString(name + ".tabPrefix");
        final String chatNameColor = groups.getString(name + ".chatNameColor");
        if (chatNameColor != null) {
            final Result<ChatColor> result = data.getChatColor().getChatColor(chatNameColor.substring(1));
            if (!result.Success()) {
                data.getLogger().severe(result.Exception().getMessage());
                return;
            }
            this.chatNameColor = result.Value();
        }
        final String tabNameColor = groups.getString(name + ".tabNameColor");
        if (tabNameColor != null) {
            final Result<ChatColor> result2 = data.getChatColor().getChatColor(tabNameColor.substring(1));
            if (!result2.Success()) {
                data.getLogger().severe(result2.Exception().getMessage());
                return;
            }
            this.tabNameColor = result2.Value();
        }
        final String chatColor = groups.getString(name + ".chatColor");
        if (chatColor != null) {
            final Result<ChatColor> result3 = data.getChatColor().getChatColor(chatColor.substring(1));
            if (!result3.Success()) {
                data.getLogger().severe(result3.Exception().getMessage());
                return;
            }
            this.chatColor = result3.Value();
        }
        this.tabWeight = groups.getInt(name + ".tabWeight");
    }

    public PGroup(final String name, final Data data, final String chatPrefix, final String tabPrefix, final ChatColor chatNameColor, final ChatColor tabNameColor, final ChatColor chatColor,
                  final int tabWeight) {
        this.name = name;
        this.data = data;
        this.chatPrefix = chatPrefix;
        this.tabPrefix = tabPrefix;
        this.chatNameColor = chatNameColor;
        this.tabNameColor = tabNameColor;
        this.chatColor = chatColor;
        this.tabWeight = tabWeight;
        final FileConfiguration groups = data.getGroups().get();
        groups.set(name + ".chatPrefix", (Object)chatPrefix);
        groups.set(name + ".tabPrefix", (Object)tabPrefix);
        groups.set(name + ".chatNameColor", (Object)("&" + chatNameColor.getChar()));
        groups.set(name + ".tabNameColor", (Object)("&" + tabNameColor.getChar()));
        groups.set(name + ".chatColor", (Object)("&" + chatColor.getChar()));
        groups.set(name + ".tabWeight", (Object)tabWeight);
        data.getGroups().save();
    }

    public String getName() {
        return this.name;
    }

    public String getChatPrefix() {
        return this.chatPrefix;
    }

    public String getTabPrefix() {
        return this.tabPrefix;
    }

    public ChatColor getChatNameColor() {
        return this.chatNameColor;
    }

    public ChatColor getTabNameColor() {
        return this.tabNameColor;
    }

    public ChatColor getChatColor() {
        return this.chatColor;
    }

    public int getTabWeight() {
        return this.tabWeight;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setChatPrefix(final String chatPrefix) {
        this.chatPrefix = chatPrefix;
        this.data.getGroups().get().set(this.name + ".chatPrefix", (Object)chatPrefix);
        this.data.getGroups().save();
    }

    public void setTabPrefix(final String tabPrefix) {
        this.tabPrefix = tabPrefix;
        this.data.getGroups().get().set(this.name + ".tabPrefix", (Object)tabPrefix);
        this.data.getGroups().save();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }

    public void setChatNameColor(final ChatColor chatNameColor) {
        this.chatNameColor = chatNameColor;
        this.data.getGroups().get().set(this.name + ".chatNameColor", (Object)("&" + chatNameColor.getChar()));
        this.data.getGroups().save();
    }

    public void setTabNameColor(final ChatColor tabNameColor) {
        this.tabNameColor = tabNameColor;
        this.data.getGroups().get().set(this.name + ".tabNameColor", (Object)("&" + tabNameColor.getChar()));
        this.data.getGroups().save();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }

    public void setChatColor(final ChatColor chatColor) {
        this.chatColor = chatColor;
        this.data.getGroups().get().set(this.name + ".chatColor", (Object)("&" + chatColor.getChar()));
        this.data.getGroups().save();
    }

    public void setTabWeight(final int tabWeight) {
        this.tabWeight = tabWeight;
        this.data.getGroups().get().set(this.name + ".tabWeight", (Object)tabWeight);
        this.data.getGroups().save();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }
}
