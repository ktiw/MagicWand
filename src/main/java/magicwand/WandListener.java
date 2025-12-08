package magicwand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WandListener implements Listener {

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long COOLDOWN_TIME = 5000;

    @EventHandler
    public void onUseWand(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.BLAZE_ROD) {
            return;
        }

        if (!item.hasItemMeta() || !item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Магический посох")) {
            return;
        }

        if (checkCooldown(player)) {
            return;
        }

        Block targetBlock = player.getTargetBlockExact(50);
        if (targetBlock != null) {
            Location loc = targetBlock.getLocation();

            player.getWorld().strikeLightning(loc);

            player.sendMessage(ChatColor.AQUA + "Бабах!");

            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + COOLDOWN_TIME);
        } else {
            player.sendMessage(ChatColor.RED + "Слишком далеко!");
        }
    }

    private boolean checkCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        if (cooldowns.containsKey(uuid)) {
            long endsAt = cooldowns.get(uuid);
            long current = System.currentTimeMillis();
            if (current < endsAt) {
                long left = (endsAt - current) / 1000;
                player.sendMessage(ChatColor.RED + "Кулдаун: " + (left + 1) + " сек.");
                return true;
            }
        }
        return false;
    }
}
