varying vec2 texCoordinate0;
void main()
{	
	gl_Position = ftransform();
	texCoordinate0 = vec2(gl_MultiTexCoord0);
}
