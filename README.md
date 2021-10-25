# My Trusted Device App 🛡

![](https://img.shields.io/static/v1?style=flat-square&logo=android&label=API&message=23%2B&color=78c257)
![https://kotlinlang.org/](https://img.shields.io/static/v1?style=flat-square&label=Kotlin&message=1.5.30&color=007ec6)

## Prerequisites ☑
- Add Firebase messaging (google_services.json)
- Run backend locally: https://github.com/yafuquen/twilio-verify-example

## Architecture 🗼
MVVM Architecture pattern

### Components
- Repository: responsible for choosing the data source and the way to get the information.
- ViewState: representation of the configuration of a view and it is the type of message the ViewModel can emit to view.
- Wrapper: mapped of external API class that allow the API to change without affecting the app to much.

## Build with 🏗️
- LiveData
- Coroutines
- Retrofit
- Dagger Hilt
- Navigation Components
- Material Design Components
- ViewBinding
- Mockk
