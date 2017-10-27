convert -delay 4 -loop 0 ./out/gso-*.png ./preview/animation.gif
gifsicle -O3 --delay=4 --colors=20 --dither --loop ./preview/animation.gif > ./preview/animation-optim.gif

# create icon
for SIZE in 16 20 32 40 44 64 128 256 512; do
    sips -z $SIZE $SIZE ./icon.png --out ./res/icons/icon_${SIZE}x${SIZE}.png ;
done