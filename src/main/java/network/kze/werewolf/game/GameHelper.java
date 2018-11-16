package network.kze.werewolf.game;

import lombok.experimental.UtilityClass;
import network.kze.werewolf.game.player.GamePlayer;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    //とりあえず
    public void tpStage(GameManager gameManager) {
        List<Location> locations = new ArrayList<>();
        World w = Bukkit.getWorld("world");
        locations.add(new Location(w, -100, 38, 659));
        locations.add(new Location(w, -161, 36, 630));
        locations.add(new Location(w, -169, 38, 570));
        locations.add(new Location(w, -115, 42, 554));
        locations.add(new Location(w, -53, 40, 545));
        locations.add(new Location(w, -34, 40, 580));
        locations.add(new Location(w, -39, 38, 583));
        locations.add(new Location(w, -21, 51, 588));
        locations.add(new Location(w, -42, 40, 618));
        locations.add(new Location(w, -61, 34, 636));
        locations.add(new Location(w, -29, 43, 646));
        locations.add(new Location(w, -33, 43, 650));
        Collections.shuffle(locations, new Random());
        int count = 0;
        for (Player player : gameManager.getGame().getJoinPlayers()) {
            player.teleport(locations.get(count));
            count++;
        }
    }


}
