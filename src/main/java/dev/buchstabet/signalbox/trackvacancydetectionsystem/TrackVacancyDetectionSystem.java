package dev.buchstabet.signalbox.trackvacancydetectionsystem;

import dev.buchstabet.signalbox.Signalbox;
import dev.buchstabet.signalbox.gui.SignalGui;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

// Gleisfreimeldeanlage
public class TrackVacancyDetectionSystem implements Listener {


  @EventHandler(ignoreCancelled = true)
  public void onVehicleMove(VehicleMoveEvent event) {
    Vehicle vehicle = event.getVehicle();
    if (!(vehicle instanceof Minecart)) return;
    if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;


    // Man kann nicht in einem Move Event durch eine Map ganz durchgehen

    Signalbox.getPlugin(Signalbox.class).getLocationPositionMap().forEach((position, location) -> {
      if (location.getBlock().equals(event.getFrom().getBlock())) {
        position.getPositionData().ifPresent(data -> {
          if (data.isOccupied()) {
            data.setOccupied(false);
            data.draw(position, SignalGui.getInstance().getPanel().getGraphics());
          }
        });
      }

      if (location.getBlock().equals(event.getTo().getBlock())) {
        position.getPositionData().ifPresent(data -> {
          if (!data.isOccupied()){
            data.setOccupied(true);
            data.draw(position, SignalGui.getInstance().getPanel().getGraphics());
          }
        });
      }
    });
  }


  /*


  |
  |    funktioniert nicht
  \/

   */

  @EventHandler(ignoreCancelled = true)
  public void onEntitySpawn(VehicleCreateEvent event) {
    Entity vehicle = event.getVehicle();
    if (!(vehicle instanceof Minecart)) return;
    Signalbox.getPlugin(Signalbox.class).getLocationPositionMap().forEach((position, location) -> {
      if (location.getBlock().equals(event.getVehicle().getLocation().getBlock())) {
        position.getPositionData().ifPresent(data -> {
          if (data.isOccupied()) {
            data.setOccupied(true);
            data.draw(position, SignalGui.getInstance().getPanel().getGraphics());
          }
        });
      }
    });
  }
}
