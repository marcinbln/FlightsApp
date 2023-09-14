package com.example.flightsapp.core.common.errors

import androidx.annotation.StringRes

abstract class ErrorStringRes(@StringRes val errorMessage: Int) : Error()
