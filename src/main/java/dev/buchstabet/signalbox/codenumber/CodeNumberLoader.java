package dev.buchstabet.signalbox.codenumber;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.buchstabet.signalbox.coordinates.PositionData;
import dev.buchstabet.signalbox.coordinates.SignalPositionData;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Getter
public class CodeNumberLoader {

  @Nullable private CodeNumbers codeNumbers;
  private final File file;
  private final Gson gson;

  public CodeNumberLoader() throws IOException {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(PositionData.class, new GsonPositionData<>());
    builder.registerTypeAdapter(SignalPositionData.class, new GsonPositionData<SignalPositionData>());

    builder.setPrettyPrinting();
    gson = builder.create();

    File directory = new File("plugins//signalbox");
    if (!directory.exists()) directory.mkdirs();
    this.file = new File(directory, "codenumbers.json");
    if (file.createNewFile()) {
      CodeNumbers codeNumbers = new CodeNumbers(new ArrayList<>());
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(gson.toJson(codeNumbers));
      fileWriter.flush();
      fileWriter.close();
    }
  }

  public CompletableFuture<Void> load() {
    return CompletableFuture.runAsync(() -> {

      try (FileReader fileReader = new FileReader(file)) {
        codeNumbers = gson.fromJson(fileReader, CodeNumbers.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  public CompletableFuture<Void> add(CodeNumber codeNumber) {
    return CompletableFuture.runAsync(() -> {
      assert codeNumbers != null;
      codeNumbers.getCodeNumbers().add(codeNumber);
      save();
    });
  }

  public void save() {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(gson.toJson(codeNumbers));
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
