package com.zakrodionov.practicalapp.app.core

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseActivity(@LayoutRes val layoutResId: Int) : AppCompatActivity(layoutResId)
