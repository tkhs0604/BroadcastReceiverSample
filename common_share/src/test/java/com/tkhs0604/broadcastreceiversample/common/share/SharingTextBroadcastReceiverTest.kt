package com.tkhs0604.broadcastreceiversample.common.share

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharingTextBroadcastReceiverTest {

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "com.tkhs0604.broadcastreceiversample.common.share.test.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION"
    )

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var sut: SharingTextBroadcastReceiver

    @Before
    fun setUp() {
        sut = SharingTextBroadcastReceiver()
    }

    @Test
    fun `register receiver should be called successfully`() {
        sut.register(context)
    }
}