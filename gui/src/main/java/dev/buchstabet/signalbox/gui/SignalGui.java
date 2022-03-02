package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.minecraftadapter.MinecraftAdapter;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Getter
public class SignalGui extends JFrame {

    private final Coordinates coordinates;
    private final MinecraftAdapter minecraftAdapter;
    @Getter
    private static SignalGui instance;
    private final BufferedImage icon, logo;

    private final JScrollBar barVertical = new JScrollBar(JScrollBar.VERTICAL, 250, 40, 0, 500);
    private final JScrollBar barHorizontal = new JScrollBar(JScrollBar.HORIZONTAL, 250, 20, 0, 500);

    private JPanel drawPanel, buttonPanel;

    public SignalGui(Coordinates coordinates, MinecraftAdapter minecraftAdapter, BufferedImage icon) throws IOException {
        this.coordinates = coordinates;
        this.minecraftAdapter = minecraftAdapter;

        instance = this;

        this.icon = icon;


        URL url = SignalGui.class.getClassLoader().getResource("logo.png");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setUseCaches(false);
        logo = ImageIO.read(urlConnection.getInputStream());
    }

    public int calculateSize(int value) {
        return value * 100 - 50000 / 2;
    }

    public void display() {
        setTitle("Signalbox");
        setSize(1700, 900);
        Position.xMove = calculateSize(barHorizontal.getValue());
        Position.yMove = calculateSize(barVertical.getValue());

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(icon);

        buttonPanel = new JPanel();
        drawPanel = new JPanel();

        add(buttonPanel);
        add(drawPanel);

        addMouseWheelListener(e -> {
            Coordinates.COORDINATE_SIZE += e.getWheelRotation();
            repaint();
        });

        {

            getContentPane().add(barVertical, BorderLayout.EAST);
            barVertical.addAdjustmentListener(e -> {
                Position.yMove = calculateSize(e.getValue());
                repaint();
            });
        }

        {
            getContentPane().add(barHorizontal, BorderLayout.SOUTH);
            barHorizontal.addAdjustmentListener(e -> {
                Position.xMove = calculateSize(e.getValue());
                repaint();
            });
        }

        getButtonPanel().setBounds(0, 0, (int) getSize().getWidth(), (int) getSize().getHeight());
        getButtonPanel().setLayout(null);
        getButtonPanel().setVisible(true);

        new SignalMouseListener(this);
        // addMouseWheelListener(e -> Coordinates.COORDINATE_SIZE = Math.max(Coordinates.COORDINATE_SIZE + e.getWheelRotation(), 5));
    }

    @SneakyThrows
    @Override
    public void paint(Graphics g) {
        // g.clearRect(0, 0, (int) getSize().getWidth(), (int) getSize().getHeight());
        super.paint(g);

        coordinates.draw(g, this);
        g.drawImage(logo, 50, 40, null);
    }
}
