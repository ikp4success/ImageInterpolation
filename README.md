# ImageInterpolation
Generate Cubic Spline from one to two dimensions &amp; Bi Cubic Interpolation algorithm

When Displaying low resolution images on high resolution monitos, for example watching standard definition TV programs on high definition TV sets, the viewers or players have to interpolate the missing pixels as illustrated byt the following figure. In the figure, the yellow dots are the pixels of the resolution image and the white dots are the missing pixels to be interpolated.

A) The Bi Cubic Interpolation algorithm that is described by the following formular
E denotes summation
F(X,Y) = E^3(i=0)E^3(j=0) ai,x^iy^i
wheere ai,j are the coefficients of the bi cubic interpolation function. The two dimensional
function ( , ) interpolates the lower resolution pixels, and it is used to generate the
missing pixels.

(B) Generalize cubic splines from one dimension to two dimensions for the application of image interpolation.
(C) Implement the image interpolation algorithms derived in (a) and (b) (either in C or in Matlab, or Maple).
Your programs need to read an image, perform image interpolation, and then display the interpolated image.
