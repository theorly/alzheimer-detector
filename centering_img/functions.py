import cv2
import numpy as np
from PIL import Image
import os

sep = os.sep

# FUNZIONI PER IL CALCOLO DEL MASSIMO E DEL MINIMO
def minimum(vector, num_coord):
    min = vector[0]
    min_coord = 0
    for i in range(num_coord):
        if vector[i] < min:
            min = vector[i]
            min_coord = i
    return (min_coord)


def maximum(vector, num_coord):
    max = vector[0]
    max_coord = 0
    for i in range(num_coord):
        if vector[i] > max:
            max = vector[i]
            max_coord = i
    return (max_coord)

def grayscale(file):
    img = Image.open(file).convert('L')
    #img.show()
    return img


def coordinates(img):
    w, h = img.size
    pix_val = list(img.getdata())
    array = np.asarray(img)
    lists = array.tolist()
    black_pixels=[]
    x_coord = []
    y_coord = []
    for y in range(len(lists)):
        for x in range(len(lists)):
            if lists[y][x]!=255:
                black_pixels.append([x,y])
                x_coord.append(x)
                y_coord.append(y)
    if len(black_pixels) >0:
        xmax_coord = maximum(x_coord, len(x_coord))
        ymax_coord = maximum(y_coord, len(x_coord))
        xmin_coord = minimum(x_coord, len(x_coord))
        ymin_coord = minimum(y_coord, len(x_coord))

        xmax= black_pixels[xmax_coord]
        ymax = black_pixels[ymax_coord]
        xmin = black_pixels[xmin_coord]
        ymin = black_pixels[ymin_coord]

        return(xmax,ymax,xmin, ymin)
    else:
        return(None,None,None,None)

def roi(img):
    xmax,ymax,xmin,ymin = coordinates(img)
    if xmax != None and ymax != None and xmin != None and ymin != None:
        alt_sx = [xmin[0], ymin[1]]
        w = xmax[0]- xmin[0]
        h = ymax[1] - ymin[1]
        return(alt_sx, w, h)
    else:
        return(None, None, None)

def resize(img):
    width, height = img.size
    if width >height:
        new_width = 224
        new_height = int(new_width * height / width)
    else:
        new_height = 224
        new_width = int(new_height * width / height)

    img = img.resize((new_width, new_height), Image.ANTIALIAS)

    return img

def center(img):
    w,h = img.size

    # load background image as grayscale
    # white = Image.new(mode="L", size=(299, 299), color=255)
    white = Image.new(mode="L", size=(224, 224), color=255)
    ww, hh = white.size

    # compute xoff and yoff for placement of upper left corner of resized image
    yoff = round((hh - h) / 2)
    xoff = round((ww - w) / 2)
    off = [xoff, yoff]

    white.paste(img, off)
    rgbimg = Image.new("RGB", white.size)
    rgbimg.paste(white)
    #rgbimg.show()

    return rgbimg


def create_path(output, file):
    path, filename = os.path.split(file)
    path2, sub = os.path.split(path)
    path3, task = os.path.split(path2)

    new_path = output + sep + task + sep + sub + sep

    if not os.path.exists(new_path):
        os.makedirs(new_path)

    return new_path + filename

























