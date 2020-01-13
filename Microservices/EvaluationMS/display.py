import argparse
from os import listdir, path
import json
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import confusion_matrix
import pandas as pd
import seaborn as sn

def main():
    """
    Generate a png corresponding to the results file given as argument.
    Return the filename of the generated png file.
    """

    parser = argparse.ArgumentParser()

    # Get all json files in the current directory
    files = [f for f in listdir(".") if f.endswith(".json")]
    # Get the recenter json file of the current repository
    last = sorted(files, key=path.getmtime)[-1]

    #Arguments : -file file
    parser.add_argument("-f", "--file", dest="file", default=last, type=str, help="Results file to read")
    args = parser.parse_args()
    file = args.file

    # Read the json file
    with open(file) as json_file:
        data = json.load(json_file)

    # Filename of the png file
    png = path.splitext(path.basename(file))[0]+".png"

    # Creation of the plot
    cm = confusion_matrix(data["true_classes"], data["predicted_classes"])
    # Get true negatives, false positives, false negatives and true positives
    tn, fp, fn, tp = cm.ravel()
    # Compute precision, sensibility to Recyclable and sensibility to Organic
    precision = (tp + tn) / (tn + fp + fn + fp)
    sensibilityR = tp / (tp+fn)
    sensibilityO = tn / (tn+fp)

    plt.figure(figsize = (16,9))
    df_cm = pd.DataFrame(cm, columns=np.unique(data["true_classes"]), index = np.unique(data["true_classes"]))
    df_cm.index.name = 'Valeur réelle'
    df_cm.columns.name = 'Valeur prédite'
    # plt.suptitle(data['algorithm'] + " " + data['parameters'], size=24)
    plt.title(f"precision = {precision}\n O sensibility = {sensibilityO}\n R sensibility = {sensibilityR}", size=16)
    sn.heatmap(df_cm, cmap="Blues", annot=True)
    # Save the plot as a png file
    plt.savefig(png)
    plt.close()

    # Return the png filename
    print(png)

if __name__ == "__main__":
    # execute only if run as a script
    main()
