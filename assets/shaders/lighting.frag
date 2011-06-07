#version 120
uniform bool isTextured;
uniform bool lightingEnabled;
uniform int numLights;
uniform sampler2D texture0;
varying vec3 normal;
varying vec3 vertex;
varying vec2 texCoordinate0;

void main (void) {
    // Start with the color of the texture
    if(isTextured)
        gl_FragColor = texture2D(texture0,texCoordinate0) + gl_Color;
    else
        gl_FragColor = gl_Color;

    if(lightingEnabled){
        vec4 effectiveLight = gl_FrontLightModelProduct.sceneColor; // gl_FrontMaterial.emission + gl_FrontMaterial.ambient * gl_LightModel.ambient
        
        for(int i = 0; i < numLights; i++) {
            //calculate Ambient Term:
            vec4 Iamb = gl_FrontLightProduct[i].ambient;
            
            //calculate Diffuse Term:
            vec3 L = gl_LightSource[i].position.xyz - vertex; 
            float dist = length(L);
            
            L = normalize(L);

            float atten = 1.0 / (gl_LightSource[i].constantAttenuation +
                    gl_LightSource[i].linearAttenuation * dist +
                    gl_LightSource[i].quadraticAttenuation * dist * dist);
            
            vec4 Idiff = gl_FrontLightProduct[i].diffuse * max(dot(normal, L), 0.0);

            vec3 E = normalize(vertex); // we are in Eye Coordinates, so EyePos is (0,0,0)
            vec3 R = normalize(-reflect(L, normal)); 

            // calculate Specular Term:
            vec4 Ispec = gl_FrontLightProduct[i].specular * pow(max(dot(R, E),0.0),0.3*gl_FrontMaterial.shininess);
            
            // Specular is still broken, not translating something right so it appears on the lim of the object and moves as we move
            Ispec = vec4(0,0,0,0);
            
            // write Total Color:
            effectiveLight += atten * (Iamb + Idiff + Ispec); 
        }
        
        gl_FragColor *= effectiveLight;
    }
}

