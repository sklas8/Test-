package com.example.stefan.trial1;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLES20;

import Util.ShaderHelper;
import Util.TextResourceReader;

/**
 * Created by Stefan on 9/26/2016.
 */
public class FloatProgram implements Program {

    private Context context;
    private int programID;
    private int projectionLocation;
    private int currentTimeLocation;
    private int colorLocation;
    private int positionLocation;
    private int directionVectorLocation;
    private int startTimeLocation;
    private Float fobject;

    public FloatProgram(Context context){
        this.context = context;
        fobject = new Float();
    }

    @Override
    public int setUp() {
        String vertex_input = TextResourceReader.readTextFileFromResource(context, R.raw.vertex_particleshader);
        String fragment_input = TextResourceReader.readTextFileFromResource(context, R.raw.fragment_particleshader);

        int vertexID = ShaderHelper.compileVertexShader(vertex_input);
        int fragmentID = ShaderHelper.compileFragmentShader(fragment_input);

        programID = ShaderHelper.linkProgram(vertexID, fragmentID);

        projectionLocation = GLES20.glGetUniformLocation(programID, "u_Matrix");
        currentTimeLocation = GLES20.glGetUniformLocation(programID, "u_CurrentTime");
        //colorLocation = GLES20.glGetAttribLocation(programID, "a_Color");
        positionLocation =GLES20.glGetAttribLocation(programID, "a_Position");
        //directionVectorLocation = GLES20.glGetAttribLocation(programID, "a_DirectionVector");
        //startTimeLocation= GLES20.glGetAttribLocation(programID, "a_StartTime");

        return programID;
    }

    public void useProgram(){
        GLES20.glUseProgram(programID);
    }

    public void setUniforms(float[] matrix, float globalTime) {
        GLES20.glUniformMatrix4fv(projectionLocation, 1, false, matrix, 0);
        GLES20.glUniform1f(currentTimeLocation, globalTime);
    }

    public void bindData(){
        ShaderHelper.setVertexAttribPointer(fobject.getData(),0, positionLocation, 2,0 );
        //ShaderHelper.setVertexAttribPointer(fobject.getData(),2, colorLocation, 3, 1);
        //ShaderHelper.setVertexAttribPointer(fobject.getData(),5, directionVectorLocation, 2,1) ;
        //ShaderHelper.setVertexAttribPointer(fobject.getData(),7, currentTimeLocation,1,1);
    }
    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,1);

    }

    public int fp_projectionLocation(){
        return projectionLocation;
    }

    public int fp_currentTimeLocation(){
        return currentTimeLocation;
    }

    public int fp_colorLocation(){
        return colorLocation;
    }

    public int fp_positionLocation(){
        return positionLocation;
    }

    public int fp_directionVectorLocation(){return directionVectorLocation;
    }

    public int fp_startTimeLocation(){
        return startTimeLocation;
    }

}
