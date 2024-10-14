package com.sample.kinopoisk.core.domain.ext

internal val Double.roundTo: Double
    get() = Math.round(this * 100.0) / 100.00