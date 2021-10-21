package com.guerrero.mytrusteddevice.shared

class PreferenceNotFoundException(
    preferenceKey: String
) : Exception("Preference not found: $preferenceKey")
