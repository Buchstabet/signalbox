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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RailRouteLoader extends JavaPlugin implements Listener {

  private final List<Location> already = new ArrayList<>();
  Location start = new Location(Bukkit.getWorld("world"), 151, 75, 173);
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

  @Override
  public void onEnable() {
    signalGui = new SignalGui(coordinates);
    signalGui.display();

    find(start, 0, 0, 0);

    getServer().getPluginManager().registerEvents(this, this);
  }


  @Override
  public void onDisable() {
    signalGui.setVisible(false);
  }

  private void find(Location start) {
    Bukkit.getScheduler().runTaskLater(this, () -> {
      find(start, 0, 0, 1);
      find(start, 1, 0, 0);
      find(start, 0, 0, -1);
      find(start, -1, 0, 0);

      find(start, 0, 1, 1);
      find(start, 1, 1, 0);
      find(start, 0, 1, -1);
      find(start, -1, 1, 0);

      find(start, 0, -1, 1);
      find(start, 1, -1, 0);
      find(start, 0, -1, -1);
      find(start, -1, -1, 0);
    }, 1);
  }

  public void find(Location start, int x, int y, int z) {
    Location location = start.clone().add(x, y, z);
    if (already.contains(location)) return;
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
          positionData = new SwitchPositionData(new Position(0, 0), new Position(0, 1));
          break;

        case 9:
          positionData = new SwitchPositionData(new Position(0, 0), new Position(1, 0));
          break;

        case 6:
          positionData = new SwitchPositionData(new Position(1, 0), new Position(0, 1));
          break;

        case 8:
          positionData = new SwitchPositionData(new Position(0, 0), new Position(0, 0));
          break;

        default:
          positionData = new RailPositionData(true);
          break;
      }

      Position position = new Position(location.getBlockX() - this.start.getBlockX(), location.getBlockZ() - this.start.getBlockZ());
      System.out.println(position);
      coordinates.setPositionData(position, positionData);
      positionData.draw(position, signalGui.getGraphics());

      find(location);
      already.add(location);
    }
  }

}
