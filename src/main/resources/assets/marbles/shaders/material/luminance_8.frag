#include frex:shaders/api/fragment.glsl
#include frex:shaders/lib/math.glsl

void frx_startFragment(inout frx_FragmentData fragData) {
	fragData.emissivity = 8.0 / 15.0;
}
