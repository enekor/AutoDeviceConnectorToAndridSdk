// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {

    var uriValue by remember { mutableStateOf("") }
    var uriExists by remember{ mutableStateOf("") }
    var sdkExists by remember { mutableStateOf(false) }
    var port by remember { mutableStateOf("") }
    var connected by remember { mutableStateOf("") }
    var inicio = true

    if(inicio){
        if(!Util.checkExistinfUriFile()){
            uriExists = "SDK path not definned"
            inicio = false
        }else{
            sdkExists = true
            uriValue = Util.sdkUri
            uriExists = "SDK located"
            inicio = false
        }
    }

    MaterialTheme {
        Column(
            Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uriValue,
                    onValueChange = { uriValue = it },
                    label = { Text("SDK uri: ") },
                    placeholder = { Text("""C:\Users\<username>\AppData\Local\Android\sdk""") }
                )

                Button(
                    onClick = {
                        if(Util.checkUriFile(uriValue)){
                            uriExists = "SDK found"
                            Util.sdkUri = uriValue
                            Util.saveSdkUriFile(uriValue)
                            sdkExists = true
                        }else{
                            uriExists = "SDK path not found"
                        }
                    },
                    modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                ) {
                    Text("Check")
                }
            }
            Text(
                uriExists,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp,end = 50.dp)
            )

            if(sdkExists){
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = port,
                        onValueChange = { port = it },
                        label = { Text("Device port: ") },
                        placeholder = { Text("localhost:5555") }
                    )
                    Button(
                        onClick = { connected = Util.connectPort(port) },
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                    ) {
                        Text("Connect")
                    }
                }
                Text(
                    connected,
                    modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
