varying vec3 N;
varying vec3 v;
varying vec2 texCoordinate0;
void main(void)
{
   v = vec3(gl_ModelViewMatrix * gl_Vertex);

   N = normalize(gl_NormalMatrix * gl_Normal);
   gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
   texCoordinate0 = vec2(gl_MultiTexCoord0);
}

