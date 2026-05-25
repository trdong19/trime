/*
 * SPDX-FileCopyrightText: 2015 - 2025 Rime community
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.trdong19.trime.ime.composition

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewOutlineProvider
import com.trdong19.trime.core.CompositionProto
import com.trdong19.trime.daemon.RimeSession
import com.trdong19.trime.daemon.launchOnReady
import com.trdong19.trime.data.theme.ColorManager
import com.trdong19.trime.data.theme.Theme
import com.trdong19.trime.ime.broadcast.InputBroadcastReceiver
import com.trdong19.trime.ime.core.TouchEventReceiverWindow
import com.trdong19.trime.ime.dependency.InputDependencyManager
import org.kodein.di.instance
import splitties.dimensions.dp
import splitties.views.horizontalPadding

class PreeditDelegate : InputBroadcastReceiver {

    private val context: Context by InputDependencyManager.getInstance().di.instance()
    private val theme: Theme by InputDependencyManager.getInstance().di.instance()
    private val rime: RimeSession by InputDependencyManager.getInstance().di.instance()

    val ui =
        PreeditUi(
            context,
            theme,
            setupPreeditView = {
                val radiusSize = dp(theme.preedit.topEndRadius)
                val radii = if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                    floatArrayOf(0f, 0f, radiusSize, radiusSize, 0f, 0f, 0f, 0f)
                } else {
                    floatArrayOf(radiusSize, radiusSize, 0f, 0f, 0f, 0f, 0f, 0f)
                }
                background = GradientDrawable().apply {
                    setColor(ColorManager.getColor("text_back_color"))
                    shape = GradientDrawable.RECTANGLE
                    cornerRadii = radii
                }
                clipToOutline = true
                outlineProvider = ViewOutlineProvider.BACKGROUND
                horizontalPadding = dp(theme.preedit.horizontalPadding)
            },
            onMoveCursor = { pos -> rime.launchOnReady { it.moveCursorPos(pos) } },
        ).apply {
            root.alpha = theme.preedit.alpha
            root.visibility = View.INVISIBLE
        }

    private val touchEventReceiverWindow = TouchEventReceiverWindow(ui.root)

    override fun onCompositionUpdate(data: CompositionProto) {
        ui.update(data)
        ui.root.visibility = if (ui.visible) View.VISIBLE else View.INVISIBLE
        if (data.length > 0) {
            touchEventReceiverWindow.show()
        } else {
            touchEventReceiverWindow.dismiss()
        }
    }
}
