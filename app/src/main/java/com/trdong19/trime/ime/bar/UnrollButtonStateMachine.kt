/*
 * SPDX-FileCopyrightText: 2015 - 2026 Rime community
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.trdong19.trime.ime.bar

import com.trdong19.trime.ime.bar.UnrollButtonStateMachine.BooleanKey.UnrolledCandidatesEmpty
import com.trdong19.trime.ime.bar.UnrollButtonStateMachine.BooleanKey.UnrolledCandidatesHighlighted
import com.trdong19.trime.ime.bar.UnrollButtonStateMachine.State.ClickToAttachWindow
import com.trdong19.trime.ime.bar.UnrollButtonStateMachine.State.ClickToDetachWindow
import com.trdong19.trime.ime.bar.UnrollButtonStateMachine.State.Hidden
import com.trdong19.trime.util.BuildTransitionEvent
import com.trdong19.trime.util.EventStateMachine
import com.trdong19.trime.util.TransitionBuildBlock

object UnrollButtonStateMachine {
    enum class State {
        ClickToAttachWindow,
        ClickToDetachWindow,
        Hidden,
    }

    enum class BooleanKey : EventStateMachine.BooleanStateKey {
        UnrolledCandidatesEmpty,
        UnrolledCandidatesHighlighted,
    }

    enum class TransitionEvent(
        val builder: TransitionBuildBlock<State, BooleanKey>,
    ) : EventStateMachine.TransitionEvent<State, BooleanKey> by BuildTransitionEvent(builder) {
        UnrolledCandidatesUpdated({
            from(Hidden) transitTo ClickToAttachWindow on (UnrolledCandidatesEmpty to false)
            from(ClickToAttachWindow) transitTo Hidden on (UnrolledCandidatesEmpty to true)
            from(ClickToAttachWindow) transitTo ClickToDetachWindow on (UnrolledCandidatesHighlighted to true)
        }),
        UnrolledCandidatesAttached({
            from(ClickToAttachWindow) transitTo ClickToDetachWindow
        }),
        UnrolledCandidatesDetached({
            from(ClickToDetachWindow) transitTo Hidden on (UnrolledCandidatesEmpty to true)
            from(ClickToDetachWindow) transitTo ClickToAttachWindow on (UnrolledCandidatesEmpty to false)
        }),
    }

    fun new(block: (State) -> Unit) = EventStateMachine<State, TransitionEvent, BooleanKey>(
        initialState = Hidden,
        externalBooleanStates =
        mutableMapOf(
            UnrolledCandidatesEmpty to true,
        ),
    ).apply {
        onNewStateListener = block
    }
}
