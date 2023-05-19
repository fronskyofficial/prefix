package com.fronsky.prefix.module.commands.help;

import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpMessage {
    public static void prefixCommand(Player player) {
        String command = "/prefix";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Displays your prefix information.\n\n")
                .append(ChatColor.RED + "prefix.prefix")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixPlayerCommand(Player player) {
        String command = "/prefix <player>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Displays prefix information for the specified player.\n\n")
                .append(ChatColor.RED + "prefix.prefix.others")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixHelpCommand(Player player) {
        String command = "/prefix help";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Displays this help message.\n\n")
                .append(ChatColor.RED + "prefix.prefix.help")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixChatCommand(Player player) {
        String command = "/prefix chat <group> <prefix>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Sets the chat prefix for the specified group.\n\n")
                .append(ChatColor.RED + "prefix.prefix.chat")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixTabCommand(Player player) {
        String command = "/prefix tab <group> <prefix>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Sets the tab prefix for the specified group.\n\n")
                .append(ChatColor.RED + "prefix.prefix.tab")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixChatNameColorCommand(Player player) {
        String command = "/prefix chatnamecolor <group> <color>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Sets the chat name color for the specified group.\n\n")
                .append(ChatColor.RED + "prefix.prefix.chatnamecolor")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixTabNameColorCommand(Player player) {
        String command = "/prefix tabnamecolor <group> <color>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Sets the tab name color for the specified group.\n\n")
                .append(ChatColor.RED + "prefix.prefix.tabnamecolor")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixChatColorCommand(Player player) {
        String command = "/prefix chatcolor <group> <color>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Sets the chat color for the specified group.\n\n")
                .append(ChatColor.RED + "prefix.prefix.chatcolor")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixWeightCommand(Player player) {
        String command = "/prefix weight <group> <weight>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Sets the tab weight for the specified group.\n\n")
                .append(ChatColor.RED + "prefix.prefix.weight")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixGroupCommand(Player player) {
        String command = "/prefix group <player or group> <group or empty>";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Sets the group for the specified player or creates a new group if the group does not already exist.\n\n")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        player.spigot().sendMessage(message);
    }

    public static void prefixReloadCommand(Player player) {
        String command = "/prefix reload";
        BaseComponent[] hoverText = new ComponentBuilder(ChatColor.GRAY + command + " - Reloads the configuration files.\n\n")
                .append(ChatColor.RED + "prefix.prefix.reload")
                .create();
        TextComponent message = new TextComponent(ChatColor.GRAY + "- " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        player.spigot().sendMessage(message);
    }

}
