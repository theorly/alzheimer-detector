import os
import glob
import argparse
import pandas as pd
import matplotlib.pyplot as plt
from sklearn import metrics

sep = os.sep

if __name__ == "__main__":
    a=argparse.ArgumentParser()

    a.add_argument("--input", default="input_files")
    a.add_argument("--output", default="output_files")

    args=a.parse_args()

    input_folder = args.input
    output = args.output

    input = input_folder + sep

    if not os.path.exists(input):
        os.mkdir(input)

    output_folder = output + sep

    if not os.path.exists(output_folder):
        os.mkdir(output_folder)

    tasks = ["T01", "T08", "T09", "T10"]
    for task in tasks:
        input_folder = input + task + sep
        files = os.listdir(input_folder)
        print(files)

        colors = ["blue", "red", "yellow", "green"]

        lw = 2
        plt.figure()

        for i in range(len(files)):
            try:
                print(input_folder + files[i])
                cls = os.path.splitext(files[i])[0]
                df = pd.read_excel(input_folder + files[i])
                y = list(df["Label"])
                scores = list(df["Prob"])
                fpr, tpr, thresholds = metrics.roc_curve(y, scores, pos_label=1)
                auc = metrics.auc(fpr, tpr)
                plt.plot(fpr, tpr, color=colors[i], lw=lw, label=cls + ' (AUC = %0.2f)' % auc)
                plt.plot([0, 1], [0, 1], color='k', lw=lw, linestyle='--')
                plt.xlim([0.0, 1.0])
                plt.ylim([0.0, 1.05])
                plt.xlabel('False Positive Rate')
                plt.ylabel('True Positive Rate')
                # plt.title('ROC ' + task)
                plt.legend(loc="lower right")
                # print(cls)
                output_folder = output + sep + task + sep
                if not os.path.exists(output_folder):
                    os.mkdir(output_folder)
                img_path = output_folder + "ROC_" + task + '_INC.png'
                plt.savefig(img_path, bbox_inches='tight')
            except:
                print("ERROR")

