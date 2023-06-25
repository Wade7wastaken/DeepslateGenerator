package me.wade7.deepslategenerator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeepslateGenerator extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	private boolean isTouching(Block b, Material m) {
		return (b.getRelative(BlockFace.NORTH).getType().equals(m) ||
				b.getRelative(BlockFace.SOUTH).getType().equals(m) ||
				b.getRelative(BlockFace.EAST).getType().equals(m) ||
				b.getRelative(BlockFace.WEST).getType().equals(m));
	}

	private boolean shouldGenerate(Block block, Block toBlock) {
		return block.getType() == Material.LAVA &&
				isTouching(toBlock, Material.WATER) &&
				isTouching(toBlock, Material.DEEPSLATE) &&
				toBlock.getLocation().getBlockY() <= 0;
	}

	@EventHandler
	public void onBlockFlow(BlockFromToEvent event) {
		Block toBlock = event.getToBlock();
		Block block = event.getBlock();

		if (shouldGenerate(block, toBlock)) {
			toBlock.setType(Material.COBBLED_DEEPSLATE);

			event.setCancelled(true);
		}
	}
}
