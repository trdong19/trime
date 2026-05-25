/*
 * SPDX-FileCopyrightText: 2015 - 2024 Rime community
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.trdong19.trime.ui.main.settings

import com.trdong19.trime.data.prefs.AppPrefs
import com.trdong19.trime.data.prefs.PreferenceDelegateFragment

class CandidatesSettingsFragment : PreferenceDelegateFragment(AppPrefs.defaultInstance().candidates)
