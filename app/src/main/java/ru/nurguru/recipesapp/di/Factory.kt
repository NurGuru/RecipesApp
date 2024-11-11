package ru.nurguru.recipesapp.di

interface Factory<T> {
    fun create(): T
}