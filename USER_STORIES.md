## REQUIRMENTS 
### The Game
1. The game contains a map
2. The game contains a hero
3. The game contains enemies
4. The game contains items
5. The hero is spawn randomly (may change this)
6. The hero can move in the areas defined in the map
7. The hero can fight enemies
8. The hero has basic stats - atk/defense/speed
9. The enemies are spawn randomly when the map is created
10. The enemies chase the hero when he's some certain distance from them
11. The enemies can fight the hero
12. The enemies have basic stats - atk/defense/speed
17. The game ends when either the hero or all the enemies die

### The Spring app is responsible for the game logic
1. The Spring app is a restful app
2. The Spring app sends the required data to the React app in the form of HTTP responses
3. The game state is transfered using a HTTP GET request
4. The position and stats for all the objects (hero/enemies) are updated every few ms
5. The client can interract with the character using POST requests

### The React app is responsible for the rendering
1. The React app is a http client
2. The game loop is implemented using HTTP GET requests constantly
3. The React app receives the state of the game and draws accordingly
4. The React app is responsible for sending the user input
5. Inputs generate an HTTP POST request
