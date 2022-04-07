package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.Signalbox;
import dev.buchstabet.signalbox.codenumber.RailRoute;
import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.gui.TrainAddGui;
import dev.buchstabet.signalbox.helpbuttons.SGT;
import dev.buchstabet.signalbox.helpbuttons.FHT;
import dev.buchstabet.signalbox.helpbuttons.HaGT;
import dev.buchstabet.signalbox.pathfinder.PathPosition;
import dev.buchstabet.signalbox.pathfinder.PathfinderAlgorithm;
import dev.buchstabet.signalbox.signal.SignalPosition;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class SignalPositionData implements PositionData {

  public static final Map<UUID, Integer> REGISTERED_MINECART = new HashMap<>();

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

  public SignalPositionData(Position position, byte currentSet, Material material, Location location) {
    this.position = position;
    this.currentSet = currentSet;
    this.material = material;
    this.location = location;

    button = new JButton();
    button.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

    final SignalGui signalGui = SignalGui.getInstance();

    SignalPositionData positionData = this;

    button.addMouseListener(new MouseAdapter() {
      boolean pressed;

      @Override
      public void mousePressed(MouseEvent e) {
        button.getModel().setArmed(true);
        button.getModel().setPressed(true);
        pressed = true;
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        //if(isRightButtonPressed) {underlyingButton.getModel().setPressed(true));
        button.getModel().setArmed(false);
        button.getModel().setPressed(false);

        if (pressed) {
          if (SwingUtilities.isRightMouseButton(e)) {

            new TrainAddGui(positionData);

          } else {
            if (FHT.pressed) {
              FHT.frt(position);
            } else if (SGT.pressed) {
              setSignalPosition(SignalPosition.BEACON);
              signalGui.getSGT().use();
            } else if (HaGT.pressed) {
              setSignalPosition(SignalPosition.STOP);
              signalGui.getHaGT().use();
            } else handleClick();
          }
        }
        pressed = false;

      }

      @Override
      public void mouseExited(MouseEvent e) {
        pressed = false;
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        pressed = true;
      }
    });

    signalGui.getPanel().add(button);

    this.isSettable();

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

      String text = SignalGui.getInstance().getCodeNumberSelection().getText();

      if (!text.equals("")) {
        try {
          int number = Integer.parseInt(text);
          assert SignalGui.getInstance().getCodeNumberLoader().getCodeNumbers() != null;
          SignalGui.getInstance().getCodeNumberLoader().getCodeNumbers().getCodeNumbers().stream().filter(codeNumber ->
            codeNumber.getIdentifier() == number).findAny().ifPresent(codeNumber -> codeNumber.getRailRoutes().add(new RailRoute(start.getPositionData().orElse(null), position.getPositionData().orElse(null))));
          SignalGui.getInstance().getCodeNumberLoader().save();
          start = null;
          SignalGui.getInstance().getLogFrame().log("RailRoute saved");
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
        return;
      }

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
      if (signal == SignalPosition.DRIVE) signal = SignalPosition.STOP;
      next = null;
    } else if (next != null) {
      Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Signalbox.class), () -> minecart.setVelocity(calculateVelocity(next, minecart.getLocation())));
    }

  }

  @Override
  public void setSet(boolean set) {
    this.set = set;
    if (!set) {
      if (signal == SignalPosition.DRIVE) signal = SignalPosition.STOP;
      next = null;
    }
  }

  @Override
  public void draw(Graphics graphics) {
    Position coordinate = position.toCoordinate();

    setButtonColor();
    button.setBounds(coordinate.getX(), coordinate.getY(), Coordinates.COORDINATE_SIZE, Coordinates.COORDINATE_SIZE);

  }

  private void setButtonColor() {
    button.setBackground(isOccupied() ? Color.cyan : signal == SignalPosition.STOP ? Color.red : signal == SignalPosition.BEACON ? Color.white : Color.GREEN);
  }

  public void setSignalPosition(SignalPosition drive) {
    signal = drive;
    setButtonColor();
  }
}
