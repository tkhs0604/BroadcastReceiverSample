package com.tkhs0604.broadcastreceiversample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tkhs0604.broadcastreceiversample.feature.share.SharingTextBroadcastReceiver
import com.tkhs0604.broadcastreceiversample.ui.theme.BroadcastReceiverSampleTheme

class MainActivity : ComponentActivity() {

    private val broadcastReceiver = SharingTextBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        broadcastReceiver.register(this)

        setContent {
            BroadcastReceiverSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        var text by rememberSaveable { mutableStateOf("") }
                        TextField(
                            value = text,
                            onValueChange = { text = it }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                runCatching {
                                    val intent = SharingTextBroadcastReceiver.createChooserIntent(
                                        this@MainActivity,
                                        text
                                    )
                                    startActivity(intent)
                                }
                            },
                        ) {
                            Text(text = "share text")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        broadcastReceiver.unregister(this)

        super.onDestroy()
    }
}
