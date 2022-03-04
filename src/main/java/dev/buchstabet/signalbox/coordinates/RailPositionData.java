package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.switchmanager.SwitchManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;

import java.awt.*;
import java.util.Objects;

@Getter
public class RailPositionData implements PositionData {

    public static Position start = null;

    private final Position position;
    private byte currentSet;
    @Setter private boolean settable;
    @Setter private boolean set;
    private final Material material;
    private final Location location;
    private Minecart minecart;

    public RailPositionData(Position position, byte currentSet, Material material, Location location) {
        this.position = position;
        this.currentSet = currentSet;
        this.material = material;
        this.location = location;
        handleSettable();
    }


    @Override
    public void handleClick() {

    }

    @Override
    public void setOccupied(Minecart minecart) {
        this.minecart = minecart;
        setSet(false);
    }

    @Override
    public boolean isOccupied() {
        return minecart != null;
    }

    public void set(byte b) {
        if (!settable) return;
        currentSet = b;
        SwitchManager.setSwitch(position, currentSet);
    }

    @Override
    public void draw(Graphics graphics) {
        Position coordinate = position.toCoordinate();
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

        graphics.setColor(minecart != null ? Color.RED : set ? Color.GREEN : Color.YELLOW);
        graphics.drawLine(startX, startY, targetX, targetY);
    }



}
