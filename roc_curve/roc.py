import os
import glob
import argparse
import pandas as pd
import matplotlib.pyplot as plt
from sklearn import metrics

sep = os.sep

def info(path):
    cls = os.path.splitext(os.path.basename(path))[0]
    delim2 = path.find("Prob")-1
    delim1 = delim2 -3
    task = path[delim1:delim2]
    #print(f"cls: {cls}")
    #print(f"task: {task}")
    return cls, task

def info2(path):
    print(path)
    cls = os.path.splitext(os.path.basename(path))[0]
    delim2 = path.find(cls)-1
    delim1 = delim2 -3
    task = path[delim1:delim2]
    #print(f"cls: {cls}")
    #print(f"task: {task}")
    return cls, task

def manage_df(file,cls,task):
    final_df = pd.DataFrame(columns=["Task", "Classifier", "ID", "Label", "Prob", "Pred", "NumOcc"])
    df = pd.read_excel(file)
    max_id=181
    for i in range(1, max_id+1):
        df_id = df[df["ID"] == i].reset_index()
        if not df_id.empty:
            label = df_id.loc[1, "Label"]
            prob =df_id["Prob"].mean()
            #if df_id["Pred"].mean() > 0.5:
            if df_id["Pred"].mean() > pow(10, -5):
                pred = 1
            else:
                pred = 0
            num_occ = df_id.shape[0]
            final_df=final_df.append({"Task": task, "Classifier":cls, "ID":i, "Label":label, "Prob":prob, "Pred":pred, "NumOcc":num_occ}, ignore_index=True)
            #print(df_id)
            #print(f"i = {i}, label= {label}")
    return final_df

def single_roc(file,cls, task):
    lw = 2
    plt.figure()
    df = pd.read_excel(file)
    y = list(df["Label"])
    scores = list(df["Prob"])
    fpr, tpr, thresholds = metrics.roc_curve(y, scores, pos_label=1)
    auc = metrics.auc(fpr, tpr)
    plt.plot(fpr, tpr, color='blue', lw=lw, label=cls + ' (AUC = %0.2f)' % auc)
    plt.plot([0, 1], [0, 1], color='k', lw=lw, linestyle='--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    # plt.title('ROC ' + task)
    plt.legend(loc="lower right")
    return plt

def multiple_roc(file,cls, task):
    lw = 2
    plt.figure()
    df = pd.read_excel(file)
    y = list(df["Label"])
    scores = list(df["Prob"])
    fpr, tpr, thresholds = metrics.roc_curve(y, scores, pos_label=1)
    auc = metrics.auc(fpr, tpr)
    plt.plot(fpr, tpr, color='blue', lw=lw, label=cls + ' (AUC = %0.2f)' % auc)
    plt.plot([0, 1], [0, 1], color='k', lw=lw, linestyle='--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    # plt.title('ROC ' + task)
    plt.legend(loc="lower right")
    return plt


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
                output_folder = output + task + sep
                if not os.path.exists(output_folder):
                    os.mkdir(output_folder)
                img_path = output_folder + "ROC_" + task + '.png'
                plt.savefig(img_path, bbox_inches='tight')
            except:
                print("ERROR")

