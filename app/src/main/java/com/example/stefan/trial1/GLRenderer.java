package com.example.stefan.trial1;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Util.ShaderHelper;
import Util.TextResourceReader;

/**
 * Created by Stefan on 9/19/2016.
 */
public class GLRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private SomeObject object;

    BackgroundProgram background;
    FloatProgram fprogram;
    private float[] perspectiveM = new float[16];
    private float[] MVP = new float[16];
    float[] col1;
    int count;
    boolean goingup = false;
    private float globalStartTime;


    public GLRenderer(Context context){
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0,0,1,0);
        col1 = new float[]{0,0 ,255};
        count = 2;

        fprogram = new FloatProgram(context);
        fprogram.setUp();
        //background = new BackgroundProgram(context);
        //background.setUp();

        globalStartTime  = System.nanoTime();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.setIdentityM(MVP, 0);
        Matrix.translateM(MVP, 0, MVP, 0, 0, 0, 0);
        Matrix.perspectiveM(perspectiveM, 0,90, (float)width/ (float)height,1, 2);
        Matrix.multiplyMM(MVP, 0, perspectiveM, 0, MVP, 0);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //background.useProgram();
        //background.setUniforms(MVP);
        //background.bindData();
        //background.draw();

        fprogram.useProgram();
        fprogram.setUniforms(MVP,globalStartTime);
        fprogram.bindData();
        fprogram.draw();
    }

    public int transition(int count){
        Log.d("count",String.valueOf(count));
        if(!goingup && col1[count] > 0){
            col1[count]--;
            return 0;
        }else if(!goingup && col1[count] == 0){
            goingup = true;
            return 1;
        }else if(goingup && col1[count] < 255){
            col1[count]++;
            return 0;
        }else {
            goingup = false;
            return 0;
        }

    }
}
