uniform mat4 u_Matrix;
uniform float u_CurrentTime;

//attribute vec3 a_Color;
attribute vec2 a_Position;
//attribute vec2 a_DirectionVector;
//attribute float a_StartTime;

//varying vec3 v_Color;

void main() {
    //v_Color = a_Color;
    //float v_ElapsedTime = u_CurrentTime - a_StartTime;
    //vec2 currentPosition = a_Position + (a_DirectionVector * v_ElapsedTime);
    //gl_Position = u_Matrix * vec4(0.4f,0.5f, -0.7f, 1f);
    gl_Position = vec4(1.0,0.5,0.6,1.0);
    gl_PointSize = 5.0;
}
