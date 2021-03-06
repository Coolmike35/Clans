package com.youtube.hempfest.clans.util.events;

import com.youtube.hempfest.clans.util.StringLibrary;
import com.youtube.hempfest.clans.util.construct.Claim;
import com.youtube.hempfest.clans.util.construct.ClaimUtil;
import com.youtube.hempfest.clans.util.construct.Clan;
import com.youtube.hempfest.clans.util.construct.ClanUtil;
import com.youtube.hempfest.clans.util.listener.ClanEventBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ClaimBuildEvent extends ClanEventBuilder {

    private static final HandlerList handlers = new HandlerList();

    private final Player p;
    private final Location location;

    private boolean cancelled;

    public ClaimBuildEvent(Player p, Location location) {
        this.p = p;
        this.location = location;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public HandlerList getHandlerList() {
        return handlers;
    }

    public ClaimUtil getClaimUtil() {
        return Claim.claimUtil;
    }

    @Override
    public ClanUtil getUtil() {
        return Clan.clanUtil;
    }

    @Override
    public StringLibrary stringLibrary() {
        return new StringLibrary();
    }

    public Claim getClaim() {
        return new Claim(getClaimUtil().getClaimID(location), p);
    }

    public void handleCheck() {
        if (getClaimUtil().isInClaim(location)) {
            if (getUtil().getClan(p) != null) {
                if (!getClaim().getOwner().equals(getUtil().getClan(p))) {
                    if (!getUtil().getAllies(getClaim().getOwner()).contains(getUtil().getClan(p))) {
                        setCancelled(true);
                        StringLibrary stringLibrary = new StringLibrary();
                        stringLibrary.sendMessage(p, "&c&oYou cannot do this here, land owned by: " + getUtil().clanRelationColor(getUtil().getClan(p), getClaim().getOwner()) + getUtil().getClanTag(getClaim().getOwner()));
                    }
                }
            } else {
                setCancelled(true);
                StringLibrary stringLibrary = new StringLibrary();
                stringLibrary.sendMessage(p, "&c&oYou cannot do this here, land owned by: &e&o&n" + getUtil().getClanTag(getClaim().getOwner()));
            }
        }
    }

}
