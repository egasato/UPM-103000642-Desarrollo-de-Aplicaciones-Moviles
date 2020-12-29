#!/usr/bin/env sh

# Example usage:
# ./generate.sh <image> <resolution> <output-directory>
#
# Will generate <resolution>-dp android drawables for 6 DPI on <output-directory>/<image>/ directory.
# Be sure your original image has sustainable resolution for xxxhdpi drawable.
#
# Requires ImageMagic
# SVG conversion recommended to be done with Inkscape:
# https://inkscape.org/en/

# Parse the arguments
IMAGE=$(basename "$1")
IMAGENAME="${IMAGE%.*}"
IMAGEEXT="${IMAGE##*.}"
DPSIZE=$2

# Sanitize the arguments
if [ ! -d "$3" ] ; then
	ASSETDIR="out/${IMAGENAME}/mipmap-"
	echo "Directory '$3' not exists. Using ${ASSETDIR}<\*dpi> for image output"
else 
	ASSETDIR="$3/mipmap-"
fi 

# Hardcoded macOS Inkscape executable path
OSX_INKSCAPE_PATH="/Applications/Inkscape.app/Contents/Resources/bin/inkscape"

# Inkscape variables
USE_INKSCAPE=0
INKSCAPE_PATH=""

# Guess Inkscape's path if the image is an SVG
if test "$IMAGEEXT" = 'svg'; then
	unamestr="$(uname)"
	if test "$unamestr" = 'Darwin'; then
		INKSCAPE_PATH=$OSX_INKSCAPE_PATH
	else
		INKSCAPE_PATH=$(which inkscape)
	fi
	if test ! -x "$INKSCAPE_PATH"; then
		echo "\x1B[93mWARNING:\x1B[39m Inkskape is not in PATH"
	else
		USE_INKSCAPE=1
	fi
fi

# Check ImageMagick support
if test ! -x "$(command -v convert)"; then
	echo "\x1B[1;31mERROR:\x1B[39m ImageMagic convert is not in PATH while required for this script"
	exit 2
fi

# Compute the sizes for the different DPIs
SIZE_ldpi=$(echo "$DPSIZE * 0.75" | bc -l | cut -d '.' -f 1)
SIZE_mdpi=$DPSIZE
SIZE_hdpi=$(echo "$DPSIZE * 1.5" | bc -l | cut -d '.' -f 1)
SIZE_xhdpi=$((DPSIZE * 2))
SIZE_xxhdpi=$((DPSIZE * 3))
SIZE_xxxhdpi=$((DPSIZE * 4))

# Create the directories
mkdir -p "${ASSETDIR}ldpi"
mkdir -p "${ASSETDIR}mdpi"
mkdir -p "${ASSETDIR}hdpi"
mkdir -p "${ASSETDIR}xhdpi"
mkdir -p "${ASSETDIR}xxhdpi"
mkdir -p "${ASSETDIR}xxxhdpi"

# Create the drawables using an SVG and Inkscape
if test $USE_INKSCAPE = 1; then
	echo "Using Inkscape to convert SVG"
	$INKSCAPE_PATH  --batch-process -o "$(pwd)/${ASSETDIR}ldpi/$IMAGENAME.png"    -w "$SIZE_ldpi"    "$(pwd)/$IMAGE"
	$INKSCAPE_PATH  --batch-process -o "$(pwd)/${ASSETDIR}mdpi/$IMAGENAME.png"    -w "$SIZE_mdpi"    "$(pwd)/$IMAGE"
	$INKSCAPE_PATH  --batch-process -o "$(pwd)/${ASSETDIR}hdpi/$IMAGENAME.png"    -w "$SIZE_hdpi"    "$(pwd)/$IMAGE"
	$INKSCAPE_PATH  --batch-process -o "$(pwd)/${ASSETDIR}xhdpi/$IMAGENAME.png"   -w "$SIZE_xhdpi"   "$(pwd)/$IMAGE"
	$INKSCAPE_PATH  --batch-process -o "$(pwd)/${ASSETDIR}xxhdpi/$IMAGENAME.png"  -w "$SIZE_xxhdpi"  "$(pwd)/$IMAGE"
	$INKSCAPE_PATH  --batch-process -o "$(pwd)/${ASSETDIR}xxxhdpi/$IMAGENAME.png" -w "$SIZE_xxxhdpi" "$(pwd)/$IMAGE"
else 
	echo "Using ImageMagick to convert image"
	convert "$IMAGE" \
		\( +clone -resize "$SIZE_ldpi"    -write "${ASSETDIR}ldpi/$IMAGENAME.png"    +delete \) \
		\( +clone -resize "$SIZE_mdpi"    -write "${ASSETDIR}mdpi/$IMAGENAME.png"    +delete \) \
		\( +clone -resize "$SIZE_hdpi"    -write "${ASSETDIR}hdpi/$IMAGENAME.png"    +delete \) \
		\( +clone -resize "$SIZE_xhdpi"   -write "${ASSETDIR}xhdpi/$IMAGENAME.png"   +delete \) \
		\( +clone -resize "$SIZE_xxhdpi"  -write "${ASSETDIR}xxhdpi/$IMAGENAME.png"  +delete \) \
		\( +clone -resize "$SIZE_xxxhdpi" -write "${ASSETDIR}xxxhdpi/$IMAGENAME.png" +delete \)
fi

# Print the exit status
echo "\x1B[32mDrawables for $IMAGENAME were successfully created for $DPSIZE DP and can be found in $ASSETDIR<\*dpi> directory \x1B[39m"
