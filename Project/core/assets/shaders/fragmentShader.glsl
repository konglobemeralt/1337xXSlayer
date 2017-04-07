#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord0;

uniform vec3 u_color;
uniform sampler2D u_texture;

void main() {
    vec4 pink = vec4(0.7, 0.1, 0.7, 1.0);
    vec4 colorTint = vec4(v_texCoord0.x/2.0, v_texCoord0.y/2.0, 0, 1.0);
    colorTint = colorTint * pink;
    gl_FragColor = texture2D(u_texture, v_texCoord0) + colorTint;
    //gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
}