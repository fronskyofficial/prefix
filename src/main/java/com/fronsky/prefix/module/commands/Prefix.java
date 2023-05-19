package com.fronsky.prefix.module.commands;

import com.fronsky.prefix.logic.command.CommandHandler;
import com.fronsky.prefix.logic.utils.Result;
import com.fronsky.prefix.module.PrefixModule;
import com.fronsky.prefix.module.data.Data;
import com.fronsky.prefix.module.models.PGroup;
import com.fronsky.prefix.module.models.PPlayer;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.Arrays;

public class Prefix extends CommandHandler {
    private final Data data;

    public Prefix() {
        super("prefix", "prefix.cmd.prefix");
        setSubcommands(Arrays.asList("help", "chat", "tab", "chatnamecolor", "tabnamecolor", "chatcolor", "weight", "group", "reload"));
        this.data = PrefixModule.getData();
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length > 0) {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player != null) {
                if (sender instanceof Player && !this.hasPermission((Player)sender, "prefix.cmd.prefix.others")) {
                    return true;
                }
                this.others(sender, player, label, args);
                return true;
            }
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by players, please ensure you are using it correctly.");
            return true;
        }
        final Player player = (Player)sender;
        final PPlayer pplayer = new PPlayer(player, this.data);
        if (pplayer.getGroupName().equals("") || pplayer.getGroup() == null) {
            player.sendMessage(ChatColor.RED + "It seems you are not part of a recognized group, please contact an administrator for assistance.");
            return true;
        }
        final Result<PGroup> result = pplayer.getGroup();
        if (!result.Success()) {
            data.getLogger().severe(result.Exception().getMessage());
            player.sendMessage(ChatColor.RED + "An error occurred in the command. Please contact an administrator.");
            return true;
        }
        final PGroup pgroup = result.Value();
        sender.sendMessage(ChatColor.YELLOW + "Prefix for player " + pplayer.getPlayer().getDisplayName());
        player.sendMessage(ChatColor.WHITE + "Player: " + pplayer.getPlayer().getDisplayName());
        player.sendMessage(ChatColor.WHITE + "Chatprefix: " + pgroup.getChatPrefix());
        player.sendMessage(ChatColor.WHITE + "Tabprefix: " + pgroup.getTabPrefix());
        player.sendMessage(ChatColor.WHITE + "Chatnamecolor: " + pgroup.getChatNameColor() + pgroup.getChatNameColor().name());
        player.sendMessage(ChatColor.WHITE + "Tabnamecolor: " + pgroup.getTabNameColor() + pgroup.getTabNameColor().name());
        player.sendMessage(ChatColor.WHITE + "Chatcolor: " + pgroup.getChatColor() + pgroup.getChatColor().name());
        player.sendMessage(ChatColor.WHITE + "Group: " + pgroup.getName());
        return true;
    }

    public void others(@NonNull CommandSender sender, @NonNull Player target, @NonNull String label, @NonNull String[] args) {
        final PPlayer pplayer = new PPlayer(target, this.data);
        if (pplayer.getGroupName().equals("") || pplayer.getGroup() == null) {
            sender.sendMessage(ChatColor.RED + "It seems that this player is not part of a recognized group, please contact an administrator for assistance.");
            return;
        }
        final Result<PGroup> result = pplayer.getGroup();
        if (!result.Success()) {
            data.getLogger().severe(result.Exception().getMessage());
            sender.sendMessage(ChatColor.RED + "An error occurred in the command. Please contact an administrator.");
            return;
        }
        final PGroup pgroup = result.Value();
        sender.sendMessage(ChatColor.YELLOW + "Prefix for player " + pplayer.getPlayer().getDisplayName());
        sender.sendMessage(ChatColor.WHITE + "Player: " + pplayer.getPlayer().getDisplayName());
        sender.sendMessage(ChatColor.WHITE + "Chatprefix: " + pgroup.getChatPrefix());
        sender.sendMessage(ChatColor.WHITE + "Tabprefix: " + pgroup.getTabPrefix());
        sender.sendMessage(ChatColor.WHITE + "Chatnamecolor: " + pgroup.getChatNameColor() + pgroup.getChatNameColor().name());
        sender.sendMessage(ChatColor.WHITE + "Tabnamecolor: " + pgroup.getTabNameColor() + pgroup.getTabNameColor().name());
        sender.sendMessage(ChatColor.WHITE + "Chatcolor: " + pgroup.getChatColor() + pgroup.getChatColor().name());
        sender.sendMessage(ChatColor.WHITE + "Group: " + pgroup.getName());
    }

    public void help(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "Prefix Help:");
        sender.sendMessage(ChatColor.GRAY + "/prefix - Displays your prefix information (prefix.prefix)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix [player] - Displays prefix information for the specified player (prefix.prefix.others)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix help - Displays this help message (prefix.prefix.help)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix chat [group] [prefix] - Sets the chat prefix for the specified group (prefix.prefix.chat)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix tab [group] [prefix] - Sets the tab prefix for the specified group (prefix.prefix.tab)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix chatnamecolor [group] [color] - Sets the chat name color for the specified group (prefix.prefix.chatnamecolor)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix tabnamecolor [group] [color] - Sets the tab name color for the specified group (prefix.prefix.tabnamecolor)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix chatcolor [group] [color] - Sets the chat color for the specified group (prefix.prefix.chatcolor)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix weight [group] [weight] - Sets the tab weight for the specified group (prefix.prefix.weight)");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix group [player | group] [group | empty] - Sets the group for the specified player or group");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/prefix reload - Reloads the configuration files (prefix.prefix.reload)");
    }

    public void chat(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format, use /prefix chat [group] [prefix].");
            return;
        }
        final StringBuilder prefix = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            if (i + 1 != args.length) {
                prefix.append(args[i]).append(" ");
            }
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        pgroup.setChatPrefix(String.valueOf(prefix));
        sender.sendMessage(ChatColor.GREEN + "Chatprefix for group '" + pgroup.getName() + "' changed successfully.");
    }

    public void tab(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format, use /prefix tab [group] [prefix].");
            return;
        }
        final StringBuilder prefix = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            if (i + 1 != args.length) {
                prefix.append(args[i]).append(" ");
            }
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        pgroup.setTabPrefix(String.valueOf(prefix));
        sender.sendMessage(ChatColor.GREEN + "Tabprefix for group '" + pgroup.getName() + "' changed successfully.");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }

    public void chatnamecolor(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format, use /prefix chatnamecolor [group] [color].");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        final Result<ChatColor> result = this.data.getChatColor().getChatColor(args[1].substring(1, 2));
        if (!result.Success()) {
            sender.sendMessage(ChatColor.RED + result.Exception().getMessage());
            return;
        }
        pgroup.setChatNameColor(result.Value());
        sender.sendMessage(ChatColor.GREEN + "Chatnamecolor for group '" + pgroup.getName() + "' changed successfully.");
    }

    public void tabnamecolor(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format, use /prefix tabnamecolor [group] [color].");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        final Result<ChatColor> result = this.data.getChatColor().getChatColor(args[1].substring(1, 2));
        if (!result.Success()) {
            sender.sendMessage(ChatColor.RED + result.Exception().getMessage());
            return;
        }
        pgroup.setTabNameColor(result.Value());
        sender.sendMessage(ChatColor.GREEN + "Tabnamecolor for group '" + pgroup.getName() + "' changed successfully.");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }

    public void chatcolor(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format, use /prefix chatcolor [group] [color].");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        final Result<ChatColor> result = this.data.getChatColor().getChatColor(args[1].substring(1, 2));
        if (!result.Success()) {
            sender.sendMessage(ChatColor.RED + result.Exception().getMessage());
            return;
        }
        pgroup.setChatColor(result.Value());
        sender.sendMessage(ChatColor.GREEN + "Chatcolor for group '" + pgroup.getName() + "' changed successfully.");
    }

    public void weight(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format, use /prefix weight [group] [weight].");
            return;
        }
        int weight = 0;
        try {
            weight = Integer.parseInt(args[1]);
        }
        catch (Exception exception) {
            sender.sendMessage(ChatColor.RED + "Invalid number format, please enter a valid number from 0-9.");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        pgroup.setTabWeight(weight);
        sender.sendMessage(ChatColor.GREEN + "Tabweight for group '" + pgroup.getName() + "' changed successfully.");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }

    public void group(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            if (args.length == 1 && sender instanceof Player) {
                final Player player = (Player)sender;
                final PPlayer pplayer = new PPlayer(player, this.data);
                pplayer.setGroup(args[0]);
                if (!pplayer.getGroup().Success()) {
                    new PGroup(args[0], this.data);
                }
                sender.sendMessage(ChatColor.GREEN + "Group for '" + pplayer.getPlayer().getDisplayName() + "' changed successfully.");
                return;
            }
            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix group [player | group] [group | empty].");
        }
        else {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player was not found, please ensure that the player is online.");
                return;
            }
            final PPlayer pplayer = new PPlayer(player, this.data);
            pplayer.setGroup(args[1]);
            sender.sendMessage(ChatColor.GREEN + "Group for '" + pplayer.getPlayer().getDisplayName() + "' changed successfully.");
        }
    }

    public void reload(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        this.data.getGroups().reload();
        this.data.getPlayers().reload();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
        sender.sendMessage(ChatColor.GREEN + "Prefix has successfully been reloaded.");
    }
}