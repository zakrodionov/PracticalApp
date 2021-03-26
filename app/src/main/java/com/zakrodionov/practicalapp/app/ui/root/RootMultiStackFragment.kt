package com.zakrodionov.practicalapp.app.ui.root

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.MultiScreen
import com.github.terrakok.modo.android.multi.MultiStackFragmentImpl
import com.github.terrakok.modo.backToTabRoot
import com.github.terrakok.modo.selectStack
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.ui.Tab
import org.koin.android.ext.android.inject

class RootMultiStackFragment : MultiStackFragmentImpl() {

    private val modo: Modo by inject()

    override fun createTabView(index: Int, parent: LinearLayout): View =
        LayoutInflater.from(context).inflate(R.layout.layout_tab, parent, false).apply {
            findViewById<ImageView>(R.id.ivIcon).setImageResource(Tab.values()[index].icon)
            findViewById<TextView>(R.id.tvTitle).text = Tab.values()[index].title
            setOnClickListener {
                val currentScreen = modo.state.chain.lastOrNull()
                if (currentScreen is MultiScreen) {
                    if (currentScreen.selectedStack != index) {
                        modo.selectStack(index)
                    } else {
                        modo.backToTabRoot()
                    }
                }
            }
        }
}
