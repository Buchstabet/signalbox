package dev.buchstabet.signalbox.routeloader;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.gui.SignalGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Rails;

import javax.swing.*;
import java.awt.*;

public class SizeGui extends JFrame {

    public SizeGui() {
        super("Coordinaten Größe");
        setSize(300, 150);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Größe");
        panel.add(label);
        JTextField tfName = new JTextField("20", 15);
        tfName.setForeground(Color.BLUE);
        tfName.setBackground(Color.YELLOW);
        panel.add(tfName);

        setVisible(true);

        JButton buttonOK = new JButton("OK");

        buttonOK.addActionListener(e -> {
            try {
                Coordinates.COORDINATE_SIZE = Integer.parseInt(tfName.getText());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            RailRouteLoader.getPlugin(RailRouteLoader.class).launch();
            setVisible(false);
        });

        panel.add(buttonOK);

        add(panel);
        setVisible(true);
    }
}
