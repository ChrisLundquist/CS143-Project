varying vec2 texture_coordinate;
void main()
{	
	gl_Position = ftransform();
	texture_coordinate = vec2(gl_MultiTexCood0);
}
