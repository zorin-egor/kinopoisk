package com.sample.kinopoisk.core.domain

internal val Double.roundTo: Double
    get() = Math.round(this * 100.0) / 100.00