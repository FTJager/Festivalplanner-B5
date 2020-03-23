# Portfolio
## Kim Veldhoen 
## Studentnummer: 2154630
## Jaar 1, klas B

# Inhoud:
1. [`Wekelijkse reflectie`](#wekelijkse-reflectie)
2. [`Vakinhoudelijke reflectie`](#vakinhoudelijke-reflectie)
3. [`JSON applicaties`](#json-applicaties)


# Wekelijkse reflectie 
## Week 2:
In deze week zijn we gestart met de opzet van de agenda GUI. Voor het maken van het klassendiagram hebben we, in de eerste week, een opzet gemaakt van hou de GUI eruit zou moeten zien en welke functies deze nodig had.
![Ontwerp agenda GUI](handleiding.png)

Hierbij hebben we verschillende klassen opgezet waarin de agenda GUI zelf, de datastore, de shows, artiesten en podiums aangemaakt kunnen worden.
![Klassendiagram FestivalPlanner](FestivalAgenda.jpg)

Ieder persoon van de projectgroep kreeg een klasse aangewezen om deze week te maken. Remco en ik zijn beide begonnen aan de agenda GUI klasse zelf.
In deze week heb ik een aantal veranderingen gemaakt aan de GUI klas. Dit in overleg met de rest van de project groep, omdat bepaalde aspecten die in week 1 in de GUI klas zijn geïmplementeerd niet aan onze eisen voldoen. 

## Week 3:
In deze week zijn we verder gaan werken aan de functionaliteiten van de agenda GUI.
Ik heb in de GUI klas een aantal aanpassingen gedaan aan hoe de knoppen van de verschillende stages (new, edit, delete) shows aanmaken, bewerken, en opslaan.
Dit in combinatie met gebruik van ObjectIO wat Florian heeft geïmplementeerd. Hiermee kunnen we nieuwe shows aanmaken en opslaan, shows ophalen en deze bewerken, shows individueel verwijderen, of alle shows tegelijk verwijderen.
![GUI](agendaGUI.png)

## Week 4:
In deze week zijn we begonnen met het inladen van JSON-files in InteliJ. Hiermee kunnen we een aparte file maken wat als "map" achtergrond gebruikt kan worden.
Hierop plaatsen we de vier verschillende podiums, toiletten, eettentjes en rustplaatsen waar de karakters op kunnen rondlopen.
Verder hebben we tijdens het senior gesprek met Johan besproken dat we de individuele portfolio's in de git-repository van het project toevoegen. Hierdoor is de toegang tot onze portfolio's voor onze eigen senior, Joep, makkelijker.

## Week 5:
In deze week zijn we begonnen met de opzet van de festival simulatormodule. Hiervoor hebben we een globaal overzicht gemaakt van welke klassen er nodig zullen zijn.
Op basis van de globale opzet kunnen we een klassendiagram maken van welke klassen er nodig zijn voor de simulator zelf.
![Globale opzet](simulatiemodule.png)
Wanneer de klasse voor NPC en gasten af is, kunnen de karakters op de map worden ingeladen. In eerste instantie testen we of deze karakters over de map heen kunnen lopen en of de collisiondetection goed werkt.
In week 6 kunnen we met A-STAR pathfinding ervoor zorgen dat deze karakters tussen de verschillende podiums, toiletten en eettentjes kunnen lopen.
Zo kan het gedrag van gasten op een festival gesimuleerd worden en kunnen de knelpunten van het festival terrein in kaart worden gebracht.

# Vakinhoudelijke reflectie 
## VR Week 2:
In week 1 hebben we een opzet gemaakt voor de GUI van de agenda. Hier hadden we een tabel in verwerkt, maar dit bleek uiteindelijk niet te voldoen aan onze eisen omdat we hiermee geen gebruik konden maken van java2D.
Hierdoor heb ik in week 2 de hele GUI klas omgebouwd waardoor deze wel gebruik kon maken van java2D.
In de GUI klas hebben we ook gebruik gemaakt van de popup klas van Java zelf. Deze popups heb ik, na het ombouwen van de GUI klas, achterwegen gelaten omdat deze niet het effect gaven wat we eigenlijk hadden verwacht.
Ik heb in plaats van de popups nieuwe klassen aangemaakt voor elke actie die we willen gebruiken: het toevoegen van een nieuwe show, het bewerken van een bestaande show, en het verwijderen van een bestaande show.
Wanneer er nu op een bepaalde knop wordt gedrukt (new, edit, delete), zal er een nieuwe instantie van de desbetreffende klas worden aangemaakt.
Hierin kan doormiddel van tekstvelden informatie worden ingevoerd die een (nieuwe) show zullen aanmeken.
Deze show wordt voor nu opgeslagen in een ArrayList met shows die vanuit de datastore klas kan worden opgeslagen en opgevraagd.

## VR Week 3:
Deze week heb ik samen met Florian er voor gezorgd dat de informatie van de tekstvelden worden opgeslagen door gebruik te maken van ObjectIO.
Wanneer nu, tijdens het aanmaken van een nieuwe show, op de 'done' knop gedrukt is zal er eerst worden gekeken of er een artiestennaam is ingevuld in het artiesten tekstvak.
Als dit niet het geval is, dan kan er niet op 'done' gedrukt worden. Dit om er voor te zorgen dat er geen shows worden opgeslagen zonder artiest.
Wanneer er wel een artiesten naam is ingevuld, maar de rest van de tekstvakken zijn leeg dan zullen deze een standaard waarde van '0' meekrijgen. Deze zijn bij het bewerken van de show, editStage, aan te passen.

    doneButton.setOnAction(e -> {
        if(!artistField.getText().isEmpty()){
            newShow.setShow(artistField.getText());
            } else {
                artistField.setText("please type in a name");
            }
            if(!beginTimeField.getText().isEmpty()
              && Integer.parseInt(beginTimeField.getText()) != Integer.parseInt(endTimeField.getText())
              && Integer.parseInt(beginTimeField.getText()) < Integer.parseInt(endTimeField.getText())){
                newShow.setStartTime(Integer.parseInt(beginTimeField.getText()));
            } else {
                newShow.setStartTime(0);
            }
            if(!endTimeField.getText().isEmpty()){
                newShow.setEndTime(Integer.parseInt(endTimeField.getText()));
            } else {
                newShow.setEndTime(0);
            }
            if(!popularityField.getText().isEmpty()){
                newShow.setPopularity(Integer.parseInt(popularityField.getText()));
            } else {
                newShow.setPopularity(0);
            }
            if(!stageField.getText().isEmpty()){
                newShow.setStage(Integer.parseInt(stageField.getText()));
            } else {
                newShow.setStage(0);
            }
            
            this.showList.add(newShow);
            newStage.close();
                 
            if (!this.showList.isEmpty()){
                serializer.Write(this.showList);
            }
    });

Bij het verwijderen van een show zal eerst worden gevraagd of de gebruiker zeker weet of hij/zij deze show wilt verwijderen. Nadat op 'yes' is gedrukt, zal de desbetreffende show uit de lijst worden gehaald.
     
    yesButton.setOnAction(e -> {
        if (!deserializer.Read().isEmpty()){
            showList = deserializer.Read();
        }
        showList.remove(index);
        serializer.Write(showList);
        delStage.close();
    }); 
    
## VR Week 4:
Deze week heb ik mij verdiept in het inladen en uitlezen van JSON-files in Java en IntelliJ.

## VR Week 5:



# JSON applicaties
JSON staat voor JavaScript Object Notation en is een gemakkelijke en lichtgewicht data-uitwisselings format.
JSON maakt alleen gebruik van tekst, waardoor dit gemakkelijk van en naar een server verzonden kan worden.
## Websites
### HTML
Json kan heel gemakkelijk worden omgezet in JavaScript wat omgezet kan worden in HTML voor websites

### PHP
PHP objecten kunnen geconverteerd worden in JSON waarmee bijvoorbeeld een website gemaakt kan worden

## Web servers

## Smartwatch apps

Al deze applicaties maken gebruik van JavaScript