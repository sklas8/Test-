package com.example.stefan.trial1;

import android.graphics.Point;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Stefan on 9/25/2016.
 */
public class Float {

    private float[] vertexData = new float[2];
    private FloatBuffer vertexBuffer;

    private float[] vertexArray = {
            //2D square
            -1f,-1f,
            1f, 1f,
            -1f, 1f,
            1f, 1f,
            -1f,-1f,
            1f, -1f

    };

    public Float(){
        // Put data into client based memory location
        vertexBuffer = ByteBuffer.allocateDirect(vertexArray.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);
    }

    public FloatBuffer getData(){
        return vertexBuffer;
    }
}
