import os
import glob
import argparse
import cv2
import functions
from PIL import Image

if __name__ == "__main__":
    a = argparse.ArgumentParser()

    a.add_argument("--input", default="input/TASK_10_offline/")
    a.add_argument("--output", default="output_224/")

    args = a.parse_args()

    input = args.input
    output = args.output

    sep = os.sep

    max_x = []
    max_y = []
    min_x = []
    min_y = []

    for paths, subdir, files in os.walk(input):
        for file in glob.glob(os.path.join(paths, "*.png")):
            print(file)

            img = functions.grayscale(file)
            alt_sx, w, h = functions.roi(img)
            if alt_sx != None and w != None and h != None:
                cropped= img.crop((alt_sx[0], alt_sx[1], alt_sx[0]+w,  alt_sx[1] + h))
                resized = functions.resize(cropped)
                centered = functions.center(resized)

                path= functions.create_path(output, file)
                centered.save(path)
            else:
                white_im = Image.open(file)
                copied = img.copy()
                path = functions.create_path(output, file)
                white_im.save(path)





