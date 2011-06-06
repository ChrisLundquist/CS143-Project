
uniform sampler2D texture0;
varying vec2 texCoordinate0;
uniform bool isTextured;

void main() {
 if(isTextured)
        gl_FragColor = texture2D(texture0,texCoordinate0) + gl_Color;
    else
        gl_FragColor = gl_Color;
}
