package dev.buchstabet.signalbox.codenumber;

import dev.buchstabet.signalbox.coordinates.SignalPositionData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CodeNumber {

  private final int identifier, stationStayTime; // seconds
  private final List<RailRoute> railRoutes;
  private final List<SignalPositionData> stations;

}
