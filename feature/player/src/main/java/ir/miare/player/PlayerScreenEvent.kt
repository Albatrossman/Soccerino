package ir.miare.player

sealed class PlayerScreenEvent {

    data object OnCloseClick : PlayerScreenEvent()

    data object OnFollowClick : PlayerScreenEvent()

}