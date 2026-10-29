
package me.worlditemblocker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldItemBlocker extends JavaPlugin
        implements Listener {

    private boolean blocked(Player p) {
        String w = p.getWorld().getName();
        return w.equalsIgnoreCase("Serverlogin")
            || w.equalsIgnoreCase("serverlobby");
    }

    private void clear(Player p) {
        if (!blocked(p)) return;
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.getInventory().setItemInOffHand(null);
        p.setItemOnCursor(null);
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                clear(p);
            }
        }, 1L, 2L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        clear(e.getPlayer());
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        clear(e.getPlayer());
    }
}
