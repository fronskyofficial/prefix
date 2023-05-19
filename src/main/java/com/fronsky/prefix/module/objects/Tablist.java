package com.fronsky.prefix.module.objects;

import com.fronsky.prefix.logic.utils.Result;
import com.fronsky.prefix.module.PrefixModule;
import com.fronsky.prefix.module.data.Data;
import com.fronsky.prefix.module.models.PGroup;
import com.fronsky.prefix.module.models.PPlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.bukkit.ChatColor;

public class Tablist {
    private final Data data;

    public Tablist() {
        this.data = PrefixModule.getData();
    }

    public void update(final PPlayer pplayer) {
        final Player player = pplayer.getPlayer();
        Result<Team> result;
        if (pplayer.getGroupName().equals("") || pplayer.getGroup() == null) {
            result = this.getTeam(player.getScoreboard(), 0, "", ChatColor.GRAY, player);
        }
        else {
            final Result<PGroup> result2 = pplayer.getGroup();
            if (!result2.Success()) {
                data.getLogger().severe(result2.Exception().getMessage());
                return;
            }
            final PGroup pgroup = result2.Value();
            ChatColor tabNameColor = pgroup.getTabNameColor();
            if (tabNameColor == null) {
                tabNameColor = ChatColor.GRAY;
            }
            result = this.getTeam(player.getScoreboard(), pgroup.getTabWeight(), pgroup.getTabPrefix(), tabNameColor, player);
        }
        if (!result.Success()) {
            data.getLogger().severe(result.Exception().getMessage());
            return;
        }
        final Team team = result.Value();
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        if (!team.hasEntry(player.getDisplayName())) {
            team.addEntry(player.getDisplayName());
        }
    }

    private String getWeight(final int weight) {
        String string = String.valueOf(weight);
        final char[] digits = { '9', '8', '7', '6', '5', '4', '3', '2', '1', '0' };
        final char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
        for (int i = 0; i < digits.length; ++i) {
            string = string.replace(digits[i], letters[i]);
        }
        return "j" + string;
    }

    private Result<Team> getTeam(final Scoreboard scoreboard, final int weight, String prefix, final ChatColor nameColor, final Player player) {
        if (scoreboard == null) {
            return Result.Fail(new Exception("Scoreboard is null!"));
        }
        String teamName = this.getWeight(weight) + player.getDisplayName();
        teamName = teamName.substring(0, Math.min(teamName.length(), 15));
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        if (!prefix.endsWith(" ") && !prefix.equals("")) {
            prefix += " ";
        }
        prefix = prefix.substring(0, Math.min(prefix.length(), 15));
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        team.setPrefix(prefix);
        team.setDisplayName(teamName);
        team.setColor(nameColor);
        return Result.Ok(team);
    }
}
