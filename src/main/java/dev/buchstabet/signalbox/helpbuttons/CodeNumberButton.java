package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.codenumber.CodeNumber;
import dev.buchstabet.signalbox.gui.SignalGui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class CodeNumberButton extends HelpButton {

  public CodeNumberButton(int x, int y) {
    super(x, y);
    setBackground(Color.GREEN);
    setText("Kennziffer");

    addActionListener(this);
    setBounds(x, y, 130, 30);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    SignalGui.getInstance().getLogFrame().log("CodeNumber was pressed");

    JFrame jFrame = new CodeNumberGui("Kennziffer erstellen") {
      @Override
      public void paint_(Graphics g) {
        g.drawString("Kennziffer", 20, 60);
        g.drawString("Wartezeit an Haltestellen (Sekunden)", 20, 160);
      }
    };
    JPanel jPanel = new JPanel();


    jFrame.setSize(600, 400);
    jPanel.setSize(600, 400);
    jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    // jPanel.setIconImage(icon);
    jFrame.setResizable(false);
    jFrame.setAlwaysOnTop(true);

    jPanel.setLayout(null);

    JTextField identifier = new JTextField(Integer.toString(new Random().nextInt(99)));
    identifier.setBounds(20, 50, 170, 50);
    identifier.setBackground(Color.lightGray);
    jPanel.add(identifier);

    JTextField stationStayTime = new JTextField(Integer.toString(20));
    stationStayTime.setBounds(20, 150, 170, 50);
    stationStayTime.setBackground(Color.lightGray);
    jPanel.add(stationStayTime);

    JButton ok = new JButton("Kennziffer speichern");
    ok.setBounds(20, 300, 170, 30);

    ok.addActionListener(e1 -> {

      jFrame.setVisible(false);
      int stationStayTimeInt;
      int identifierInt;

      try {
        stationStayTimeInt = Integer.parseInt(stationStayTime.getText());
        identifierInt = Integer.parseInt(identifier.getText());
      } catch (NumberFormatException exception) {
        exception.printStackTrace();
        return;
      } finally {
        jFrame.setVisible(false);
      }

      CodeNumber codeNumber = new CodeNumber(identifierInt, stationStayTimeInt, new ArrayList<>(), new ArrayList<>());
      SignalGui.getInstance().getCodeNumberLoader().add(codeNumber);
    });
    ok.setBackground(Color.green);

    jPanel.add(ok);

    jFrame.add(jPanel);
    jFrame.setVisible(true);
  }

  private abstract class CodeNumberGui extends JFrame {

    public CodeNumberGui() throws HeadlessException {
    }

    public CodeNumberGui(GraphicsConfiguration gc) {
      super(gc);
    }

    public CodeNumberGui(String title) throws HeadlessException {
      super(title);
    }

    public CodeNumberGui(String title, GraphicsConfiguration gc) {
      super(title, gc);
    }

    @Override
    public void paint(Graphics g) {
      super.paint(g);
      paint_(g);
    }

    public abstract void paint_(Graphics g);

  }

}
