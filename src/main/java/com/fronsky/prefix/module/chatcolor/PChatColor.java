package com.fronsky.prefix.module.chatcolor;

import com.fronsky.prefix.logic.utils.Result;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class PChatColor {
    private final Map<Character, ChatColor> chatColorMap;

    public PChatColor() {
        this.chatColorMap = new HashMap<Character, ChatColor>();
        for (final ChatColor color : ChatColor.values()) {
            this.chatColorMap.put(color.getChar(), color);
        }
    }

    public Result<ChatColor> getChatColor(final String color) {
        if (!this.chatColorMap.containsKey(color.charAt(0))) {
            return Result.Fail(new Exception("This color doesn't exist."));
        }
        return Result.Ok(this.chatColorMap.get(color.charAt(0)));
    }

    public boolean colorExists(final String color) {
        return this.chatColorMap.containsKey(color.charAt(0));
    }
}
