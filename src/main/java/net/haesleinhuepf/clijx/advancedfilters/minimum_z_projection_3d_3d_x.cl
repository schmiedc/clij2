
__kernel void minimum_z_projection_3d_3d(
    IMAGE_dst_min_TYPE dst_min,
    IMAGE_src_TYPE src
) {
  const sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP_TO_EDGE | CLK_FILTER_NEAREST;

  const int x = get_global_id(0);
  const int y = get_global_id(1);
  float min = 0;
  for(int z = 0; z < GET_IMAGE_DEPTH(src); z++)
  {
    float value = READ_src_IMAGE(src,sampler,(int4)(x,y,z,0)).x;
    if (value < min || z == 0) {
      min = value;
    }
  }
  WRITE_dst_min_IMAGE(dst_min,(int4)(x,y,0,0), CONVERT_dst_min_PIXEL_TYPE(min));
}