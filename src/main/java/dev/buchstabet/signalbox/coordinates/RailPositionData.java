package dev.buchstabet.signalbox.coordinates;

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
    private final byte currentSet;
    @Setter private boolean set;
    private final Material material;
    private final Location location;
    private Minecart minecart;

    public RailPositionData(Position position, byte currentSet, Material material, Location location) {
        this.position = position;
        this.currentSet = currentSet;
        this.material = material;
        this.location = location;
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



}
