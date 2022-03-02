package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.gui.SignalGui;

import java.awt.*;

public interface PositionData {

  void draw(Position position, Graphics graphics, SignalGui jFrame);

  void handleClick();

  byte getCurrentSet();

  void set(byte b);

  Position getPosition();

}
