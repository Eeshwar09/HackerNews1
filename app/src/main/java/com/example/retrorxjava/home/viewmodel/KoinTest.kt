package com.example.retrorxjava.home.viewmodel

import org.koin.core.KoinComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

interface KoinTest: KoinComponent
inline fun <reified T> KoinTest.get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T =
    getKoin().get(qualifier, parameters)

inline fun <reified T> KoinTest.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { get<T>(qualifier, parameters) }
