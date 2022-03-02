package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.gui.SignalGui;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

@Getter
public class SwitchPositionData implements PositionData {

  private Position from, to;
  private final Position position;
  private byte currentSet;
  private boolean settable;

  public SwitchPositionData(Position position, byte currentSet) {
    this.position = position;
    this.currentSet = currentSet;

    selectFromAndTo();
  }

  private void selectFromAndTo() {
    switch (currentSet) {
      case 7:
      case 0:
      case 4:
      case 5:
        from = new Position(0, 0);
        to = new Position(0, 1);
        break;

      case 2:
      case 1:
      case 3:
      case 9:
        from = new Position(0, 0);
        to = new Position(1, 0);
        break;

      case 6:
        from = new Position(1, 0);
        to = new Position(0, 1);
        break;

      case 8:
        from = new Position(0, 0);
        to = new Position(0, 0);
        break;


    }
  }

  @Override
  public void handleClick(MouseEvent mouseEvent) {
    if (!settable) return;
    SignalGui instance = SignalGui.getInstance();
    switch (currentSet) {
      case 9:
        currentSet = 0;
        break;

      case 1: case 2: case 3: case 4:
        currentSet = 6;
        break;

      default:
        currentSet++;
        break;
    }

    instance.getMinecraftAdapter().setPosition(position, currentSet);
    selectFromAndTo();
    SignalGui.getInstance().repaint();
  }

  public void set(byte b) {
    if (!settable) return;
    currentSet = b;
    SignalGui instance = SignalGui.getInstance();
    instance.getMinecraftAdapter().setPosition(position, currentSet);
    selectFromAndTo();
    SignalGui.getInstance().repaint();
  }

  @Override
  public void draw(Position position, Graphics graphics, JFrame jFrame) {
    Position coordinate = position.toCoordinate();

    int startX = 0;
    int startY = 0;

    int targetX = 0;
    int targetY = 0;

    RailPosition railPosition;

    switch (currentSet) {
      case 5:
        graphics.setColor(Color.GREEN);
        railPosition = RailPosition.VERTICAL;
        break;

      case 4:
        graphics.setColor(Color.RED);
        railPosition = RailPosition.VERTICAL;
        break;

      case 3:
        graphics.setColor(Color.RED);
        railPosition = RailPosition.HORIZONTAL;
        break;

      case 2:
        graphics.setColor(Color.GREEN);
        railPosition = RailPosition.HORIZONTAL;
        break;

      default:
        railPosition = RailPosition.getFromId(currentSet).orElse(null);
        graphics.setColor(Color.BLACK);
        settable = true;
        break;
    }

    switch (Objects.requireNonNull(railPosition)) {
      case HORIZONTAL:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.25);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.75);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;

      case VERTICAL:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.25);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.75);
        break;

      case SOUTH_EAST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.75);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.75);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;

      case NORTH_EAST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.25);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.75);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;

      case SOUTH_WEST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.25);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.75);
        break;

      case NORTH_WEST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.25);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.25);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;
    }

    graphics.drawLine(startX, startY, targetX, targetY);

    if (settable) {
      graphics.setColor(Color.BLUE);
      Graphics2D g2d = (Graphics2D) graphics;
      g2d.fillRect(coordinate.getX(), coordinate.getY(), 3, 3);
      graphics.setColor(Color.BLACK);
    }
  }

}
