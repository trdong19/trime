/*
 * SPDX-FileCopyrightText: 2015 - 2025 Rime community
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.trdong19.trime.ime.broadcast

import android.view.inputmethod.EditorInfo
import com.trdong19.trime.core.CompositionProto
import com.trdong19.trime.core.MenuProto
import com.trdong19.trime.core.RimeMessage
import com.trdong19.trime.core.SchemaItem
import com.trdong19.trime.core.StatusProto
import com.trdong19.trime.ime.window.BoardWindow

interface InputBroadcastReceiver {
    fun onStartInput(info: EditorInfo) {}

    fun onSelectionUpdate(start: Int, end: Int) {}

    fun onRimeSchemaUpdated(schema: SchemaItem) {}

    fun onRimeOptionUpdated(value: RimeMessage.OptionMessage.Data) {}

    fun onCandidateListUpdate(data: RimeMessage.CandidateListMessage.Data) {}

    fun onCompositionUpdate(data: CompositionProto) {}

    fun onCandidateMenuUpdate(data: MenuProto) {}

    fun onKeyAppearanceUpdate(composing: Boolean, menu: Boolean, paging: Boolean) {}

    fun onInputStatusUpdate(value: StatusProto) {}

    fun onWindowAttached(window: BoardWindow) {}

    fun onWindowDetached(window: BoardWindow) {}

    fun onEnterKeyLabelUpdate(label: String) {}
}
