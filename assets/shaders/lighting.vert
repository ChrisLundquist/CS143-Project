#version 120
uniform bool lightingEnabled;
uniform bool isTextured;
varying vec3 normal;
varying vec3 vertex;
varying vec2 texCoordinate0;

void main(void) {
    vertex = gl_Vertex.xyz;
    normal = normalize(gl_NormalMatrix * gl_Normal);
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    if(isTextured)
        texCoordinate0 = vec2(gl_MultiTexCoord0);
        
    gl_FrontColor = gl_Color;
}

