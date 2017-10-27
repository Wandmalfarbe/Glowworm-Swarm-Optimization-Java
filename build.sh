convert -delay 4 -loop 0 ./out/gso-*.png ./preview/animation.gif
gifsicle -O3 --delay=4 --colors=20 --dither --loop ./preview/animation.gif > ./preview/animation-optim.gif