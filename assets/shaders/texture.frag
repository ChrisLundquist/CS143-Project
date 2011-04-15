varying vec2 texture_coordinate
uniform sampler2D texture0;
void main()
{
    gl_FragColor = texture2D(texture0,texture_coordinate);
}
