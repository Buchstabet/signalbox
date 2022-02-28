package dev.buchstabet.signalbox.routeloader;

import dev.buchstabet.signalbox.coordinates.*;
import dev.buchstabet.signalbox.gui.SignalGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Rails;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class RailRouteLoader extends JavaPlugin implements Listener {

  private final Map<Position, Location> locationPositionMap = new HashMap<>();
  Location start = new Location(Bukkit.getWorld("world"), -165, 77, -501);
  Coordinates coordinates = new Coordinates(new HashMap<>());

  private SignalGui signalGui;

  @EventHandler(ignoreCancelled = true)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getClickedBlock() == null) return;
    Block clickedBlock = event.getClickedBlock();
    if (clickedBlock.getType() != Material.RAILS) return;
    event.getPlayer().sendMessage("ID: " + getData(clickedBlock));
  }

  private byte getData(Block block) {
    BlockState state = block.getState();
    MaterialData data = state.getData();
    Rails rails = (Rails) data;
    return rails.getData();
  }

  public void launch() {
    signalGui.setVisible(true);
  }

  @Override
  public void onEnable() {
    new SizeGui();

    signalGui = new SignalGui(coordinates, (position, switchPosition) -> Bukkit.getScheduler().runTask(this, () -> {
      Block block = locationPositionMap.get(position).getBlock();
      if (block.getType() != Material.RAILS) return;
      BlockState state = block.getState();
      MaterialData data = state.getData();
      Rails rails = (Rails) data;

      rails.setData(switchPosition);
      state.setData(data);
      state.update();
    }));
    signalGui.display();

    getServer().getPluginManager().registerEvents(this, this);

    Bukkit.getScheduler().runTaskLater(this, () -> find(start, 0, 0, 0), 20 * 5);
  }


  @Override
  public void onDisable() {
    signalGui.setVisible(false);
  }

  private void find(Location start) {
    Bukkit.getScheduler().runTaskLater(this, () -> {
      for (int i = -1; i <= 1; i++) {
        find(start, 0, i, 1);
        find(start, 1, i, 0);
        find(start, 0, i, -1);
        find(start, -1, i, 0);
      }
    }, 1);
  }

  public void find(Location start, int x, int y, int z) {
    Location location = start.clone().add(x, y, z);
    if (locationPositionMap.containsValue(location)) return;
    Position position = new Position(location.getBlockX() - this.start.getBlockX(), location.getBlockZ() - this.start.getBlockZ());
    if (location.getBlock().getType() == Material.RAILS) {

      byte data = getData(location.getBlock());
      PositionData positionData;

      switch (data) {
        case 0: case 4: case 5:
          positionData = new RailPositionData(true);
          break;

        case 2: case 1: case 3:
          positionData = new RailPositionData(false);
          break;

        case 7:

        case 9:

        case 6:

        case 8:
          positionData = new SwitchPositionData(position, data);
          break;

        default:
          positionData = new RailPositionData(true);
          break;
      }

      coordinates.setPositionData(position, positionData);
      positionData.draw(position, signalGui.getGraphics(), signalGui);

      locationPositionMap.put(position, location);
      find(location);
    }
  }

}
