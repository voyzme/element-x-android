/*
 * Copyright 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 * Please see LICENSE in the repository root for full details.
 */

package io.element.android.features.voicetranscription

import io.element.android.libraries.matrix.api.room.MatrixRoom
import io.element.android.libraries.matrix.api.timeline.MatrixTimelineItem
import io.element.android.libraries.matrix.api.timeline.Timeline
import io.element.android.libraries.matrix.api.timeline.item.event.MessageContent
import io.element.android.libraries.matrix.api.timeline.item.event.EventContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import io.element.android.libraries.di.RoomScope
import io.element.android.libraries.di.SingleIn

// @SingleIn(RoomScope::class)
class VoiceMessageTranscriber(// @Inject constructor(
    private val timeline: Timeline,
    private val room: MatrixRoom,
    private val scope: CoroutineScope,
) {
    fun start() {
        timeline.timelineItems
            .onEach { items ->
                items.filterIsInstance<MatrixTimelineItem.Event>()
                    //.filter { isVoiceMessage(it) && it.event.isOwn }
                    // .filter { !hasTranscriptionAlready(it, items) }
                    .forEach { item ->
                        scope.launch {
                            try {
                                val audioFile = resolveAudioFile(item) ?: return@launch
                                val transcript = transcribe(audioFile)
                                if (transcript.isNotBlank()) {
                                    // todo send message
                                    Unit
                                    //room.sendMessage("[Voice transcription] $transcript", null, emptyList())
                                }
                            } catch (e: Exception) {
                                Timber.e(e, "Error transcribing voice message")
                            }
                        }
                    }
            }
            .launchIn(scope)
    }

    private fun isVoiceMessage(item: MatrixTimelineItem.Event): Boolean {
        // val content = item.event.content as? MessageContent ?: return false
        // val isAudio = content.msgType == "m.audio"
        // val isVoice = content.voiceMessageIndicator != null
        // return isAudio && isVoice
        // todo redo it
        return true
    }

    private fun hasTranscriptionAlready(
        voiceItem: MatrixTimelineItem.Event,
        allItems: List<MatrixTimelineItem>
    ): Boolean {
        val voiceEventId = voiceItem.event.eventId?.value ?: return false
        return allItems.filterIsInstance<MatrixTimelineItem.Event>()
            .any {
                // val rawContent = it.event.content as? EventContent ?: return@any false
                // rawContent.toString().contains("m.voice.transcription") &&
                //    rawContent.toString().contains(voiceEventId)
                // todo look for transcription linked to voice event id
                return true
            }
    }

    private fun resolveAudioFile(item: MatrixTimelineItem.Event): File? {
        // TODO: Implement actual file download or retrieval
        return null
    }

    private fun transcribe(file: File): String {
        // TODO: Hook into Vosk or other STT engine
        return "(transcribed text)"
    }
}
