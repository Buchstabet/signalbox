package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.gui.SignalGui;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

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
                from = new Position(0, 0);
                to = new Position(0, 1);
                break;

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
        instance.getMinecraftAdapter().setPosition(position, currentSet += 1);
        selectFromAndTo();

        instance.setVisible(false);
        instance.setVisible(true);
    }

    @Override
    public void draw(Position position, Graphics graphics, JFrame jFrame) {

        Dimension size = jFrame.getSize();

        graphics.setColor(Color.RED);
        graphics.drawLine(
                (int) (size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE + this.from.getX() * Coordinates.COORDINATE_SIZE),
                (int) (size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE + this.from.getY() * Coordinates.COORDINATE_SIZE),
                (int) (size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE + this.to.getX() * Coordinates.COORDINATE_SIZE),
                (int) (size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE + this.to.getY() * Coordinates.COORDINATE_SIZE));
        graphics.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawOval((int) size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE, (int) size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE, 20, 20);
    }

}
