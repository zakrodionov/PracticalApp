package com.zakrodionov.common.extensions.library

import android.widget.EditText
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

fun EditText.setPhoneMask(behaviour: DecoroBehaviour = RusPhoneNumberShortBehaviour()) {
    val slots: Array<Slot> = Slot.copySlotArray(behaviour.getSlots())
    behaviour.changeSlots(slots)
    val mask = MaskImpl(slots, behaviour.isTerminated)
    behaviour.changeMask(mask)
    val watcher = MaskFormatWatcher(mask)
    if (behaviour.fillWhenInstall()) {
        watcher.installOnAndFill(this)
    } else {
        watcher.installOn(this)
    }
}

// Fill mask when installed, forbid left movement
// e.g. +7 (999) 111-22-23
@Suppress("MagicNumber")
class RusPhoneNumberBehaviour : DecoroBehaviour() {
    override fun fillWhenInstall(): Boolean = true

    override fun getSlots(): Array<Slot> = PredefinedSlots.RUS_PHONE_NUMBER

    override fun changeSlots(slots: Array<Slot>) {
        slots[3].flags = slots[3].flags or Slot.RULE_FORBID_CURSOR_MOVE_LEFT
    }
}

// Fill mask when installed, forbid left movement
// e.g. +7 999 111-22-23
@Suppress("MagicNumber")
class RusPhoneNumberShortBehaviour : DecoroBehaviour() {
    override fun fillWhenInstall(): Boolean = true

    override fun getSlots(): Array<Slot> = RUS_PHONE_NUMBER_SHORT

    override fun changeSlots(slots: Array<Slot>) {
        slots[2].flags = slots[2].flags or Slot.RULE_FORBID_CURSOR_MOVE_LEFT
    }
}

open class DecoroBehaviour {
    open fun changeMask(mask: MaskImpl?) {}

    open fun changeSlots(slots: Array<Slot>) {}

    open val isTerminated: Boolean = true

    open fun fillWhenInstall(): Boolean = false

    open fun getSlots(): Array<Slot> = arrayOf()
}

val RUS_PHONE_NUMBER_SHORT = arrayOf(
    PredefinedSlots.hardcodedSlot('+'),
    PredefinedSlots.hardcodedSlot('7'),
    PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot('-').withTags(Slot.TAG_DECORATION),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot('-').withTags(Slot.TAG_DECORATION),
    PredefinedSlots.digit(),
    PredefinedSlots.digit()
)
