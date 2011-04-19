uniform sampler2D texture0;
uniform int numLights;
varying vec3 N;
varying vec3 v;
varying vec2 texCoordinate0;

vec4 effectiveLight()
{
	vec4 color = vec4(0.0f,0.0f,0.0f,0.0f);
	vec3 E = normalize(-v); // we are in Eye Coordinates, so EyePos is (0,0,0)


	for(int i = 0; i < numLights; i++){
		vec3 L = normalize(gl_LightSource[i].position.xyz - v); 
		vec3 R = normalize(-reflect(L,N)); 
		float dist = length(L);

		float atten = 1.0f / (gl_LightSource[i].constantAttenuation +
				gl_LightSource[i].linearAttenuation * dist +
				gl_LightSource[i].quadraticAttenuation * dist * dist);

		//calculate Ambient Term:
		vec4 Iamb = gl_LightSource[i].ambient * gl_FrontMaterial.ambient;

		//calculate Diffuse Term:
		vec4 Idiff = (gl_LightSource[i].diffuse * max(dot(N,L), 0.0f)) * gl_FrontMaterial.diffuse;

		// calculate Specular Term:
		vec4 Ispec = gl_FrontMaterial.specular * pow(max(dot(R,E),0.0f),0.3f * gl_FrontMaterial.shininess);

		color += gl_FrontMaterial.emission+Iamb+atten*(gl_FrontMaterial.ambient + Idiff + Ispec);
	}
	return color;
}

void main (void)
{
	// write Total Color:
	gl_FragColor = effectiveLight()*texture2D(texture0, texCoordinate0);
}

