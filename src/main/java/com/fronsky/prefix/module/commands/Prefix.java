package com.fronsky.prefix.module.commands;

import com.fronsky.prefix.logic.command.CommandHandler;
import com.fronsky.prefix.logic.utils.Result;
import com.fronsky.prefix.module.PrefixModule;
import com.fronsky.prefix.module.commands.help.HelpMessage;
import com.fronsky.prefix.module.data.Data;
import com.fronsky.prefix.module.models.PGroup;
import com.fronsky.prefix.module.models.PPlayer;
import lombok.NonNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.Arrays;

public class Prefix extends CommandHandler {
    private final Data data;

    public Prefix() {
        super("prefix", "prefix.cmd.prefix");
        setSubcommands(Arrays.asList("help", "chat", "tab", "chatnamecolor", "tabnamecolor", "chatcolor", "weight", "group", "reload", "info"));
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
        player.sendMessage(ChatColor.WHITE + "<---------------" + ChatColor.DARK_RED + "Prefix Player Info" + ChatColor.WHITE + "--------------->");
        player.sendMessage(ChatColor.WHITE + "Player: " + ChatColor.GRAY + pplayer.getPlayer().getDisplayName());
        player.sendMessage(ChatColor.WHITE + "Chatprefix: " + pgroup.getChatPrefix());
        player.sendMessage(ChatColor.WHITE + "Tabprefix: " + pgroup.getTabPrefix());
        player.sendMessage(ChatColor.WHITE + "Chatnamecolor: " + pgroup.getChatNameColor() + pgroup.getChatNameColor().name());
        player.sendMessage(ChatColor.WHITE + "Tabnamecolor: " + pgroup.getTabNameColor() + pgroup.getTabNameColor().name());
        player.sendMessage(ChatColor.WHITE + "Chatcolor: " + pgroup.getChatColor() + pgroup.getChatColor().name());
        player.sendMessage(ChatColor.WHITE + "Group: " + ChatColor.GRAY + pgroup.getName());
        player.sendMessage(ChatColor.WHITE + "<---------------------------------------->");
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
        sender.sendMessage(ChatColor.WHITE + "<---------------" + ChatColor.DARK_RED + "Prefix Player Info" + ChatColor.WHITE + "--------------->");
        sender.sendMessage(ChatColor.WHITE + "Player: " + ChatColor.GRAY + pplayer.getPlayer().getDisplayName());
        sender.sendMessage(ChatColor.WHITE + "Chatprefix: " + pgroup.getChatPrefix());
        sender.sendMessage(ChatColor.WHITE + "Tabprefix: " + pgroup.getTabPrefix());
        sender.sendMessage(ChatColor.WHITE + "Chatnamecolor: " + pgroup.getChatNameColor() + pgroup.getChatNameColor().name());
        sender.sendMessage(ChatColor.WHITE + "Tabnamecolor: " + pgroup.getTabNameColor() + pgroup.getTabNameColor().name());
        sender.sendMessage(ChatColor.WHITE + "Chatcolor: " + pgroup.getChatColor() + pgroup.getChatColor().name());
        sender.sendMessage(ChatColor.WHITE + "Group: " + ChatColor.GRAY + pgroup.getName());
        sender.sendMessage(ChatColor.WHITE + "<---------------------------------------->");
    }

