import pandas as pd
from matplotlib import pyplot as plt

Clothes = pd.read_csv('../dummydata/dummyClothes.csv', encoding='Utf-8', index_col=0)
MyClothes = pd.read_csv('../dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
UserData = pd.read_csv('../dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
user_id = 'dummy1'
dummy = df[df['ID'] == user_id]
Clothes['MainCategory'] = ['bottom'
                            if s == 'skirt'
                              or s == 'trousers'
                           else 'top' if s == 'short_sleeve_top'
                                         or s == 'long_sleeve_top'
                                         or s == 'short_sleeve_outer'
                                         or s == 'long_sleeve_outer'
                                         or s == 'vest'
                                         or s == 'sling'
                                         or s == 'shirts'
                                    else 'dress'
                            for s in Clothes['category']]
print(Clothes)
dummy['MainCategory'] = ['bottom'
                            if s == 'skirt'
                              or s == 'trousers'
                           else 'top' if s == 'short_sleeve_top'
                                         or s =='long_sleeve_top'
                                         or s == 'short_sleeve_outer'
                                         or s == 'long_sleeve_outer'
                                         or s == 'vest'
                                         or s == 'sling'
                                         or s == 'shirts'
                                    else 'dress'
                            for s in dummy['myCategory']]
print(dummy)
top_clothes = Clothes[Clothes['MainCategory'] == 'top']
print(top_clothes)
top_clothes['clothes'] = top_clothes['color'] + ' - ' + top_clothes['category']
top_dummy = dummy[dummy['MainCategory'] == 'top']
top_dummy['clothes'] = top_dummy['myColor'] + ' - ' + top_dummy['myCategory']
have = pd.merge(top_clothes, top_dummy, left_on='clothes', right_on='clothes', how='left')
print(have)
my_max_category1 = have['myCategory'].value_counts()[have['myCategory'].value_counts() == max((have['myCategory'].value_counts()))].head(1)
my_max_category1 = pd.DataFrame(my_max_category1)
my_max_category1 = my_max_category1.reset_index().values.tolist()
my_max_category1 = sum(my_max_category1, [])
top_rcmd_clothes = top_clothes[top_clothes['category'] == my_max_category1[0]].loc[:, ['clothes']]
top_rcmd_img = top_clothes[top_clothes['category'] == my_max_category1[0]].loc[:, ['img']]
bottom_clothes = Clothes[Clothes['MainCategory'] == 'bottom']
bottom_clothes['clothes'] = bottom_clothes['color'] + ' - ' + bottom_clothes['category']
bottom_dummy = dummy[dummy['MainCategory'] == 'bottom']
bottom_dummy['clothes'] = bottom_dummy['myColor'] + ' - ' + bottom_dummy['myCategory']
have = pd.merge(bottom_clothes, bottom_dummy, left_on='clothes', right_on='clothes', how='left')
my_max_category2 = have['myCategory'].value_counts()[have['myCategory'].value_counts() == max((have['myCategory'].value_counts()))].head(1)
my_max_category2 = pd.DataFrame(my_max_category2)
my_max_category2 = my_max_category2.reset_index().values.tolist()
my_max_category2 = sum(my_max_category2, [])
bottom_rcmd_clothes = bottom_clothes[bottom_clothes['category'] == my_max_category2[0]].loc[:, ['clothes']]
bottom_rcmd_img = bottom_clothes[bottom_clothes['category'] == my_max_category2[0]].loc[:, ['img']]
print(have['myCategory'])
print(bottom_rcmd_img)