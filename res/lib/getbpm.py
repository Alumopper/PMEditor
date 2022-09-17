import librosa
import numpy as np
import sys
import warnings


def getbpm(filename):
    warnings.filterwarnings("ignore")
    yy, sr = librosa.load(filename)
    onset_env = librosa.onset.onset_strength(yy, sr=sr, hop_length=512, aggregate=np.median)
    tempo, _ = librosa.beat.beat_track(onset_envelope=onset_env, sr=sr)
    return tempo


try:
    if __name__ == '__main__':
        print(getbpm(sys.argv[1]))
except Exception as e:
    print("Python Exception", e)
