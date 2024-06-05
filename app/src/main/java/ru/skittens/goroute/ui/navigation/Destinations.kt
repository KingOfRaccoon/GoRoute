package ru.skittens.goroute.ui.navigation

enum class Destinations(val title: String = "") {
    Splash,
    OnBoarding,
    Authentication,
    Registration,
    MainEmployee,
    MainAdmin,
    MainTourist,
    Map("Карта"),
    NewsFriends("Лента новостей"),
    Profile("Профиль"),
    ListFriends("Список друзей"),
    Rating("Рейтинг"),
    Achievement("Достижения"),
    Event("Точка интереса")
}