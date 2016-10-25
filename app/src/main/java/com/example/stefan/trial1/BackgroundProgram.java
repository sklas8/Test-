package com.example.stefan.trial1;

import android.content.Context;
import android.opengl.GLES20;

import Util.ShaderHelper;
import Util.TextResourceReader;

/**
 * Created by Stefan on 9/26/2016.
 */
public class BackgroundProgram implements Program {

    private Context context;
    private int programID;
    private int positionLocation;
    private int matrixLocation;
    private int u_TextureLocation;
    private int a_TextureCoordinateLocation;
    private int textureHolder;
    private SomeObject object;

    public BackgroundProgram(Context context){
        this.context = context;
        object = new SomeObject();
    }

    @Override
    public int setUp() {
        String vertex_input = TextResourceReader.readTextFileFromResource(context, R.raw.vertex_shader);
        String fragment_input = TextResourceReader.readTextFileFromResource(context, R.raw.fragment_shader);

        int vertexID = ShaderHelper.compileVertexShader(vertex_input);
        int fragmentID = ShaderHelper.compileFragmentShader(fragment_input);

        programID = ShaderHelper.linkProgram(vertexID, fragmentID);

        // Get attribute/uniform Locations
        positionLocation = GLES20.glGetAttribLocation(programID, "a_Position");
        matrixLocation = GLES20.glGetUniformLocation(programID, "u_Matrix");
        u_TextureLocation = GLES20.glGetUniformLocation(programID, "u_Texture");
        a_TextureCoordinateLocation = GLES20.glGetAttribLocation(programID, "a_TextureCoordinate");

        textureHolder = ShaderHelper.loadTexture(context, R.drawable.picture);
        return programID;
    }

    public void setUniforms(float[] matrix) {
        GLES20.glUniformMatrix4fv(matrixLocation, 1, false, matrix, 0);
    }

    public void draw(){
        //This is needed for the opacity
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_CONSTANT_ALPHA);

        //We set the active texture to be unit 0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Bind will automatically bind the texture to the unit
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHolder);
        // Then what is held in unit 0 will be transferred to the uniform
        GLES20.glUniform1i(u_TextureLocation, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        GLES20.glDisable(GLES20.GL_BLEND);
    }

    public void bindData(){
        ShaderHelper.setVertexAttribPointer(object.getData(), 0, positionLocation, 3, 5 * 4);
        ShaderHelper.setVertexAttribPointer(object.getData(), 3, a_TextureCoordinateLocation, 2, 5 * 4);
    }

    public void useProgram(){
        GLES20.glUseProgram(programID);
    }

    public int bp_positionLocation(){
        return positionLocation;
    }

    public int bp_matrixLocation(){
        return matrixLocation;
    }

    public int bp_u_TextureLocation(){
        return u_TextureLocation;
    }

    public int bp_a_TextureCoordinateLocation(){
        return a_TextureCoordinateLocation;
    }
}
