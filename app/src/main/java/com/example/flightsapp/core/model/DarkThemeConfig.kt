/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.flightsapp.core.model

import androidx.annotation.StringRes
import com.example.flightsapp.R

enum class DarkThemeConfig(@StringRes val display: Int) {
    FOLLOW_SYSTEM(R.string.dark_mode_config_system_default), LIGHT(R.string.dark_mode_config_light), DARK(
        R.string.dark_mode_config_dark
    )
}
