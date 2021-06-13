# Muzica

Muzica is a a music listing showcase app that read data from a mcoked searchable API source for the music tracks

### Features
1. Kotlin
2. MVVM architecture
3. Reactive pattern
4. Android architecture components and Jetpack
5. Single activity
6. Dependency injection
7. Testing (Upcoming)


### Tech Stacks
1. Retrofit + OkHttp - RESTful API and networking client.
2. Hilt - Dependency injection.
3. Android Architecture Components - A collections of libraries that help you design rebust, testable and maintainable apps.
4. ViewModel - UI related data holder, lifecycle aware.
5. LiveData - Observable data holder that notify views when underlying data changes.
7. Kotlin Coroutines - Asynchronous programming with observable streams.
8. Glide - Image loading.

### Package Structures
```
amr.barakat.muzica              # Root Package
├── base                        # Base arch. classes (Activity/Fragment/ViewModel)
|
├── data                        # For data modeling layer
│   ├── modles                  # Data classes 
│   ├── remote                  # Remote data source
│       └── repo                # Repositories for single source of data
│       └── services            # Retrofi service
|       └── Source              # Remote data source
│ 
├── di                          # Dependency injection modules
│
├── ui                          # Fragment / View layer
│   ├── listing                 # Grid view for sound tracks 
│   ├── MainActivity            # Main application activity
|
├── extentions                  # Kotlin extentions helper methods
├── App                         # Application

```


## Screenshot
![image](https://user-images.githubusercontent.com/7738296/121814246-44f14c80-cc70-11eb-8a76-2fcd965aea6f.png)
