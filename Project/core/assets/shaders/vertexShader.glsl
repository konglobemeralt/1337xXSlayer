attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

attribute vec2 a_boneWeight0;
attribute vec2 a_boneWeight1;
attribute vec2 a_boneWeight2;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;
uniform mat4 u_bones[3];

varying vec2 v_texCoord0;

void main() {
    v_texCoord0 = a_texCoord0;

    mat4 skinning = mat4(0.0);

    skinning += (a_boneWeight0.y) * u_bones[int(a_boneWeight0.x)];
    skinning += (a_boneWeight1.y) * u_bones[int(a_boneWeight1.x)];
    skinning += (a_boneWeight2.y) * u_bones[int(a_boneWeight2.x)];

    vec4 pos = u_worldTrans * skinning * vec4(a_position, 1.0);
    gl_Position = u_projViewTrans * pos;
}