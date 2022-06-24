import pandas as pd
import numpy as np

userid = '77'
MyClothes = pd.read_csv('../dummydata/MyClothes.csv', encoding='Utf-8', index_col=0)
MyClothes['userid'] = MyClothes['userid'].apply(str)
MyClothes['myclothesid'] = MyClothes['myclothesid'].apply(str)
df = MyClothes[MyClothes['userid'] == userid]
df = df.groupby('mycategory').count().loc[:, 'userid']
df = df.to_frame()
form = pd.DataFrame(
 {"count":(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},
 index={'long_sleeve_dress', 'long_sleeve_outer', 'long_sleeve_top', 'short_sleeve_dress', 'short_sleeve_outer',
        'short_sleeve_top', 'shorts', 'skirt', 'sling', 'sling_dress', 'trousers', 'vest', 'vest_dress'})
df = pd.merge(form,df, left_index=True, right_index=True, how='left')
df = df.loc[:, 'userid'].sort_index()
df = df.fillna(0)
df = df.astype('int')
print(df)
print('-----------------------')
df = df.reset_index().values.tolist()
category = []
n = []
k = 1
for i in df:
    for j in i:
        if k % 2 == 0:
            n.append(j)
            k += 1
        else:
            category.append(j)
            k += 1
context = {
    'category': category,
    'n': n
}
print(context)



df2 = MyClothes[MyClothes['userid'] == userid]
df2 = df2.groupby('mycolor').count().loc[:, 'userid']
df2 = df2.to_frame()
form2 = pd.DataFrame(
 {"count":(0, 0, 0, 0, 0, 0, 0, 0)},
 index={'black', 'blue', 'red', 'green', 'white', 'pattern', 'gray', 'beige'})
df2 = pd.merge(form2, df2, left_index=True, right_index=True, how='left')
df2 = df2.loc[:, 'userid'].sort_index()
df2 = df2.fillna(0)
color = []
n = []
k = 1
for i in df:
    for j in i:
        if k % 2 == 0:
            n.append(j)
            k += 1
        else:
            color.append(j)
            k += 1
context = {
    'color': color,
    'n': n
}

