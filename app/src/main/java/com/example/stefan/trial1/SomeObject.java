package com.example.stefan.trial1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Stefan on 9/19/2016.
 */
public class SomeObject {

    private FloatBuffer vertexBuffer;
    private int FLOAT_BYTES = 4;

    private float[] vertexArray = {
            //2D square
            -1f,-1f, -1f, 0, 1,
             1f, 1f, -1f, 1, 0,
            -1f, 1f, -1f, 0, 0,
            ///
            1f, 1f, -1f, 1, 0,
            -1f,-1f,-1f, 0, 1,
            1f, -1f, -1f, 1, 1

    };

    public SomeObject(){
        // Put data into client based memory location
        vertexBuffer = ByteBuffer.allocateDirect(vertexArray.length * FLOAT_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);
    }

    public FloatBuffer getData(){
        return vertexBuffer;
    }

}
