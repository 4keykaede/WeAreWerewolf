package network.kze.werewolf.game;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_13_R2.*;
import network.kze.werewolf.game.player.GamePlayer;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@UtilityClass
public class GameHelper {

    public final ItemStack BASE_SPLASH;
    public final ItemStack MAIN_WEAPON;
    public final ItemStack ITEM_LOD;
    public final ItemStack ITEM_SPLASH;
    public final ItemStack ITEM_TOTEM;
    public final ItemStack ITEM_ELYTRA;
    public final ItemStack ITEM_PICKAXE;
    public final ItemStack ITEM_SHIELD;
    public final ItemStack ITEM_ENERGY;
    public final ItemStack ITEM_PEARL;

    private boolean flash = true;

    static {
        MAIN_WEAPON = new ItemStack(Material.BOW);
        ItemMeta meta = MAIN_WEAPON.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 126, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        MAIN_WEAPON.setItemMeta(meta);

        ITEM_LOD = new ItemStack(Material.STICK);
        ItemMeta lod = ITEM_LOD.getItemMeta();
        lod.setDisplayName("ムダニデカイロッド");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "魔力 +60");
        lore.add(ChatColor.WHITE + "1250");
        lod.setLore(lore);
        ITEM_LOD.setItemMeta(lod);

        BASE_SPLASH = new ItemStack(Material.SPLASH_POTION);
        net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(BASE_SPLASH);
        NBTTagCompound nbtsplash = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.set("Ambient", new NBTTagByte((byte) 0));
        nbt.set("ShowIcon", new NBTTagByte((byte) 1));
        nbt.set("ShowParticles", new NBTTagByte((byte) 1));
        nbt.set("Duration", new NBTTagInt(10));
        nbt.set("Id", new NBTTagByte((byte) 7));
        nbt.set("Amplifier", new NBTTagByte((byte) 126));
        nbttaglist.add(nbt);
        nbtsplash.set("CustomPotionEffects", nbttaglist);
        nmsStack.setTag(nbtsplash);
        ItemStack customSplashStack = CraftItemStack.asBukkitCopy(nmsStack);
        ITEM_SPLASH = customSplashStack;

        ITEM_TOTEM = new ItemStack(Material.TOTEM_OF_UNDYING);

        ITEM_ELYTRA = new ItemStack(Material.ELYTRA);

        ITEM_PICKAXE = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta pickaxeMeta = ITEM_PICKAXE.getItemMeta();
        ((Damageable) pickaxeMeta).setDamage(335);
        pickaxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 99, true);
        ITEM_PICKAXE.setItemMeta(pickaxeMeta);

        ITEM_SHIELD = new ItemStack(Material.SHIELD);
        ItemMeta shieldMeta = ITEM_SHIELD.getItemMeta();
        ((Damageable) shieldMeta).setDamage(335);
        ITEM_SHIELD.setItemMeta(shieldMeta);

        ITEM_ENERGY = new ItemStack(Material.SCUTE);
        ItemMeta energyMeta = ITEM_ENERGY.getItemMeta();
        energyMeta.setDisplayName("モンスターエナジー");
        ArrayList<String> energyLore = new ArrayList<>();
        energyLore.add(ChatColor.GREEN + "役職の能力を鏡華・再演できる");
        energyLore.add(ChatColor.WHITE + "予言・霊媒 能力使用回数を増やせる");
        energyLore.add(ChatColor.WHITE + "人狼 狂人を知ることができる");
        energyMeta.setLore(energyLore);
        ITEM_ENERGY.setItemMeta(energyMeta);

        ITEM_PEARL = new ItemStack(Material.ENDER_PEARL);
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

    public ItemStack getSupportItem(int i) {
        List<ItemStack> items = new ArrayList<>();
        items.add(ITEM_LOD);
        items.add(ITEM_SPLASH);
        items.add(ITEM_TOTEM);
        items.add(ITEM_ELYTRA);
        items.add(ITEM_PICKAXE);
        items.add(ITEM_SHIELD);
        items.add(ITEM_ENERGY);
        items.add(ITEM_PEARL);

        if (i >= items.size()) {
            return ITEM_LOD;
        }

        return items.get(i);
    }

    public void checkGameEnd(Game game) {
        if (game.getVillagers(false).size() <= 0) {
            game.setResult(Game.Result.WOLFS_WIN);
        }

        if (game.getWolfs(false).size() <= 0) {
            game.setResult(Game.Result.VILLAGERS_WIN);
        }
    }

    //とりあえず
    public void tpStage(GameManager gameManager) {
        List<Location> locations = new ArrayList<>();
        World w = Bukkit.getWorld("world");
        /*
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
        */

        locations.add(new Location(w, -261,63,813));
        locations.add(new Location(w, -289,71,821));
        locations.add(new Location(w, -261,63,838));
        locations.add(new Location(w, -249,71,829));
        locations.add(new Location(w, -249,71,807));
        locations.add(new Location(w, -241,63,772));
        locations.add(new Location(w, -284,71,778));
        locations.add(new Location(w, -265,63,782));
        locations.add(new Location(w, -261,63,814));
        locations.add(new Location(w, -273,63,806));
        locations.add(new Location(w, -273,63,822));
        locations.add(new Location(w, -274,71,797));

        Collections.shuffle(locations, new Random());
        int count = 0;
        for (Player player : gameManager.getGame().getJoinPlayers()) {
            player.teleport(locations.get(count));
            count++;
        }
    }


}
