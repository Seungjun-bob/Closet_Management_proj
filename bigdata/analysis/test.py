import pandas as pd
from matplotlib import pyplot as plt

Clothes = pd.read_csv('../dummydata/dummyClothes.csv', encoding='Utf-8', index_col=0)
MyClothes = pd.read_csv('../dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
UserData = pd.read_csv('../dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
user_id = 'dummy1'
dummy = df[df['ID'] == user_id]
Clothes['MainCategory'] = ['bottom'
                           if s == 'jean'
                              or s == 'cotton'
                              or s == 'jogger'
                              or s == 'slacks'
                              or s == 'skirt'
                              or s == 'longPants'
                              or s == 'shortPants'
                           else 'top'
                           for s in Clothes['category']]
dummy['MainCategory'] = ['bottom'
                         if s == 'jean'
                            or s == 'cotton'
                            or s == 'jogger'
                            or s == 'slacks'
                            or s == 'skirt'
                            or s == 'longPants'
                            or s == 'shortPants'
                         else 'top'
                            for s in dummy['myCategory']]
top_dummy = dummy[dummy['MainCategory'] == 'top']
dummy_top_have = top_dummy['myCategory'].value_counts()
print(dummy_top_have)
bottom_dummy = dummy[dummy['MainCategory'] == 'bottom']
dummy_bottom_have = bottom_dummy['myCategory'].value_counts()
print(dummy_bottom_have)