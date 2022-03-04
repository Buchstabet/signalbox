package dev.buchstabet.signalbox;

import dev.buchstabet.signalbox.coordinates.*;
import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.pathfinder.PathfinderAlgorithm;
import dev.buchstabet.signalbox.trackvacancydetectionsystem.TrackVacancyDetectionSystem;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Rails;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Signalbox extends JavaPlugin implements Listener {

    @Getter
    private final Map<Position, Location> locationPositionMap = new HashMap<>();
    @Getter
    private final Map<Location, Position> locationMap = new HashMap<>();
    private Location start;
    private final Coordinates coordinates = new Coordinates(new ConcurrentHashMap<>());
    private SignalGui signalGui;
    private final List<Location> toLoad = new ArrayList<>();

    // Automatisch alle geraden in Strom schienen verwandeln und powern

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock.getType() != Material.RAIL && clickedBlock.getType() != Material.POWERED_RAIL && clickedBlock.getType() != Material.ACTIVATOR_RAIL && clickedBlock.getType() != Material.DETECTOR_RAIL)
            return;
        event.getPlayer().sendMessage("ID: " + getData(clickedBlock));
    }

    private byte getData(Block block) {
        BlockState state = block.getState();
        MaterialData data = state.getData();
        Rails rails = (Rails) data;
        return rails.getData();
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();

        URL url = SignalGui.class.getClassLoader().getResource("icon.png");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setUseCaches(false);
        BufferedImage icon = ImageIO.read(urlConnection.getInputStream());

        ConfigurationSection configurationSection = getConfig().getConfigurationSection("location");

        start = new Location(Bukkit.getWorld(configurationSection.getString("world")),
                configurationSection.getInt("x"),
                configurationSection.getInt("y"),
                configurationSection.getInt("z"));

        signalGui = new SignalGui(coordinates, icon);

        toLoad.add(start);
        while (!toLoad.isEmpty()) {
            find();
        }

        start.getWorld().getEntities().forEach(entity -> {
            if (!(entity instanceof Minecart)) return;

            if (locationMap.containsKey(entity.getLocation().getBlock().getLocation())) {
                new ArrayList<>(locationMap.keySet()).stream()
                        .filter(position -> position.equals(entity.getLocation().getBlock().getLocation()))
                        .forEach(position -> locationMap.get(position).getPositionData().ifPresent(data -> data.setOccupied((Minecart) entity)));
            }
        });
        getServer().getPluginManager().registerEvents(new TrackVacancyDetectionSystem(), this);


        signalGui.setVisible(true);
    }


    @Override
    public void onDisable() {
        signalGui.setVisible(false);
    }

    public void find() {
        Location location = toLoad.remove(0);

        if (!location.getChunk().isLoaded()) location.getChunk().load(false);

        if (locationMap.containsKey(location)) return;
        Position position = new Position(location.getBlockX() - this.start.getBlockX(), location.getBlockZ() - this.start.getBlockZ());
        Material type = location.getBlock().getType();
        if (type == Material.RAIL
                || type == Material.DETECTOR_RAIL
                || type == Material.ACTIVATOR_RAIL
                || type == Material.POWERED_RAIL) {

            byte data = getData(location.getBlock());
            locationMap.put(location, position);
            locationPositionMap.put(position, location);
            List<Position> affected = new ArrayList<>();
            for (int i = -1; i <= 1; i++) {
                find(0, i, 1, location, affected);
                find(1, i, 0, location, affected);
                find(0, i, -1, location, affected);
                find(-1, i, 0, location, affected);
            }

            PositionData positionData;
            if (type == Material.DETECTOR_RAIL) {
                positionData = new SignalPositionData(position, data, type, location);
            } else if (affected.size() >= 3 && type == Material.RAIL) {
                List<Byte> possibleSettings = new ArrayList<>();
                affected.forEach(pos1 -> affected.forEach(pos2 ->{
                    if (pos1.equals(pos2)) return;
                    byte b = PathfinderAlgorithm.fromToCheck(pos1, position, pos2);
                    if (!possibleSettings.contains(b))
                        possibleSettings.add(b);
                }));

                positionData = new SwitchPositionData(position, data, type, location, possibleSettings);
            } else {
                positionData = new RailPositionData(position, data, type, location);
            }
            coordinates.setPositionData(position, positionData);
        }
    }

    private void find(int i, int i1, int i2, Location location, List<Position> positions) {
        Location add = location.clone().add(i, i1, i2);
        switch (add.getBlock().getType()) {
            case RAIL:
            case ACTIVATOR_RAIL:
            case DETECTOR_RAIL:
            case POWERED_RAIL:
                if (toLoad.contains(add)) {
                    positions.add(new Position(add.getBlockX() - this.start.getBlockX(), add.getBlockZ() - this.start.getBlockZ()));
                    return;
                }

                toLoad.add(add);
                positions.add(new Position(add.getBlockX() - this.start.getBlockX(), add.getBlockZ() - this.start.getBlockZ()));

        }
    }

}
