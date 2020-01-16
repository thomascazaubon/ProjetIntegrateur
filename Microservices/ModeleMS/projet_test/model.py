#!/usr/bin/env python
import fastai
from fastai import *
from fastai.vision import *
#from fastai.callbacks.hooks import *
import pandas as pd
import pickle
import argparse
import pathlib
from pathlib import Path, PurePosixPath
import os
from PIL import Image
#import cPickle

def main():

    parser = argparse.ArgumentParser()
    parser.add_argument("-a", "--action", dest="action", default=None, type=str, help="Action to be processed by the model.py")
    parser.add_argument("-i", "--inputFiles", dest="dataSet", default=None, type=str, help="The name of the directory.")

    arguments = parser.parse_args()
    # Action to be processes by the model.py
    action = arguments.action
    # String name of the directory where the right images are
    dataSet = arguments.dataSet

    if action == "predictOne":
        # Read model
        #loaded_object = pickle.load(open('projet_test/model_3.pkl', 'rb'))
        #model_3 = pd.read_pickle("projet_test/model_3.pkl", "infer")
        #model_3 = pickle.load('projet_test/model_3.pkl')
        os.path.join("./projet_test", "model_3.pkl")
        #path = Path("./projet_test")
        with open(r"projet_test/model_3.pkl", "rb") as input_file:
        	model_3 = pickle.load(input_file)

        # image
        testImageList = ImageList.from_folder("projet_test/" + dataSet)
        image = open_image(testImageList.items[0])

        #image = Image.open('projet_test/dataSet/imgTest.jpg')

        # prediction
        className, label, probability = model_3.predict(image)
        print(className)

        # TO BE DONE
    elif action == "predictAll":
        # read model
        model_3 = pd.read_pickle("projet_test/model_3.pkl", "infer")
        #print(model_3)

        # image
        testImageList = ImageList.from_folder("projet_test/dataSet")
        image = open_image(testImageList.items[0])

        # prediction
        className, label, probability = model_3.predict(image)
        print(className)

    elif action == 'train':
        # train model
        #learn = cnn_learner(data, models.resnet34, metrics=accuracy, model_dir='/tmp/model/')
        # if you don't run learn.unfreeze() before learn.fit_one_cycle(1) just train fully connect layers,
        # the convolutional framework has been pre-trained and freeze
        # learn.unfreeze()
        learn.lr_find()
        learn.recorder.plot()
        learn.fit_one_cycle(2)

        # save the model, stage_3.pth and export.pkl in F:/DATASET/stage_3
        learn.model_dir = "projet_test"
        learn.save('stage_3', return_path=True)
        learn.path = Path('projet_test/stage_3')
        learn.export()
        model_3 = load_learner(learn.path)

        # store model in format pickle
        #pickle_out = open('F:\DATASET\stage_3\model_3.pkl', 'wb')
        pickle.dump(model_3, open('stage_3.pkl', 'wb'))
        pickle_out.close()


if __name__ == "__main__":
    main()
