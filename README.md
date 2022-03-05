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
![image](https://user-images.githubusercontent.com/71724439/156902140-d4c550d1-19c9-47e2-9f1c-4e65b67b683f.png)

## Gleisfreimeldeanlage
Die Gleisfreimeldeanlage zeigt an, ob ein Gleis besetzt ist (ob ein Minecart auf dem Gleis steht) oder nicht.

Besetzte Signale sind blau:

![image](https://user-images.githubusercontent.com/71724439/156902188-22761503-3c26-446a-9a5a-fa617f7d0db7.png)

Besetzte Gleise sind rot:

![image](https://user-images.githubusercontent.com/71724439/156902214-6bfb40fa-6b26-40e4-bce1-d3ae12e8061d.png)

Freie Gleise sind Gelb:

![image](https://user-images.githubusercontent.com/71724439/156902247-5bbaf299-0b09-4c02-acf8-580a93933c89.png)


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

![image](https://user-images.githubusercontent.com/71724439/156901906-c4ac997b-8857-47cc-91b3-d6d5cb3da4d5.png)

Wird diese Taste und danach eine Signaltaste gedrückt, wird die Fahrstraße aufgelöst, die mit dem Signal verbunden ist.

### FfRT (Fahrstraßenfreigabetaste)

![image](https://user-images.githubusercontent.com/71724439/156901971-7b3bbef0-a3be-4989-8fc8-7451cd911490.png)

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
