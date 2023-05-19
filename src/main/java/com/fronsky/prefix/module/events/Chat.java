package com.fronsky.prefix.module.events;

import com.fronsky.prefix.logic.utils.ColorUtils;
import com.fronsky.prefix.logic.utils.Result;
import com.fronsky.prefix.module.PrefixModule;
import com.fronsky.prefix.module.data.Data;
import com.fronsky.prefix.module.models.PGroup;
import com.fronsky.prefix.module.models.PPlayer;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    private final Data data;

    public Chat() {
        this.data = PrefixModule.getData();
    }

    @org.bukkit.event.EventHandler
    public void asyncPlayerChatEvent(final AsyncPlayerChatEvent event) {
        final PPlayer pplayer = new PPlayer(event.getPlayer(), this.data);
        final String message = event.getMessage();
        final String[] messageParts = message.split(" ");
        final Result<PGroup> result = pplayer.getGroup();
        if (!result.Success()) {
            data.getLogger().severe(result.Exception().getMessage());
            event.getPlayer().sendMessage(ChatColor.RED + "An error occurred while retrieving your group information. Please contact an administrator.");
            return;
        }
        final PGroup pgroup = result.Value();
        final ChatColor chatColor = pgroup.getChatColor();
        String format;
        if (pgroup.getChatPrefix() == null) {
            format = ChatColor.GRAY + pplayer.getPlayer().getDisplayName() + ": ";
        }
        else if (pgroup.getChatPrefix().isEmpty()) {
            format = pgroup.getChatNameColor() + pplayer.getPlayer().getDisplayName() + ": " + chatColor;
        }
        else {
            format = ColorUtils.colorize(pgroup.getChatPrefix()) + " " + pgroup.getChatNameColor() + pplayer.getPlayer().getDisplayName() + ": " + chatColor;
        }
        for (final Player recipient : event.getRecipients()) {
            final StringBuilder messageFormat = new StringBuilder(format);
            boolean foundName = false;
            for (final String part : messageParts) {
                boolean found = false;
                if (part.equalsIgnoreCase(recipient.getName())) {
                    foundName = true;
                    found = true;
                }
                if (found) {
                    messageFormat.append(ChatColor.BOLD).append(part).append(ChatColor.RESET).append(chatColor).append(" ");
                }
                else {
                    messageFormat.append(part).append(" ");
                }
            }
            if (foundName) {
                event.getRecipients().remove(recipient);
                recipient.sendMessage(String.valueOf(messageFormat));
            }
        }
        event.setFormat(format + message);
    }
}
