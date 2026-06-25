TicTacToe application built using Java 25 LTS and Java FX. Can play as single player (VS Computer) or dual players. Game has dark mode.
Built using GitHub Copilot and IDE Visual Studio Code.

## What's implemented:
- **GUI built using JavaFX**: pom.xaml file has dependencies for the JavaFX Maven plugin
- **Dynamic Theme Switching**: Click the checkbox to toggle between light and dark modes
- **Fixed disabling focus traversal on the cells**: The blue highlight is the default JavaFX focus indicator on buttons. After a button is clicked, it retains focus which shows a blue highlight. Let me fix this by disabling focus traversal on the cells

## Prompts:
* create tic tac toe game using Java and JavaFX. Make game fun to play. Can be single player with computer or two player mode.
* as I play the game, it blue highlights a cell next to my last chosen cell. Let remove the blue highlight 
* lets add dark mode toggle

## Coding LLMs:
Claude Haiku 4.5
Raptor mini

## Screenshot(s)
<img width="532" height="664" alt="Gameplay" src="https://github.com/user-attachments/assets/ed3a671f-cbf0-40ee-8deb-c6d41e82467f" />
<img width="532" height="664" alt="Gameplay (dark mode)" src="https://github.com/user-attachments/assets/d837e656-306a-44d7-8764-5cffaa15e4ca" />

## Build steps:
Simply use maven commands to build / compile

## Run game:
Run the application using the JavaFX Maven plugin - 
```unix
mvn javafx:run
