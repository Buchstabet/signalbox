package dev.buchstabet.signalbox.coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public interface PositionData {

  void draw(Position position, Graphics graphics, JFrame jFrame);

  void handleClick(MouseEvent mouseEvent);

}
