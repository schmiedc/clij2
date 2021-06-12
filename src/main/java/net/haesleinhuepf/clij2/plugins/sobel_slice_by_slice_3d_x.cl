__constant sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP_TO_EDGE | CLK_FILTER_NEAREST;

__kernel void sobel_slice_by_slice_3d
(
  IMAGE_dst_TYPE dst,
  IMAGE_src_TYPE src
)
{
  const int x = get_global_id(0);
  const int y = get_global_id(1);
  const int z = get_global_id(2);

  const int4 pos = (int4){x,y,z,0};

  float valueCenter = READ_src_IMAGE(src, sampler, pos).x;

  float valueRight = READ_src_IMAGE(src, sampler, (pos + (int4){1, 0,0,0})).x;
  float valueLeft = READ_src_IMAGE(src, sampler, (pos + (int4){-1, 0,0,0})).x;
  float valueBottom = READ_src_IMAGE(src, sampler, (pos + (int4){0, 1,0,0})).x;
  float valueTop = READ_src_IMAGE(src, sampler, (pos + (int4){0, -1,0,0})).x;

  float valueTopLeft = READ_src_IMAGE(src, sampler, (pos + (int4){-1, -1,0,0})).x;
  float valueTopRight = READ_src_IMAGE(src, sampler, (pos + (int4){1, -1,0,0})).x;
  float valueBottomLeft = READ_src_IMAGE(src, sampler, (pos + (int4){-1, 1,0,0})).x;
  float valueBottomRight = READ_src_IMAGE(src, sampler, (pos + (int4){1, 1,0,0})).x;

  float result_x = valueTopLeft * -1.0 +
                 valueLeft * -2.0 +
                 valueBottomLeft * -1.0 +
                 valueTopRight * 1.0 +
                 valueRight * 2.0 +
                 valueBottomRight * 1.0;

  float result_y = valueTopLeft * -1.0 +
                 valueTop * -2.0 +
                 valueTopRight * -1.0 +
                 valueBottomLeft * 1.0 +
                 valueBottom * 2.0 +
                 valueBottomRight * 1.0;

  float result = sqrt(result_x * result_x + result_y * result_y);
  WRITE_dst_IMAGE (dst, pos, CONVERT_dst_PIXEL_TYPE(result));
}
