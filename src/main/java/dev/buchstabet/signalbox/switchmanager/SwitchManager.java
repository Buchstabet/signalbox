package dev.buchstabet.signalbox.switchmanager;

import dev.buchstabet.signalbox.Signalbox;
import dev.buchstabet.signalbox.coordinates.Position;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Rails;

public class SwitchManager {

  public static void setSwitch(Position position, byte switchPosition) {
    Bukkit.getScheduler().runTask(Signalbox.getPlugin(Signalbox.class), () -> {
      Block block = Signalbox.getPlugin(Signalbox.class).getLocationPositionMap().get(position).getBlock();
      if (block.getType() != Material.RAILS) return;
      BlockState state = block.getState();
      MaterialData data = state.getData();
      Rails rails = (Rails) data;

      rails.setData(switchPosition);
      state.setData(data);
      state.update();
    });
  }

}
