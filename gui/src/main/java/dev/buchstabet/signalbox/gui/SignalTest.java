package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;

import java.util.HashMap;
import java.util.Map;

public class SignalTest {

  public static void main(String[] args) {
    Map<Position, PositionData> positionDataMap = new HashMap<>();

    Coordinates coordinates = new Coordinates(positionDataMap);
    SignalGui signalGui = new SignalGui(coordinates);
    signalGui.display();


  }

}
