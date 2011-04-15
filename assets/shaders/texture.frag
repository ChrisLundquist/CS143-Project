
uniform sampler2D texture0;
varying vec2 texCoordinate0;

void main()
{
    gl_FragColor = texture2D(texture0,texCoordinate0);
}
