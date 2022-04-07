package dev.buchstabet.signalbox.trackvacancydetectionsystem;

import dev.buchstabet.signalbox.Signalbox;
import dev.buchstabet.signalbox.codenumber.CodeNumber;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.SignalPositionData;
import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.pathfinder.PathPosition;
import dev.buchstabet.signalbox.pathfinder.PathfinderAlgorithm;
import lombok.Data;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// Gleisfreimeldeanlage
public class TrackVacancyDetectionSystem implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onVehicleMove(VehicleMoveEvent event) {
    Vehicle vehicle = event.getVehicle();
    if (!(vehicle instanceof Minecart)) return;
    if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;

    SignalGui signalGui = SignalGui.getInstance();
    {
      Position position = Signalbox.getPlugin(Signalbox.class).getLocationMap().get(event.getFrom().getBlock().getLocation());
      if (position != null) {
        position.getPositionData().ifPresent(data -> {
          if (data.isOccupied()) {
            data.setOccupied(null);
            data.draw(signalGui.getPanel().getGraphics());
          }
        });
      }
    }


    {
      Position position = Signalbox.getPlugin(Signalbox.class).getLocationMap().get(event.getTo().getBlock().getLocation());
      if (position != null) {
        position.getPositionData().ifPresent(data -> {
          if (!data.isOccupied()) {
            data.setOccupied((Minecart) vehicle);
            data.draw(signalGui.getPanel().getGraphics());
          }

          if (data instanceof SignalPositionData) {
            SignalPositionData signalPositionData = (SignalPositionData) data;
            if (!signalPositionData.getSignal().canDrive()) {
              if (SignalPositionData.REGISTERED_MINECART.containsKey(vehicle.getUniqueId())) {
                signalGui.
                        getCodeNumberLoader().
                        getCodeNumbers().
                        getCodeNumbers().
                        stream().
                        filter(codeNumber -> codeNumber.getIdentifier() == SignalPositionData.REGISTERED_MINECART.get(vehicle.getUniqueId())).
                        findAny().flatMap(codeNumber -> codeNumber.getRailRoutes().stream().filter(railRoute -> railRoute.getStart().getPosition().equals(position)).findAny()).
                        ifPresent(railRoute -> {
                          CompletableFuture<Collection<PathPosition>> future = new PathfinderAlgorithm(railRoute.getStart().getPosition(), railRoute.getTarget().getPosition(), signalGui.getCoordinates()).paintBestWay();
                          future.thenAccept(pathPositions -> {
                            signalGui.repaint();
                            signalGui.getStart().setBackground(Color.GREEN);
                            if (!pathPositions.isEmpty()) signalGui.getLogFrame().log("Der Selbststellbetrieb wurde aktiv");
                          });
                        });
              }

              vehicle.setVelocity(new Vector(0, 0, 0));
            }
          }
        });
      }
    }

  }


  @EventHandler(ignoreCancelled = true)
  public void onEntitySpawn(VehicleCreateEvent event) {
    Entity vehicle = event.getVehicle();
    if (!(vehicle instanceof Minecart)) return;

    Position position = Signalbox.getPlugin(Signalbox.class).getLocationMap().get(event.getVehicle().getLocation().getBlock().getLocation());
    if (position == null) return;
    position.getPositionData().ifPresent(data -> {
      if (!data.isOccupied()) {
        SignalGui.getInstance().getLogFrame().log("A vehicle was spawned");

        data.setOccupied((Minecart) vehicle);
        data.draw(SignalGui.getInstance().getPanel().getGraphics());
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
        data.setOccupied(null);
        data.draw(SignalGui.getInstance().getPanel().getGraphics());
      }
    });
  }

  @Data
  private static class StoppedMinecart {
    private final Minecart minecart;
    private final Vector vector;
    private final SignalPositionData signalPositionData;
  }
}
