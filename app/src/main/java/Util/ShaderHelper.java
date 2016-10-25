package Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.example.stefan.trial1.R;

import java.nio.FloatBuffer;

/**
 * Created by Stefan on 9/19/2016.
 */
public class ShaderHelper {

    public static int compileVertexShader(String shaderCode){
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }


    private static int compileShader(int type, String shaderCode){
        // First we create a new shader object which takes in the shader type. This method will return an ID for the shader
        final int shaderObjectID =  GLES20.glCreateShader(type);
        // If the ID is 0 then something went wrong
        if(shaderObjectID == 0){
            Log.w("Error", "Something went wrong");
            return 0;
        }

        // Now add the shaderSourceCode to the object created
        GLES20.glShaderSource(shaderObjectID, shaderCode);
        //Once source code is in the created object, we can compile the shader. VERY SIMPLE UP TO NOW
        GLES20.glCompileShader(shaderObjectID);

        //JUST CHECKING IF COMPILES PROBABLY - NOT IMPORTANT
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectID, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjectID);
            Log.w("Error", "Compilation of shader failed with " + String.valueOf(type));
            return 0;
        }

        return shaderObjectID;


    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        //First you create the new Program Object
        int programObjectId = GLES20.glCreateProgram();
        //Next you add the shaders
        GLES20.glAttachShader(programObjectId,vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);
        //Now to link the shaders
        GLES20.glLinkProgram(programObjectId);

        return programObjectId;


    }

    public static void setVertexAttribPointer(FloatBuffer vertexData, int dataOffset, int attributeLocation, int componentCount, int stride){
        vertexData.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, vertexData);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        vertexData.position(0);
    }

    public static int loadTexture(final Context context, final int resourceID){
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        if(textureHandle[0] != 0){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, options);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0,bitmap, 0);
            bitmap.recycle();
        }
        return textureHandle[0];
    }
//Doesnt work
    public static int loadTextureWithText(Context context, int resourceID, String text){
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        if(textureHandle[0] != 0){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            final Bitmap bitmap =  BitmapFactory.decodeResource(context.getResources(), resourceID, options);
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            Paint textPaint = new Paint();
            textPaint.setTextSize(32);
            textPaint.setAntiAlias(true);
            textPaint.setARGB(0, 0, 0, 0);
            canvas.drawText(text, (width / 2 - 20), (height / 2), textPaint);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap2, 0);
            bitmap2.recycle();
            bitmap.recycle();
        }
        return textureHandle[0];

    }
}
