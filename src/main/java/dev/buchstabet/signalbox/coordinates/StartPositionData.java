package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.Signalbox;
import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.helpbuttons.FRT;
import dev.buchstabet.signalbox.pathfinder.PathPosition;
import dev.buchstabet.signalbox.pathfinder.PathfinderAlgorithm;
import dev.buchstabet.signalbox.signal.SignalPosition;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Getter
public class StartPositionData implements PositionData {

  public static Position start = null;

  private final Position position;
  @Getter private final byte currentSet;
  private final JButton button;
  private boolean set;
  private final Material material;
  @Getter private SignalPosition signal;
  @Getter private final Location location;
  @Getter private Location next;
  private Minecart minecart;

  public StartPositionData(Position position, byte currentSet, Material material, Location location) {
    this.position = position;
    this.currentSet = currentSet;
    this.material = material;
    this.location = location;

    button = new JButton();
    button.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (FRT.pressed) {
          FRT.frt(position);
        } else handleClick();
      }
    });
    SignalGui.getInstance().getPanel().add(button);

    handleSettable();

    signal = SignalPosition.STOP;
  }

  public void setNext(Location next) {
    this.next = next;

    if (isOccupied() && next != null) {
      minecart.setVelocity(calculateVelocity(next, minecart.getLocation()));
    }
  }

  public Vector calculateVelocity(Location next, Location location) {
    Vector vector = next.toVector();
    vector.subtract(location.toVector());
    vector.multiply(3);
    return vector;
  }

  @Override
  public void handleClick() {
    SignalGui signalGui = SignalGui.getInstance();
    if (start == null) {
      signalGui.getLogFrame().log("Start was set");
      start = position;
      signalGui.getStart().setBackground(Color.RED);
    } else {
      CompletableFuture<Collection<PathPosition>> future = new PathfinderAlgorithm(start, position, signalGui.getCoordinates()).paintBestWay();
      start = null;
      future.thenAccept(pathPositions -> {
        signalGui.repaint();
        signalGui.getStart().setBackground(Color.GREEN);
        signalGui.getLogFrame().log(pathPositions.isEmpty() ? "No rail route found" : "Rail route found");
      });
    }
  }

  @Override
  public boolean isOccupied() {
    return minecart != null;
  }

  @Override
  public void setOccupied(Minecart minecart) {
    this.set = false;
    this.minecart = minecart;

    if (!isOccupied()) {
      signal = SignalPosition.STOP;
      next = null;
    } else if (next != null) {
      Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Signalbox.class), () -> minecart.setVelocity(calculateVelocity(next, minecart.getLocation())));
    }

  }

  @Override
  public void setSet(boolean set) {
    this.set = set;
    if (!set) {
      signal = SignalPosition.STOP;
      next = null;
    }
  }

  @Override
  public boolean isSettable() {
    return false;
  }

  @Override
  public void setSettable(boolean b) {

  }

  public void set(byte b) {

  }

  @Override
  public void draw(Graphics graphics) {
    Position coordinate = position.toCoordinate();

    button.setBackground(isOccupied() ? new Color(182, 0, 12) : signal == SignalPosition.STOP ? new Color(255, 102, 99) : Color.GREEN);
    button.setBounds(coordinate.getX(), coordinate.getY(), Coordinates.COORDINATE_SIZE, Coordinates.COORDINATE_SIZE);

  }

  public void setSignalPosition(SignalPosition drive) {
    signal = drive;
    button.setBackground(isOccupied() ? new Color(182, 0, 12) : signal == SignalPosition.STOP ? new Color(255, 102, 99) : Color.GREEN);
  }
}
