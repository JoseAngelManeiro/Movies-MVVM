# Movies-MVVM

Android project that uses the MVVM pattern and Clean architecture to show a list of movies and their detail.

<img src="./art/screenshots.png" width="500" hspace="20">


## Architecture

This app uses [Android Architecture components][architecture] with [Koin][koin].

The architecture is divided into 3 layers:

### Data

I use **Repository** implementations to **control the flow of data access**. In those repositories where there is more than one data source, the abstract class [PrefetchLocalData][prefetchlocaldata] guides the search according to the precept of [Single source of truth][single-source].

### Domain

This layer includes those **models** that are part of the project core (*and are independent of the data layer*) and the **repository interfaces**. Also belong the **Interactors, which use repositories and models to accomplish the use case**.

### Presentation

This layer consists mainly of Activity-ViewModel pairs.

#### ViewModel

The [ViewModel][viewmodel] is the fundamental piece of the [MVVM pattern][mvvm-pattern]. It exposes [LiveData][livedata] objects, which are **lifecycle-aware**, and thanks to this it can protect the state of the information in situations such as changing the orientation of the screen.

On the other hand, the management of asynchrony is done through an instance of [InteractorExecutor][executor], which is responsible for launching the execution of Interactors.

#### Activity

The Activity acts as a simple **Observer** of the data exposed by the ViewModel.


## Testing

In this project I have focused mainly on covering with **unit tests** the classes that contain most of the logic of the app: **ViewModels** and **Interactors**.




[architecture]: https://developer.android.com/topic/libraries/architecture
[koin]: https://insert-koin.io/
[prefetchlocaldata]: https://github.com/JoseAngelManeiro/Movies-MVVM/blob/master/app/src/main/java/com/joseangelmaneiro/movies/data/PrefetchLocalData.kt
[single-source]: https://developer.android.com/jetpack/docs/guide#truth
[viewmodel]: https://developer.android.com/topic/libraries/architecture/viewmodel
[mvvm-pattern]: https://docs.microsoft.com/en-us/previous-versions/msp-n-p/hh848246(v%3dpandp.10)
[livedata]: https://developer.android.com/topic/libraries/architecture/livedata
[resource]: https://github.com/JoseAngelManeiro/Movies-MVVM/blob/master/app/src/main/java/com/joseangelmaneiro/movies/platform/Resource.kt
[executor]: https://github.com/JoseAngelManeiro/Movies-MVVM/blob/master/app/src/main/java/com/joseangelmaneiro/movies/platform/executor/InteractorExecutor.kt
