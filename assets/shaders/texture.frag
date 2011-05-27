
uniform sampler2D texture0;
varying vec2 texCoordinate0;

void main() {
    gl_FragColor = texture2D(texture0,texCoordinate0) + (1.0f - gl_Color.a) * gl_Color;
}
