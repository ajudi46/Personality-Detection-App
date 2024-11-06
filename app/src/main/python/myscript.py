import tweepy
import numpy as np
import pandas as pd
import csv
import random
import pickle
import nltk
import collections
import tensorflow as tf
from nltk import word_tokenize
from nltk.stem import WordNetLemmatizer
from nltk.corpus import stopwords
from sklearn.externals import joblib
from sklearn.pipeline import Pipeline
from sklearn.model_selection import KFold
from sklearn.metrics import confusion_matrix, accuracy_score
from keras.wrappers.scikit_learn import KerasClassifier
from keras.models import Sequential
from keras.models import load_model
from keras.layers import Dense
from keras.layers import LSTM
from keras.layers import Bidirectional
from keras.layers import GRU
from keras.layers import SimpleRNN
from keras.layers.embeddings import Embedding
from keras.preprocessing import sequence
from keras.preprocessing import text
from keras.optimizers import Adam
from keras.models import load_model
from keras.preprocessing import sequence
from keras.preprocessing import text
import re
from os.path import dirname, join


def get_tweets(username):


    nltk.download('stopwords')
    nltk.download('wordnet')

    consumer_key = "WaHoM3IWqaznioUYXpJNUxDI6"
    consumer_secret = "72PbMwIViUZG18eN7iv5nl36iwnFggnXGuqubEFmx6h13TSaCL"
    access_key = "445419766-nB1niNN3jrpGC1VtLn8IHSTLBGXMT2BRkEklKut1"
    access_secret = "uASgCoY9cMMNQnYzuEYzDXeH8fJKGX9yyezyJ4ekqHCUL"


    # Authorization to consumer key and consumer secret
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)

    # Access to user's access key and access secret
    auth.set_access_token(access_key, access_secret)

    # Calling api
    api = tweepy.API(auth)

    # 200 tweets to be extracted

    tweets = api.user_timeline(screen_name=username,count = 100)

    # Empty Array
    tmp=[]
    srno = 1
    # create array of tweet information: username,
    # tweet id, date/time, text
    tweets_for_csv = [tweet.text for tweet in tweets] # CSV file created
    for j in tweets_for_csv:

        # Appending tweets to the empty array tmp
        tmp.append(j)


    stop_words = stopwords.words("english")

    preprocess = []

    for tweet in tmp:
        if (tweet != '') and (tweet != None) and (re.search("[a-zA-Z]", tweet)):
            result = re.sub(r"http\S+", "", tweet)
            preprocess.append(result)

    lemmatized = []
    lemmatizer = WordNetLemmatizer()
    for post in preprocess:
        post = post.replace('RT', '')
        post = re.sub('@[^\s]+','',post)
        temp = post.lower()
        temp = ' '.join([lemmatizer.lemmatize(word) for word in temp.split(' ')	if (word not in stop_words)])
        lemmatized.append(temp)

    DIMENSIONS = ['IE', 'NS', 'FT', 'PJ']

    mbti = ""

    trait_percent = {
        'I' : 0,
        'E' : 0,
        'F' : 0,
        'T' : 0,
        'N' : 0,
        'S' : 0,
        'P' : 0,
        'J' : 0
    }

    MAX_POST_LENGTH = 40

    for trait in DIMENSIONS:
        with open(join(dirname(__file__),'tokenizer_{}.pkl'.format(trait)),'rb') as handle:
            tokenizer = pickle.load(handle)
        model = load_model(join(dirname(__file__),'model_'+trait+'.h5'))
        tokenized = tokenizer.texts_to_sequences(lemmatized)
        test_seq = sequence.pad_sequences(tokenized, maxlen=MAX_POST_LENGTH)
        predictions_for_batch = model.predict_classes(test_seq)
        predictions_for_batch = [item for sublist in predictions_for_batch for item in sublist]
        count_0 = 0
        count_1 = 0
        for i in predictions_for_batch:
            if i==0:
                count_0 = count_0 + 1
            else :
                count_1 = count_1 + 1

        percent_0 = count_0*100/len(predictions_for_batch)
        percent_1 = count_1*100/len(predictions_for_batch)
        trait_percent[trait[0]] = percent_0
        trait_percent[trait[1]] = percent_1
        print(trait[0]+" Chances :",percent_0)
        print(trait[1]+" Chances :",percent_1)
        prediction = collections.Counter(predictions_for_batch).most_common(1)[0][0]
        mbti = mbti + trait[prediction]

    return trait_percent


def get_tweets_bigv(username):

    nltk.download('stopwords')
    nltk.download('wordnet')

    consumer_key = "WaHoM3IWqaznioUYXpJNUxDI6"
    consumer_secret = "72PbMwIViUZG18eN7iv5nl36iwnFggnXGuqubEFmx6h13TSaCL"
    access_key = "445419766-nB1niNN3jrpGC1VtLn8IHSTLBGXMT2BRkEklKut1"
    access_secret = "uASgCoY9cMMNQnYzuEYzDXeH8fJKGX9yyezyJ4ekqHCUL"


    # Authorization to consumer key and consumer secret
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)

    # Access to user's access key and access secret
    auth.set_access_token(access_key, access_secret)

    # Calling api
    api = tweepy.API(auth)

    # 200 tweets to be extracted

    tweets = api.user_timeline(screen_name=username,count = 100)

    # Empty Array
    tmp=[]
    srno = 1
    # create array of tweet information: username,
    # tweet id, date/time, text
    tweets_for_csv = [tweet.text for tweet in tweets] # CSV file created
    for j in tweets_for_csv:

        # Appending tweets to the empty array tmp
        tmp.append(j)


    stop_words = stopwords.words("english")

    preprocess = []

    for tweet in tmp:
        if (tweet != '') and (tweet != None) and (re.search("[a-zA-Z]", tweet)):
            result = re.sub(r"http\S+", "", tweet)
            preprocess.append(result)

    lemmatized = []
    lemmatizer = WordNetLemmatizer()
    for post in preprocess:
        post = post.replace('RT', '')
        post = re.sub('@[^\s]+','',post)
        temp = post.lower()
        temp = ' '.join([lemmatizer.lemmatize(word) for word in temp.split(' ')	if (word not in stop_words)])
        lemmatized.append(temp)

    MAX_POST_LENGTH = 15

    big5 = ['EXT' ,'NEU' , 'AGR' , 'CON' , 'OPN']
    big5trait = {
        'EXT':0.0 ,
        'NEU':0.0,
        'AGR':0.0 ,
        'CON':0.0 ,
        'OPN':0.0
    }

    for trait in big5:
        with open(join(dirname(__file__),'tokenizer_{}.pkl'.format(trait)),'rb') as handle:
            tokenizer = pickle.load(handle)
        model = load_model(join(dirname(__file__),'model_'+trait+'.h5'))
        tokenized = tokenizer.texts_to_sequences(lemmatized)
        test_seq = sequence.pad_sequences(tokenized, maxlen=MAX_POST_LENGTH)
        predictions_for_batch = model.predict(test_seq)
        big5trait[trait] = np.mean(predictions_for_batch)

    return big5trait





if __name__ == '__main__':
    print ("NOT FOUND")