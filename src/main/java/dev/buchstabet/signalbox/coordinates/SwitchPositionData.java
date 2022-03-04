package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.helpbuttons.WGT;
import dev.buchstabet.signalbox.switchmanager.SwitchManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Getter
public class SwitchPositionData implements PositionData {

  public static Position start = null;

  private final Position position;
  private byte currentSet;
  private final JButton button;
  private boolean set;
  private final Material material;
  @Getter private final Location location;
  private Minecart minecart;
  private final List<Byte> possibleSettings;
  private int currentPosition;

  public SwitchPositionData(Position position, byte currentSet, Material material, Location location, List<Byte> possibleSettings) {
    this.position = position;
    this.currentSet = currentSet;
    this.material = material;
    this.location = location;
    this.possibleSettings = possibleSettings;

    button = new JButton();
    button.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleClick();
      }
    });
    this.isSettable();

    for (Byte possibleSetting : possibleSettings) {
      if (currentSet == possibleSetting) return;
      currentPosition++;
    }
  }

  @Override
  public void handleClick() {

    currentPosition++;
    if (possibleSettings.size() <= currentPosition)
      currentPosition = 0;
    if (isSet()) return;

    if (!WGT.pressed) return;
    SignalGui.getInstance().getWGt().use();

    set(possibleSettings.get(currentPosition));
    SignalGui.getInstance().repaint();
  }

  @Override
  public boolean isOccupied() {
    return minecart != null;
  }

  @Override
  public void setOccupied(Minecart minecart) {
    this.set = false;
    this.minecart = minecart;
  }

  @Override
  public void setSet(boolean set) {
    this.set = set;
  }

  @Override
  public void set(byte b) {
    if (!isSettable()) return;
    currentSet = b;
    SwitchManager.setSwitch(position, currentSet);
  }

  @Override
  public void draw(Graphics graphics) {
    PositionData.super.draw(graphics);
    Position coordinate = position.toCoordinate();

    setButtonColor();
    button.setBounds(coordinate.getX(), coordinate.getY(), Coordinates.COORDINATE_SIZE, Coordinates.COORDINATE_SIZE);

  }

  private void setButtonColor() {
    button.setBackground(minecart != null ? Color.RED : set ? Color.GREEN : Color.YELLOW);
  }
}
