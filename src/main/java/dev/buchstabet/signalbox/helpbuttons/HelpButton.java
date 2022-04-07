package dev.buchstabet.signalbox.helpbuttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class HelpButton extends JButton implements ActionListener {

  private final int x, y;

  public HelpButton(int x, int y, String text) {
    super(text);
    addActionListener(this);

    this.x = x;
    this.y = y;

    setBounds(x, y, 130, 30);
  }

  public HelpButton(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public abstract void actionPerformed(ActionEvent e);

}
