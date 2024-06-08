package com.example.ui

interface ModelMapper<D, U> {
    fun fromDataModel(dataModel: D): U
    fun toDataModel(uiModel: U): D
}