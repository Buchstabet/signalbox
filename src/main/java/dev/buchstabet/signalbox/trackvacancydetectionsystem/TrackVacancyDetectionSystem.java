package dev.buchstabet.signalbox.trackvacancydetectionsystem;

import dev.buchstabet.signalbox.Signalbox;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.gui.SignalGui;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

// Gleisfreimeldeanlage
public class TrackVacancyDetectionSystem implements Listener {


  @EventHandler(ignoreCancelled = true)
  public void onVehicleMove(VehicleMoveEvent event) {
    Vehicle vehicle = event.getVehicle();
    if (!(vehicle instanceof Minecart)) return;
    if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;

    {
      Position position = Signalbox.getPlugin(Signalbox.class).getLocationMap().get(event.getFrom().getBlock().getLocation());
      if (position != null) {
        position.getPositionData().ifPresent(data -> {
          if (data.isOccupied()) {
            data.setOccupied(false);
            data.draw(position, SignalGui.getInstance().getPanel().getGraphics());
          }
        });
      }
    }


    {
      Position position = Signalbox.getPlugin(Signalbox.class).getLocationMap().get(event.getTo().getBlock().getLocation());
      if (position != null) {
        position.getPositionData().ifPresent(data -> {
          if (!data.isOccupied()){
            data.setOccupied(true);
            data.draw(position, SignalGui.getInstance().getPanel().getGraphics());
          }
        });
      }
    }

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

    Position position = Signalbox.getPlugin(Signalbox.class).getLocationMap().get(event.getVehicle().getLocation().getBlock().getLocation());
    if (position == null) return;
    position.getPositionData().ifPresent(data -> {
      if (!data.isOccupied()) {
        SignalGui.getInstance().getLogFrame().log("A vehicle was spawned");

        data.setOccupied(true);
        data.draw(position, SignalGui.getInstance().getPanel().getGraphics());
      }
    });
  }

  @EventHandler(ignoreCancelled = true)
  public void onEntityDestroy(VehicleDestroyEvent event) {
    Entity vehicle = event.getVehicle();
    if (!(vehicle instanceof Minecart)) return;

    Position position = Signalbox.getPlugin(Signalbox.class).getLocationMap().get(event.getVehicle().getLocation().getBlock().getLocation());
    if (position == null) return;
    position.getPositionData().ifPresent(data -> {
      if (data.isOccupied()) {
        SignalGui.getInstance().getLogFrame().log("A vehicle was killed");
        data.setOccupied(false);
        data.draw(position, SignalGui.getInstance().getPanel().getGraphics());
      }
    });
  }
}
