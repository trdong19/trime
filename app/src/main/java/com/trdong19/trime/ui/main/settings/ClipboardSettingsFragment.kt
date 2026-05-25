/*
 * SPDX-FileCopyrightText: 2015 - 2025 Rime community
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.trdong19.trime.ui.main.settings

import com.trdong19.trime.data.prefs.AppPrefs
import com.trdong19.trime.data.prefs.PreferenceDelegateFragment

class ClipboardSettingsFragment : PreferenceDelegateFragment(AppPrefs.defaultInstance().clipboard)
