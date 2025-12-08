package magicwand;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.hasPermission("magicwand.give")) {
            player.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        
        player.getInventory().addItem(createWand());
        player.sendMessage(ChatColor.GREEN + "Магический посох получен!");

        return true;
    }

    private ItemStack createWand() {
        ItemStack wand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = wand.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "Магический посох");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Древний артефакт.");
            lore.add(ChatColor.GRAY + "Нажми ПКМ для магии!");
            meta.setLore(lore);
            wand.setItemMeta(meta);
        }
        return wand;
    }
}
