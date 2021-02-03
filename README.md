# Pokemon Changelle
This android has been development as a challenge to demonstrate the use of the a concrete technology stack. The application has the follow features:
1. A main screen where appear a list of Pokemons. Each item show an image and name of a Pokemon.
2. A detail screen where the use can see a set of descriptions of the pokemon, like abilities, moves, weight, etc...

## Architecture
The architecture is based in three layers.

#### 1. Domain layer
This layer has the logic of the behaviour that we want to do in the app. In this case, to load and store efficiently the Pokemon data into device. For this app, a repository do this logic.

#### 2. Data source layer
Here is the necessary code to get the data from the API and store in the device to cached in it. This layer has data sources for persistence and communication with the Pokemon API.
- A connection with the remote API.
- A database for store the data getted from the API and used like a *cache* to improve the performance.


#### 3. Presenter layer
Where the user see the data.
- **MVVM** design pattern has been used.
- It has itself data model that it is translate from domain data model.
- Each feature is a package with the structure:
  - *navigator*: It has the class/es necessaries to navigation from this feature.
  - *view*: It has the class/es necessaries to show the data(activities, adapters, viewholders...).
  - *viewmodel*: it has the viewmodel classes.

## Technology stack
- Language: **Kotlin**
- Dependency injection: **Koin**
- Asynchronous computation: **Flow**
- Android Jetpack components:
  - **Androidx**
  - **Room**
  - **ViewModel**
  - **ViewBinding**
- Other relevant libraries:
  - Functional programming: **Arrow**
  - Wrapper Library for Kotlin **PokeKotlin** by sargunster
  - To load images: **Coin**

## Extras
- JUnit tests
- Instrumentation tests
- Adaption UI to mobile orientation changes
- Favorite status. You can see the network responses [here](https://webhook.site/a1dfe050-4748-437d-8040-fe60f4917923)
