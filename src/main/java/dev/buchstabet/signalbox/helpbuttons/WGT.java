package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.coordinates.SwitchPositionData;
import dev.buchstabet.signalbox.gui.SignalGui;

import java.awt.*;
import java.awt.event.ActionEvent;

public class WGT extends HelpButton {

  public static boolean pressed;

  public WGT(int x, int y, String text) {
    super(x, y, text);
    setBackground(Color.GREEN);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    pressed = !pressed;
    setBackground(pressed ? Color.RED : Color.GREEN);
    SignalGui.getInstance().getLogFrame().log("WGT was pressed");

    if (pressed) {
      SignalGui.getInstance().getCoordinates().getPositions()
              .values()
              .stream()
              .filter(positionData -> positionData instanceof SwitchPositionData)
              .map(positionData -> (SwitchPositionData) positionData)
              .filter(switchPositionData -> !switchPositionData.isSet())
              .forEach(switchPositionData -> SignalGui.getInstance().getPanel().add(switchPositionData.getButton()));
    } else {
      SignalGui.getInstance().getCoordinates().getPositions()
              .values()
              .stream()
              .filter(positionData -> positionData instanceof SwitchPositionData)
              .map(positionData -> (SwitchPositionData) positionData)
              .forEach(switchPositionData -> SignalGui.getInstance().getPanel().remove(switchPositionData.getButton()));
    }

    SignalGui.getInstance().repaint();
  }

  public void use() {
    if (pressed) actionPerformed(null);
    SignalGui.getInstance().getLogFrame().log("WGT was used");
  }

}
