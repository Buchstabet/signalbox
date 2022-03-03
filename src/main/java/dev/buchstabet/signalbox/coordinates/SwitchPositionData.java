package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.helpbuttons.FHT;
import dev.buchstabet.signalbox.pathfinder.PathfinderAlgorithm;
import dev.buchstabet.signalbox.switchmanager.SwitchManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

@Getter
public class SwitchPositionData implements PositionData {

    private static Position start = null;

    private final Position position;
    private byte currentSet;
    @Setter private boolean settable;
    private JButton button;
    @Setter private boolean set;
    private final boolean hasButton;
    private boolean occupied;
    private final Material material;

    public SwitchPositionData(Position position, byte currentSet, boolean hasButton, Material material) {
        this.position = position;
        this.currentSet = currentSet;
        this.hasButton = hasButton;
        this.material = material;

        if (hasButton) {
            button = new JButton();
            button.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (FHT.pressed) {
                        FHT.fht(position);
                    } else handleClick();
                }
            });
            SignalGui.getInstance().getPanel().add(button);
        }

        handleSettable();
    }

    private void handleSettable() {
        if (material != Material.RAILS) settable = false;
        switch (currentSet) {
            case 5: case 2: case 3: case 4:
                settable = false;
                break;
            default:
                settable = true;
        }
    }


    @Override
    public void handleClick() {
        if (start == null)
            start = position;
        else {
            new PathfinderAlgorithm(start, position, SignalGui.getInstance().getCoordinates()).paintBestWay();
            start = null;
        }


        /*

        Code to change the setting of the switch

        SignalGui instance = SignalGui.getInstance();
        switch (currentSet) {
            case 9:
                currentSet = 0;
                break;

            case 1:
            case 2:
            case 3:
            case 4:
                currentSet = 6;
                break;

            default:
                currentSet++;
                break;
        }

        instance.getMinecraftAdapter().setPosition(position, currentSet);
        selectFromAndTo();
        SignalGui.getInstance().repaint();
         */
    }

    @Override
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
        this.set = false;
    }

    public void set(byte b) {
        if (!settable) return;
        currentSet = b;
        SwitchManager.setSwitch(position, currentSet);
        SignalGui.getInstance().repaint();
    }

    @Override
    public void draw(Position position, Graphics graphics) {
        Position coordinate = position.toCoordinate();
        if (hasButton) {
            button.setBounds(coordinate.getX(), coordinate.getY(), Coordinates.COORDINATE_SIZE, Coordinates.COORDINATE_SIZE);
        } else {

            int startX = 0;
            int startY = 0;

            int targetX = 0;
            int targetY = 0;

            RailPosition railPosition = RailPosition.getFromId(currentSet, material);

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

            graphics.setColor(occupied ? Color.RED : set ? Color.GREEN : settable ? Color.YELLOW : Color.MAGENTA);
            graphics.drawLine(startX, startY, targetX, targetY);
        }
    }

}
