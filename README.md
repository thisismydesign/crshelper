# CRSHelper

Utility to support Catmull-Rom Spline operations with libGDX framework.

Main features:
- Fixed-precision spline iterator (as explained in my blog post: http://lifeinacubicleblog.com/2016/10/09/libgdx-iterating-a-catmull-rom-spline-by-fixed-units/)
- Smooth, animated transition of splines
- Finding the intersection of a Line (n points) and a Spline (m points) in O(n*m) time
- Finding the intersection of a Line and a Spline in O(n*log m) time if the spline in continuous on the X axis
- Random Spline generation with adjustable complexity and flatness
- Finding Spline position at a certain ratio of the Spline's length
- Finding distance of points on the Spline curve

The demo package contains an app showcasing these features.

## Usage

To run the demo use the following gradle job:
demo:desktop:run
Be aware that the demo:core package is dependent on a built version of crshelper and looks for it in the default directory: "jar://$MODULE_DIR$/../../crshelper/build/libs/crshelper-1.0.jar"
This has the side effect that you must re-build the crshelper project if you've modified it in order for modifications to take effect in the demo.

To build the crshelper project use the following gradle job:
crshelper:build

## Feedback

Any feedback is much appreciated.

I can only tailor this project to fit use-cases I know about - which are usually my own ones. If you find that this might be the right direction to solve your problem too but you find that it's suboptimal or lacks features don't hesitate to contact me.

Please let me know if you make use of this project so that I can prioritize further efforts.

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/thisismydesign/crshelper. This project is intended to be a safe, welcoming space for collaboration, and contributors are expected to adhere to the [Contributor Covenant](http://contributor-covenant.org) code of conduct.


## License

The gem is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).
