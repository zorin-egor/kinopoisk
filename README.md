# Kinopoisk

<b>Kinopoisk</b> - sample multi modals app developed with android architecture components, conventions plugins.

``` text
├── app.............. Entry point to the mobile application
│   └── NavHost.... App navigation coordination
├── core......... Independent project/component logic
│   ├── common...... Utilities, extension functions, helpers
│   ├── network..... Interaction with the network
│   ├── database.... Database
│   ├── data........ Repositories
│   ├── domain...... Business logic
│   ├── model....... Business logic models
│   ├── ui.......... Basic UI components, themes, color schemes, comprehensive UI components for a specific presentation
├── features....... All screens are divided into module-features
│   ├── films....... Feature list of films
│   └── film_details....... Feature details about the film
└──gradle-plugins.......... Convention gradle plugin for forwarding dependencies between modules
```