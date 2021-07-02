package com.zakrodionov.common.custom

import android.text.method.DialerKeyListener

class PhoneKeyListener : DialerKeyListener() {
    companion object {
        val instance by lazy { PhoneKeyListener() }

        private val CHARACTERS = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '#',
            '*',
            '+',
            '-',
            '(',
            ')',
            ',',
            '/',
            'N',
            '.',
            ' ',
            ';',
            '_'
        )
    }

    override fun getAcceptedChars(): CharArray {
        return CHARACTERS
    }
}
