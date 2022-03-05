# signalbox
Ein Stellwerk für Minecarts.

## Einführung

Stellwerk in Minecraft? Mit diesem Plugin wird es realität. 
Ich habe mit Java-Swing ein GUI erstellt, mit dem man seine Loren mit Fahrstraßen steuern kann. 
Das Plugin erkennt alle an eine voreingestellte Schiene angeschlossenen Schienen und stellt diese grafisch dar.
Ganz einfach Start und Ziel auswählen und der Algorithmus sucht den Weg. 
Dabei umfährt er andere Routen und Züge. Es gibt ein virtuelles Signalsystem, an dem die Loren anhalten und weiterfahren, wenn eine Fahrstraße eingestellt wird.

## Wie stellt man eine Fahrstraße ein?
Wird eine Signaltaste gedrückt, wird ein Start ausgewählt. Wird noch eine Signaltaste gedrückt, wird die Fahrstraße erstellt.
Ist ein Minecart auf dem Start, wird es nun die ausgewählte Strecke fahren.

Die Fahrstraße wird dann grün hervorgehoben.

![ezgif-1-a00439d9ac](https://user-images.githubusercontent.com/71724439/156902933-afdde00e-8482-4694-9263-4bbcf892437d.gif)

## Gleisfreimeldeanlage
Die Gleisfreimeldeanlage zeigt an, ob ein Gleis besetzt ist (ob ein Minecart auf dem Gleis steht) oder nicht.

Besetzte Signale sind blau:

![image](https://user-images.githubusercontent.com/71724439/156902188-22761503-3c26-446a-9a5a-fa617f7d0db7.png)

Besetzte Gleise sind rot:

![image](https://user-images.githubusercontent.com/71724439/156902342-e9bc7102-95b2-4466-9e90-76da7eb5cfaf.png)

Freie Gleise sind Gelb:

![image](https://user-images.githubusercontent.com/71724439/156902347-2c68c989-a0f4-48c1-9f9a-47c9faeedabe.png)

Ist ein Gleis grün und wird befahren, wird sie danach wieder gelb:

![image](https://user-images.githubusercontent.com/71724439/156902486-19c4bd65-6954-40cb-a44e-e26f25bb030a.png)

## Wichtige Info
Das Plugin ist nicht remote fähig, daher kann das nur auf deinem Computer ausgeführt werden.

## Log
Alle relevanten Tastenbedienungen, werden im Log gespeichert:

![image](https://user-images.githubusercontent.com/71724439/156902538-eb8724ea-3d60-4ac4-bb4d-e831994ec59e.png)

## Die Tasten und deren Bedeutung

Übersicht aller Tasten

![image](https://user-images.githubusercontent.com/71724439/156901494-e05b913d-63ae-4241-9c49-e0ad7e6c1106.png)

### Signaltaste:

![image](https://user-images.githubusercontent.com/71724439/156901743-be36625a-b8da-4048-81d3-24fb16b4170d.png)

Die rote Taste stellt ein Signal, das gleichzeitig auch der Start bzw. das Ziel einer Fahrstraße sein kann.
Die Taste hat immer die Farbe, die das Signal zeigt.

- Rot: Halt
- Grün: Fahrt
- Blau: Halt und besetzt
- Weiß: Kennlicht (Das Signal ist abgeschaltet und wird weder von Fahrstraßen als auch von Minecarts ignoriert)

### FHT (Fahrstraßenhilfstaste)

![image](https://user-images.githubusercontent.com/71724439/156902411-916aed0a-45b9-4d9f-b7f8-590fdfec08f7.png)

Wird diese Taste und danach eine Signaltaste gedrückt, wird die Fahrstraße aufgelöst, die mit dem Signal verbunden ist.

### FfRT (Fahrstraßenfreigabetaste)

![image](https://user-images.githubusercontent.com/71724439/156902418-a03d1246-7fd4-4095-b5b4-9ed9c18adac0.png)

Diese Taste wurde etwas zweck entfremdet und dient als Freigabe zur Fahrt in ein besetzes Gleis.
Wird die Taste gedrückt, können also Fahrstraßen in besetzte Gleise eingestellt werden.

### SGT (Signalgruppentaste)

![image](https://user-images.githubusercontent.com/71724439/156902012-71b1b2a0-75d4-4c74-b99a-fdaf770ce8b5.png)

Wird diese Taste gemeinsam mit einer Signaltaste gedrückt, zeigt das Signal Kennlicht.

### HaGT (Signalhaltgruppentaste)

![image](https://user-images.githubusercontent.com/71724439/156902025-53704ab5-494f-4d95-b2e1-dbee3a0b9ef0.png)

Wird diese Taste gemeinsam mit einer Signaltaste gedrückt, zeigt das Signal Halt.

### WGT (Weichengruppentaste)

![image](https://user-images.githubusercontent.com/71724439/156902045-a597af8e-430e-4cca-a830-7083cb5ee096.png)

Wird diese Taste gedrückt, werden die Weichentaste angezeigt. Drückt man auf eine Weichentaste, wird die Weiche umgestellt.

### Start

![image](https://user-images.githubusercontent.com/71724439/156902076-4b479ed3-4f81-49a0-9164-d556a8a49f99.png)

Ist ein Start ausgewählt und die Starttaste gedrückt, wird der Start gelöscht.

### Reload

![image](https://user-images.githubusercontent.com/71724439/156902091-61a4a00a-ea5f-496b-a1b2-fe8047491103.png)

Die Reload taste kann den Server reloaden.
