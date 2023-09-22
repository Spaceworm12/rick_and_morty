package com.example.rickandmorty.presentation.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import java.util.*

@Suppress("NAME_SHADOWING")
class CoordinatorRM(
    private val router: Router,
) : Coordinator {
    private val stack = ArrayDeque<Screen>()
    override fun goTo(screen: Screen) {
        val screen = map(screen)
        router.navigateTo(screen)
        stack.push(screen)
    }

    override fun goToDistinct(screen: Screen) {
        val screened = map(screen)
        if (stack.isNotEmpty() && stack.peek()!!.screenKey == screen.screenKey) {
            router.exit()
            stack.pop()
        }
        goTo(screened)
    }

    override fun goBack() {
        router.exit()
        if (stack.isNotEmpty()) stack.pop()
    }

    override fun goBackTo(screen: Screen) {
        val screen = map(screen)
        router.backTo(screen)
        while (stack.isNotEmpty() && stack.peek()!!.screenKey != screen.screenKey) stack.pop()
    }

    override fun replace(screen: Screen) {
        val screen = map(screen)
        router.replaceScreen(screen)
        if (stack.isNotEmpty()) stack.pop()
        stack.push(screen)
    }

    override fun newRootScreen(screen: Screen) {
        val screen = map(screen)
        router.newRootScreen(screen)
        stack.clear()
        stack.push(screen)
    }

    override fun clearStack() {
        stack.clear()
    }

    private fun map(screen: Screen): Screen {
        return screen
    }

    private fun getScreenName(screen: Screen): String {
        return if (screen is FragmentScreen) {
            screen.javaClass.simpleName ?: "null"
        } else {
            screen.javaClass.simpleName
        }
    }
}
