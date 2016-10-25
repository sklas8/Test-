precision mediump float;
uniform sampler2D u_Texture;

varying vec2 v_TextureCoordinate;

void main() {
    vec4 intermed = texture2D(u_Texture, v_TextureCoordinate);
    intermed.a = 0.7;
    gl_FragColor = intermed;
}
