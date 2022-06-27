# nasa
Multi-module Clean Architecture

## Modules:

* Api: library module containing rest data models & client
* App: Contains core logic. Can be further split up by feature and extracting the utils out
* Mvi: Base MVI classes I created for Deepfinity to facilitate MVI based viewmodels. Uses Orbit-Mvi but is extended to allow for dispath style.
* Storage: Sql based storage using Room. Two tables to contain cached & paged data for offline support.
* Utils: Utils for view binding and base view classes.

## Testing:

* Unit: Using a combination of Roboletric and basic Junit4 to test integrations with Room and flows
  More testing can be done for full coverage including DB testing
* UI: Should be done by using a mock server and creating a custom test runner to inject debug specific DI modules if required to produce the list items.