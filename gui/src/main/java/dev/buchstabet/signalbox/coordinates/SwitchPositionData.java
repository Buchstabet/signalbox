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
    SignalGui instance = SignalGui.getInstance();
    if (currentSet == 9) {
      currentSet = 0;
    } else {
      currentSet++;
    }

    instance.getMinecraftAdapter().setPosition(position, currentSet);
    selectFromAndTo();

    instance.setVisible(false);
    instance.setVisible(true);
  }

  @Override
  public void draw(Position position, Graphics graphics, JFrame jFrame) {
    Dimension size = jFrame.getSize();


        /*
        graphics.drawLine(
                (int) (size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE + this.from.getX() * Coordinates.COORDINATE_SIZE),
                (int) (size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE + this.from.getY() * Coordinates.COORDINATE_SIZE),
                (int) (size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE + this.to.getX() * Coordinates.COORDINATE_SIZE),
                (int) (size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE + this.to.getY() * Coordinates.COORDINATE_SIZE));d
         */
    Position coordinate = position.toCoordinate(jFrame);

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

      case 2:
        graphics.setColor(Color.GREEN);
        railPosition = RailPosition.HORIZONTAL;
        break;

      case 3:
        graphics.setColor(Color.RED);
        railPosition = RailPosition.HORIZONTAL;
        break;

      default:
        railPosition = RailPosition.getFromId(currentSet).orElse(null);
        graphics.setColor(Color.BLACK);
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

    graphics.setColor(Color.BLUE);
    Graphics2D g2d = (Graphics2D) graphics;
    g2d.fillRect((int) size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE, (int) size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE, 3, 3);
    graphics.setColor(Color.BLACK);
  }

}
