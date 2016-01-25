# myFinance
Online Finanzverwaltung

# Task 10:
  
Abgabedatum - 25.01.2016 bis 20:00 Uhr

## Doing

Bitte beachten, dass der Einsatz von Libraries/Frameworks erlaubt (und sogar erwÃ¼nscht) ist.   

Vorweg: Im weiteren finden sich durachaus schwammige Anforderungen, die so nicht unbedingt
gestellt werden sollten. Da dies aber in der Praxis (zu) oft genau so gemacht wird, finden sich
solch unklare bzw. unvollstÃ¤ndige Angaben auch hier.

### Finanzverwaltung

Ziel dieser Anwendung ist es, eine web-basierte Applikation zu entwickeln, die zur Verwaltung
von Finanzen dient. Es sollen Transaktionen erfasst werden kÃ¶nnen, welche einem Konto (bzw.
Sparbuch, Kreditkarte) zugeordnet werden. Transaktionen kÃ¶nnen dabei den Ist-Stand eines Kontos
sowohl positiv (Einnahmen) als auch negativ (Ausgaben) beeinflussen.

#### Anforderungen

Folgenden Anforderungen sind mindestens umzusetzen:

- Die Applikation muss Ã¼ber ein Anmeldesystem verfÃ¼gen.
- Alle Transaktionen und alle Konten mÃ¼ssen persisiert werden. VerlÃ¤sst ein Benutzer die
Applikation und meldet sich spÃ¤ter erneut an, so mÃ¼ssen alle KontostÃ¤nde und alle Transaktionen
mit
- Export- bzw. Import-FunktionalitÃ¤t muss implementiert werden
- Die Applikation ist als Single-Page-Webapplikation zu implementieren
- Es ist eine strikte Trennung zwischen HTML, JS und CSS einzuhalten
- Als JS-Framework kann wahlweise Webpack und/oder ReactJS verwendet werden
- Die Web-Applikation muss alle gesetzlichen Anforderungen von Accessibility gerecht werden
- Es ist auf eine geeignete Usability zu achten
- Die umgesetzten FunktionalitÃ¤ten sind ausreichend zu testen

#### Rahmenbedingungen

Die Webseite darf zum initialen Laden nicht mehr als 2 Sekunden benÃ¶tigen.
Werden Informationen spÃ¤ter nachgeladen, darf die Zeit zum Nachladen 500msec nicht Ã¼berschreiten.
Sollte dies nicht mÃ¶glich sein, so muss der Benutzer darÃ¼ber in geeigneter Art und Weise (Spinner,
Ladebalken) informiert werden.   
Bei Unklarheiten bzgl. Anforderungen bzw. Rahmenbedingungen soll die WebInfoSys-Mailingliste zur
KlÃ¤rung verwendet werden.

# Reading:

[Bootstrap](http://getbootstrap.com/)   
[Karma](https://karma-runner.github.io/0.13/index.html)   
[Mocha](https://mochajs.org/)   
[NodeJS](https://nodejs.org/en/)   
[PostgreSQL](http://www.postgresql.org/)   
[ReactJS](https://facebook.github.io/react/)   
[SpinJS](http://fgnass.github.io/spin.js/)   
[Webpack](https://webpack.github.io/docs/)   