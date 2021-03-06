package com.youtube.hempfest.clans.util.events;

import com.youtube.hempfest.clans.util.StringLibrary;
import com.youtube.hempfest.clans.util.construct.Clan;
import com.youtube.hempfest.clans.util.construct.ClanUtil;
import com.youtube.hempfest.clans.util.listener.ClanEventBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerShootPlayerEvent extends ClanEventBuilder {

    private static final HandlerList handlers = new HandlerList();

    private final Player shooter;

    private final Player shot;

    private boolean cancelled;

    public PlayerShootPlayerEvent(Player p, Player target) {
        this.shooter = p;
        this.shot = target;
    }

    public Player getShooter() {
        return shooter;
    }

    public Player getShot() {
        return shot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public ClanUtil getUtil() {
        return Clan.clanUtil;
    }

    @Override
    public StringLibrary stringLibrary() {
        return new StringLibrary();
    }

    public void perform() {
        ClanUtil clanUtil = getUtil();
        if (clanUtil.getClan(shooter) != null) {
            if (clanUtil.getClan(shot) != null) {
                if (clanUtil.getClan(shooter).equals(clanUtil.getClan(shot))) {
                    setCanHurt(false);
                    clanUtil.sendMessage(shooter, "&c&oYou cannot hurt allies!");
                    return;
                }
                if (clanUtil.getAllies(clanUtil.getClan(shooter)).contains(clanUtil.getClan(shot))) {
                    setCanHurt(false);
                    clanUtil.sendMessage(shooter, "&c&oYou cannot hurt allies!");
                    return;
                }
            }
        }
    }

    public boolean canHurt() {
        return cancelled;
    }

    public void setCanHurt(boolean b) {
        if (b)
            this.cancelled = false;
        if (!b)
            this.cancelled = true;
    }
}
