package dev.buchstabet.signalbox.signal;

public enum SignalPosition {

  DRIVE,
  STOP,
  BEACON; // Kennlicht

  public boolean canDrive() {
    return this != STOP;
  }
}
