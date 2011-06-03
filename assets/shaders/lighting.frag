uniform sampler2D texture0;
uniform int numLights;
uniform bool isTextured;
varying vec3 N;
varying vec3 v;
varying vec2 texCoordinate0;

void main (void) {
    // Start with the color of the texture
    if(isTextured)
        gl_FragColor = texture2D(texture0,texCoordinate0) + (1.0 - gl_Color.a) * gl_Color;
    else
        gl_FragColor = (1.0 - gl_Color.a) * gl_Color;

    vec4 effectiveLight = vec4(0.0,0.0,0.0,0.0);
    for(int i = 0; i < numLights; i++) {
        vec3 L = normalize(gl_LightSource[i].position.xyz - v); 
        vec3 E = normalize(-v); // we are in Eye Coordinates, so EyePos is (0,0,0)
        vec3 R = normalize(-reflect(L,N)); 

        //calculate Ambient Term:
        vec4 Iamb = gl_FrontLightProduct[i].ambient;

        //calculate Diffuse Term:
        vec4 Idiff = gl_FrontLightProduct[i].diffuse * max(dot(N,L), 0.0);

        // calculate Specular Term:
        vec4 Ispec = gl_FrontLightProduct[i].specular * pow(max(dot(R,E),0.0),0.3*gl_FrontMaterial.shininess);
        // write Total Color:
        effectiveLight += gl_FrontLightModelProduct.sceneColor + Iamb + Idiff + Ispec; 
    }
    gl_FragColor *= effectiveLight;
}

