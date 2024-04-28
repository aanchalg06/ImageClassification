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
    private const val MODEL_FILE_NAME = "model.tflite"
    private const val NUM_CLASSES = 10
    val imageSize = 32

    init {
        System.loadLibrary("imageclassification")
    }


    fun classifyImage(context: Context, image: Bitmap, callback: (String) -> Unit) {

        val interpreter = initializeInterpreter(context)


        val inputTensor = prepareInputTensor(image)


        val outputTensor = TensorBuffer.createFixedSize(intArrayOf(1, NUM_CLASSES), DataType.FLOAT32)
        interpreter.run(inputTensor.buffer, outputTensor.buffer)


        val confidenceArray = outputTensor.floatArray
        var maxIndex = 0
        var maxConfidence = confidenceArray[0]
        for (i in 1 until confidenceArray.size) {
            if (confidenceArray[i] > maxConfidence) {
                maxConfidence = confidenceArray[i]
                maxIndex = i
            }
        }


        val classes = arrayOf("airplane","automobile","bird","cat","deer","dog","frog","horse","ship","truck")
        val predictedClass = classes[maxIndex]

        callback(predictedClass)


        interpreter.close()
    }

    private fun initializeInterpreter(context: Context): Interpreter {
        val options = Interpreter.Options()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val nnApiDelegate = NnApiDelegate()
            options.addDelegate(nnApiDelegate)
        }

        return Interpreter(loadModelFile(context), options)
    }

    private external fun loadModelFile(assetManager: Any, modelFileName: String): ByteBuffer

    private fun loadModelFile(context: Context): ByteBuffer {
        return loadModelFile(context.assets, MODEL_FILE_NAME)
    }

    private fun prepareInputTensor(image: Bitmap): TensorBuffer {
        val inputTensor = TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 32 * 32 * 3).order(ByteOrder.nativeOrder())


        val resizedImage = Bitmap.createScaledBitmap(image, 32, 32, true)


        for (y in 0 until 32) {
            for (x in 0 until 32) {
                val pixel = resizedImage.getPixel(x, y)
                byteBuffer.putFloat((pixel shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((pixel shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((pixel and 0xFF) * (1f / 1))
            }
        }

        inputTensor.loadBuffer(byteBuffer)
        return inputTensor
    }
}