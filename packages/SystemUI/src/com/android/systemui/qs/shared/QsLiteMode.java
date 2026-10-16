/*
 * Copyright (C) 2026 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.android.systemui.qs.shared;

import android.os.SystemProperties;

/** QS rendering options, read once to keep a gesture consistent. */
public final class QsLiteMode {
    private static final boolean ENABLED =
            SystemProperties.getInt("persist.sys.qs.lite.mode", 0) == 1;

    private QsLiteMode() {}

    public static boolean isEnabled() {
        return ENABLED;
    }
}
