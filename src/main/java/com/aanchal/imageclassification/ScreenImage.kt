package com.aanchal.imageclassification

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aanchal.imageclassification.NNAPIClass.imageSize

@Composable
fun ScreenImage() {
    val launcher = rememberLauncherForActivityResult(
        cont = ActivityResultContracts.GetContent(),
        
    )
        ElevatedCard(modifier = Modifier
            .align(Alignment.Center)
            .height(400.dp)
            .width(300.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            content = {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                        Image(
                            painter = painterResource(R.drawable.photo_gallary),
                            contentDescription = "imageclassification",
                            modifier = Modifier
                                .size(200.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(26.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                            var img by remember{ mutableStateOf("")}
                           
                            Text(text = img,color = Color.Black, fontSize = 34.sp)
                        
                        
                            Text("No File Choosen yet", color = Color.Black, fontSize = 34.sp)
                        
                    }
                }
            }
        )
    }
}
