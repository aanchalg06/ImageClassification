package com.aanchal.imageclassification

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

object NNAPIClass {

    val imageSize = 32

    init {
        System.loadLibrary("imageclassification")
    }


    fun imagechecker(context: Context, image: Bitmap, callback: (String) -> Unit) {


        val outr = TensorBuffer.createFixedSize(intArrayOf(1, NUM_CLASSES), DataType.FLOAT32)
        interpreter.run(inputTensor.buffer, outputTensor.buffer)




        val class = arrayOf("airplane","automobile","bird","cat","deer","dog","frog","horse","ship","truck")
        val predic = classes[maxIndex]

        callback(predic)


        inti.close()
    }

    private fun initializeInterpreter(context: Context): Interpreter {
        val opt = Interpreter.Options()

    
        return Interpreter(loadModelFile(context), opt)
    }



}
