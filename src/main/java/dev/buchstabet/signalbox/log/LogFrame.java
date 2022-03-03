package dev.buchstabet.signalbox.log;

import javax.swing.*;

public class LogFrame extends JTextArea {

  private String log;

  public LogFrame() {
    setLineWrap(true);
    log = "Starting signalbox";
    setText(log);
  }

  public void log(String log) {
    this.log = log + "\n" + this.log;
    setText(this.log);
  }
}
