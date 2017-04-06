#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord0;

void main() {
    vec4 colorTint = vec4(0.7, 0.1, 0.6, 1.0);
    gl_FragColor = vec4(v_texCoord0, 0.0, 1.0) + colorTint;
}