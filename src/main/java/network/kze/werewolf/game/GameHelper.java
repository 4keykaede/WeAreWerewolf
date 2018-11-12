package network.kze.werewolf.game;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@UtilityClass
public class GameHelper {

    public final ItemStack MAIN_WEAPON;

    private boolean flash;

    static {
        MAIN_WEAPON = new ItemStack(Material.BOW);
        ItemMeta meta = MAIN_WEAPON.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 99, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        MAIN_WEAPON.setItemMeta(meta);
    }


    public String getWaitingMessage() {
        if (flash) {
            flash = false;
            return ChatColor.WHITE + "参加プレイヤー募集中";
        } else {
            flash = true;
            return ChatColor.AQUA + "参加プレイヤー募集中";
        }
    }

    public void giveWeapon(Player player) {
        player.getInventory().setItem(0, MAIN_WEAPON);
        player.getInventory().setItem(15, new ItemStack(Material.ARROW));
    }

    public void checkGameEnd(Game game) {
        if (game.getVillagers().size() <= 0) {
            game.setResult(Game.Result.WOLFS_WIN);
        }

        if (game.getWolfs().size() <= 0) {
            game.setResult(Game.Result.VILLAGERS_WIN);
        }
    }


}