    public void help(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        sender.sendMessage(ChatColor.WHITE + "<---------------" + ChatColor.DARK_RED + "Prefix Help" + ChatColor.WHITE + "--------------->");
        sender.sendMessage(ChatColor.YELLOW + "Aliases:" + ChatColor.GRAY + ChatColor.ITALIC + "None");
        sender.sendMessage(ChatColor.YELLOW + "Commands: ");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HelpMessage.prefixCommand(player);
            HelpMessage.prefixPlayerCommand(player);
            HelpMessage.prefixHelpCommand(player);
            HelpMessage.prefixChatCommand(player);
            HelpMessage.prefixTabCommand(player);
            HelpMessage.prefixChatNameColorCommand(player);
            HelpMessage.prefixTabNameColorCommand(player);
            HelpMessage.prefixChatColorCommand(player);
            HelpMessage.prefixWeightCommand(player);
            HelpMessage.prefixGroupCommand(player);
            HelpMessage.prefixReloadCommand(player);
        } else {
            sender.sendMessage(ChatColor.GRAY + "- /prefix");
            sender.sendMessage(ChatColor.GRAY + "- /prefix <player>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix help");
            sender.sendMessage(ChatColor.GRAY + "- /prefix chat <group> <prefix>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix tab <group> <prefix>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix chatnamecolor <group> <color>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix tabnamecolor <group> <color>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix chatcolor <group> <color>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix weight <group> <weight>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix group <player or group> <group or empty>");
            sender.sendMessage(ChatColor.GRAY + "- /prefix reload");
        }
        sender.sendMessage(ChatColor.WHITE + "<---------------------------------------->");
    }

    public void chat(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /prefix chat <group> <prefix>");
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
        sender.sendMessage(ChatColor.GREEN + "Successfully changed chat prefix for group '" + pgroup.getName() + "'.");
    }

    public void tab(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /prefix tab <group> <prefix>");
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
        sender.sendMessage(ChatColor.GREEN + "Successfully changed tab prefix for group '" + pgroup.getName() + "'.");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }

    public void chatnamecolor(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /prefix chatnamecolor <group> <color>");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        final Result<ChatColor> result = this.data.getChatColor().getChatColor(args[1].substring(1, 2));
        if (!result.Success()) {
            sender.sendMessage(ChatColor.RED + result.Exception().getMessage());
            return;
        }
        pgroup.setChatNameColor(result.Value());
        sender.sendMessage(ChatColor.GREEN + "Successfully changed chat name color for group '" + pgroup.getName() + "'.");
    }

    public void tabnamecolor(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /prefix tabnamecolor <group> <color>");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        final Result<ChatColor> result = this.data.getChatColor().getChatColor(args[1].substring(1, 2));
        if (!result.Success()) {
            sender.sendMessage(ChatColor.RED + result.Exception().getMessage());
            return;
        }
        pgroup.setTabNameColor(result.Value());
        sender.sendMessage(ChatColor.GREEN + "Successfully changed tab name color for group '" + pgroup.getName() + "'.");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
    }

    public void chatcolor(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /prefix chatcolor <group> <color>");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        final Result<ChatColor> result = this.data.getChatColor().getChatColor(args[1].substring(1, 2));
        if (!result.Success()) {
            sender.sendMessage(ChatColor.RED + result.Exception().getMessage());
            return;
        }
        pgroup.setChatColor(result.Value());
        sender.sendMessage(ChatColor.GREEN + "Successfully changed chat color for group '" + pgroup.getName() + "'.");
    }

    public void weight(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /prefix weight <group> <weight>");
            return;
        }
        int weight = 0;
        try {
            weight = Integer.parseInt(args[1]);
        } catch (Exception exception) {
            sender.sendMessage(ChatColor.RED + "Invalid number format. Please enter a valid number from 0-9.");
            return;
        }
        final PGroup pgroup = new PGroup(args[0], this.data);
        pgroup.setTabWeight(weight);
        sender.sendMessage(ChatColor.GREEN + "Successfully changed tab weight for group '" + pgroup.getName() + "'.");
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
                sender.sendMessage(ChatColor.GREEN + "Successfully changed group for '" + pplayer.getPlayer().getDisplayName() + "'.");
                return;
            }
            sender.sendMessage(ChatColor.RED + "Incorrect format. Usage: /prefix group <player or group> <group or empty>");
        } else {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player not found. Please ensure that the player is online.");
                return;
            }
            final PPlayer pplayer = new PPlayer(player, this.data);
            pplayer.setGroup(args[1]);
            sender.sendMessage(ChatColor.GREEN + "Successfully changed group for '" + pplayer.getPlayer().getDisplayName() + "'.");
        }
    }

    public void reload(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        this.data.getGroups().reload();
        this.data.getPlayers().reload();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.data.getTablist().update(new PPlayer(player, this.data));
        }
        sender.sendMessage(ChatColor.GREEN + "Prefix has been reloaded successfully.");
    }

    public void info(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        sender.sendMessage(ChatColor.WHITE + "<---------------" + ChatColor.DARK_RED + "Prefix Info" + ChatColor.WHITE + "--------------->");
        sender.sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.WHITE + "Prefix");
        sender.sendMessage(ChatColor.YELLOW + "Author: " + ChatColor.GRAY + "Fronsky Inc");

        if (sender instanceof Player) {
            Player player = (Player) sender;

            TextComponent plugin = new TextComponent(ChatColor.YELLOW + "Plugin: " + ChatColor.GRAY + "www.fronsky.com/resources/prefix");
            plugin.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.fronsky.com/resources/prefix"));

            TextComponent website = new TextComponent(ChatColor.YELLOW + "Website: " + ChatColor.GRAY + "www.fronsky.com");
            website.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.fronsky.com"));

            player.spigot().sendMessage(plugin);
            player.spigot().sendMessage(website);
        } else {
            sender.sendMessage(ChatColor.YELLOW + "Plugin: " + ChatColor.GRAY + "www.fronsky.com/resources/prefix");
            sender.sendMessage(ChatColor.YELLOW + "Website: " + ChatColor.GRAY + "www.fronsky.com");
        }
        sender.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.RED + data.getPlugin().getDescription().getVersion());
        sender.sendMessage(ChatColor.WHITE + "<---------------------------------------->");
    }
}
