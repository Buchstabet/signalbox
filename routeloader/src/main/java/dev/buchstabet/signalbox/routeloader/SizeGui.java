package dev.buchstabet.signalbox.routeloader;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SizeGui extends JFrame {

    public SizeGui(BufferedImage icon) {
        super("Coordinaten Größe");
        setSize(500, 130);
        setResizable(false);


        JPanel panel = new JPanel();
        JLabel label = new JLabel("Größe");
        panel.add(label);
        RailRouteLoader railRouteLoader = RailRouteLoader.getPlugin(RailRouteLoader.class);
        railRouteLoader.saveDefaultConfig();
        FileConfiguration config = railRouteLoader.getConfig();
        JTextField tfName = new JTextField(Integer.toString(config.getInt("size")), 15);
        tfName.setForeground(Color.BLUE);
        tfName.setBackground(Color.YELLOW);
        panel.add(tfName);

        setIconImage(icon);

        JLabel label2 = new JLabel("Location");
        panel.add(label2);

        JTextField tfName2 = new JTextField(config.getString("location"), 15);
        tfName.setForeground(Color.BLUE);
        tfName.setBackground(Color.YELLOW);
        panel.add(tfName2);

        setVisible(true);

        JButton buttonOK = new JButton("OK");

        buttonOK.addActionListener(e -> {
            // config.set("location", tfName2.getText());
            // config.set("size", tfName.getText());
            // railRouteLoader.saveConfig();

            buttonClick(tfName2.getText(), tfName.getText());
        });

        panel.add(buttonOK);

        add(panel);
        setVisible(true);
    }

    private void buttonClick(String content, String content2) {
        try {
            Coordinates.COORDINATE_SIZE = Integer.parseInt(content2);

            String[] s = content.split(" ");
            Location location = new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));

            RailRouteLoader.getPlugin(RailRouteLoader.class).launch(location);
            setVisible(false);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
