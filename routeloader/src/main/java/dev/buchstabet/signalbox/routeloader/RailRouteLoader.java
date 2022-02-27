package dev.buchstabet.signalbox.routeloader;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.RailPositionData;
import dev.buchstabet.signalbox.gui.SignalGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RailRouteLoader extends JavaPlugin {

  private final List<Location> already = new ArrayList<>();
  Location start = new Location(Bukkit.getWorld("world"), 151, 75, 173);
  Coordinates coordinates = new Coordinates(new HashMap<>());

  private SignalGui signalGui;

  /*
  Höhenunterschiede werden noch nicht erkannt, da wir ja nur überprüfen, ob um uns herum eine Schiene ist.
   */

  @Override
  public void onEnable() {
    signalGui = new SignalGui(coordinates);
    signalGui.display();

    find(start, 0, 0);
  }

  @Override
  public void onDisable() {
    signalGui.setVisible(false);
  }

  private void find(Location start) {
    Bukkit.getScheduler().runTaskLater(this, () -> {
      find(start, 0, 1);
      find(start, 1, 0);
      find(start, 0, -1);
      find(start, -1, 0);
    }, 1);
  }

  public void find(Location start, int x, int z) {
    Location location = start.clone().add(x, 0, z);
    if (already.contains(location)) return;
    if (location.getBlock().getType() == Material.RAILS) {

      RailPositionData positionData = new RailPositionData(false);
      Position position = new Position(location.getBlockX() - this.start.getBlockX(), location.getBlockZ() - this.start.getBlockZ());
      System.out.println(position);
      coordinates.setPositionData(position, positionData);
      positionData.draw(position, signalGui, signalGui.getGraphics());

      find(location);
      already.add(location);
    }
  }

}
