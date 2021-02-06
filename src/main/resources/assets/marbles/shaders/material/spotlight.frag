#include frex:shaders/api/fragment.glsl
#include frex:shaders/lib/math.glsl

void frx_startFragment(inout frx_FragmentData fragData) {
	vec3 rgb = fragData.spriteColor.rgb;
	if (rgb.r > 0.6 && rgb.g > 0.6 && rgb.b > 0.6) {
		fragData.emissivity = 3.0;
		fragData.diffuse = false;
		fragData.ao = false;
	}
}
