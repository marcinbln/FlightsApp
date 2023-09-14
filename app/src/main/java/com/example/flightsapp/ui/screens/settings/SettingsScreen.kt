package com.example.flightsapp.ui.screens.settings

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flightsapp.R
import com.example.flightsapp.core.common.ui.OnResume
import com.example.flightsapp.core.model.DarkThemeConfig
import com.example.flightsapp.ui.components.BaseCard
import com.example.flightsapp.ui.screens.settings.components.DarkModeSettingsAlertDialog
import com.example.flightsapp.ui.screens.settings.components.SettingsTile
import com.example.flightsapp.ui.screens.settings.components.Switch
import com.example.flightsapp.ui.theme.FlightsAppTheme
import com.example.flightsapp.ui.theme.customColorsPalette
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.util.Locale

@Composable
fun SettingsRoute(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val settingsUiState by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsScreen(
        onSignOutClick = settingsViewModel::onSignOutClick,
        onDarkModeSelect = settingsViewModel::onDarkModeSelect,
        settingsUiState = settingsUiState,
        dynamicColorsSwitched = settingsViewModel::dynamicColorsSwitched
    )
}

@Composable
private fun SettingsScreen(
    settingsUiState: SettingsUiState,
    onSignOutClick: () -> Unit,
    onDarkModeSelect: (DarkThemeConfig) -> Unit,
    dynamicColorsSwitched: () -> Unit
) {
    val context = LocalContext.current
    val areNotificationsEnabled = remember { mutableStateOf("") }
    val onLabel = stringResource(R.string.settings_notifications_on_label)
    val offLabel = stringResource(R.string.settings_notifications_off_label)

    BaseCard {
        CardContent(
            settingsUiState = settingsUiState,
            onDarkModeSelect = onDarkModeSelect,
            context = context,
            areNotificationsEnabled = areNotificationsEnabled,
            dynamicColorsSwitched = dynamicColorsSwitched,
            onSignOutClick = onSignOutClick
        )
    }

    OnResume {
        when {
            NotificationManagerCompat
                .from(context)
                .areNotificationsEnabled() ->
                areNotificationsEnabled.value = onLabel

            else -> areNotificationsEnabled.value = offLabel
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState,
    onDarkModeSelect: (DarkThemeConfig) -> Unit,
    context: Context,
    areNotificationsEnabled: MutableState<String>,
    dynamicColorsSwitched: () -> Unit,
    onSignOutClick: () -> Unit
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    val hasNotificationPermission = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }

    val notificationPermissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(
                permission = Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            null
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission.value = isGranted
            }
        )

    Column(
        modifier
            .padding(horizontal = 22.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = modifier.height(25.dp))

        if (openAlertDialog.value) {
            if (settingsUiState is SettingsUiState.Success) {
                DarkModeSettingsAlertDialog(
                    dialogTitle = stringResource(R.string.settings_dark_mode_dialog_title),
                    selectedOption = settingsUiState.settings.darkThemeConfig,
                    onSelected = {
                        onDarkModeSelect(it)
                        openAlertDialog.value = false
                    },
                    onDismissRequest = { openAlertDialog.value = false }
                )
            }
        }

        Text(
            modifier = modifier.padding(
                start = 8.dp,
                bottom = 18.dp
            ),
            text = stringResource(id = R.string.settings_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Column(modifier = modifier.weight(1f)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                SettingsTile(
                    leadingIcon = R.drawable.ic_language,
                    settingName = R.string.settings_language,
                    content = {
                        Text(
                            text = Locale
                                .getDefault()
                                .toLanguageTag(),
                            style = TextStyle(fontWeight = FontWeight.Light)
                        )

                        Spacer(modifier = modifier.width(10.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_forward),
                            contentDescription = null
                        )
                    },
                    onClick = {
                        context.openLanguageSettings()
                    }
                )

                Spacer(modifier = modifier.height(18.dp))
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                SettingsTile(
                    leadingIcon = R.drawable.ic_dark_mode,
                    settingName = R.string.settings_dark_mode,
                    content = {
                        if (settingsUiState is SettingsUiState.Success) {
                            Text(
                                text = stringResource(id = settingsUiState.settings.darkThemeConfig.display),
                                style = TextStyle(fontWeight = FontWeight.Light)
                            )
                        }
                    },
                    onClick = {
                        openAlertDialog.value = true
                    }
                )

                Spacer(modifier = modifier.height(18.dp))
            }

            SettingsTile(
                leadingIcon = R.drawable.ic_notifications,
                settingName = R.string.settings_notifications,
                content = {
                    Text(
                        text = areNotificationsEnabled.value
                    )

                    Spacer(modifier = modifier.width(10.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_forward),
                        contentDescription = null
                    )
                },
                onClick = {
                    handleNotificationsOnClick(
                        hasNotificationPermission.value,
                        permissionLauncher,
                        notificationPermissionState,
                        context
                    )
                }
            )

            Spacer(modifier = modifier.height(18.dp))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                SettingsTile(
                    leadingIcon = R.drawable.ic_palette,
                    settingName = R.string.settings_dynamic_colors,
                    content = {
                        if (settingsUiState is SettingsUiState.Success) {
                            Switch(
                                checkedState = settingsUiState.settings.dynamicColors,
                                onCheckedChange = dynamicColorsSwitched
                            )
                        }
                    },
                    enabled = false
                )

                Spacer(modifier = modifier.height(18.dp))
            }
        }

        TextButton(onClick = onSignOutClick, content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.settings_sign_out),
                color = MaterialTheme.customColorsPalette.extraTextColor,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        })

        Spacer(modifier = modifier.height(24.dp))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun handleNotificationsOnClick(
    hasNotificationPermission: Boolean,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    notificationPermissionState: PermissionState?,
    context: Context
) {
    val shouldShowRationale = notificationPermissionState?.status?.shouldShowRationale ?: false
    if (!hasNotificationPermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    if (hasNotificationPermission || !shouldShowRationale) {
        context.openNotificationsSettings()
    }
}

private fun Context.openNotificationsSettings() {
    val intent = Intent().apply {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(
                    Settings.EXTRA_APP_PACKAGE,
                    this@openNotificationsSettings.packageName
                )
            }

            else -> {
                action = "android.settings.APP_NOTIFICATION_SETTINGS"
                putExtra(
                    "app_package",
                    this@openNotificationsSettings.packageName
                )
                putExtra(
                    "app_uid",
                    this@openNotificationsSettings.applicationInfo.uid
                )
            }
        }
    }
    startActivity(intent)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun Context.openLanguageSettings() {
    val intent = Intent(
        Settings.ACTION_APP_LOCALE_SETTINGS,
        Uri.parse("package:${this.packageName}")
    )
    startActivity(intent)
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SettingsScreenPreview() {
    FlightsAppTheme {
        SettingsScreen(
            onSignOutClick = {},
            onDarkModeSelect = {},
            settingsUiState = SettingsUiState.Success(
                UserEditableSettings(DarkThemeConfig.FOLLOW_SYSTEM, dynamicColors = false)
            ),
            dynamicColorsSwitched = {}
        )
    }
}
