varying vec2 texCoordinate0;
uniform bool isTextured;
void main() {	
	gl_Position = ftransform();
	if(isTextured)
		texCoordinate0 = vec2(gl_MultiTexCoord0);
	gl_FrontColor = gl_Color;
}
