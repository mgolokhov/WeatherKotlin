package org.dodroid.max.weatherkotlin.domain.commands


interface Command<out T>{
    fun execute(): T
}