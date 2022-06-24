import pandas as pd
from matplotlib import pyplot as plt

userid = '88'
musinsaclothes = pd.read_csv('../dummydata/Clothes.csv', encoding='Utf-8', index_col=0)
myclothes = pd.read_csv('../dummydata/MyClothes.csv', encoding='Utf-8', index_col=0)
userdata = pd.read_csv('../dummydata/User.csv', encoding='Utf-8', index_col=0)
df = pd.merge(userdata, myclothes, left_on='userid', right_on='userid', how='left')
df['userid'] = df['userid'].apply(str)
dummy = df[df['userid'] == userid]
dummy['clothes'] = dummy['mycolor'] + ' - ' + dummy['mycategory']
musinsaclothes['clothes'] = musinsaclothes['color'] + ' - ' + musinsaclothes['category']
have = pd.merge(musinsaclothes, dummy, left_on='clothes', right_on='clothes', how='left')
my_max_category = have['mycategory'].value_counts().head(3)
my_max_category = pd.DataFrame(my_max_category)
my_max_category = my_max_category.reset_index().values.tolist()
my_max_category = sum(my_max_category, [])
rcmd_clothes = musinsaclothes[musinsaclothes['category'] == my_max_category[0]].loc[:, ['clothes']].head(5)
rcmd_img = musinsaclothes[musinsaclothes['category'] == my_max_category[0]].loc[:, ['img']].head(5)
for i in range(1, 3):
    rcmd_clothes_add = musinsaclothes[musinsaclothes['category'] == my_max_category[2*i]].loc[:, ['clothes']].head(5)
    rcmd_img_add = musinsaclothes[musinsaclothes['category'] == my_max_category[2*i]].loc[:, ['img']].head(5)
    rcmd_clothes = pd.concat([rcmd_clothes, rcmd_clothes_add])
    rcmd_img = pd.concat([rcmd_img, rcmd_img_add])
context = {
    'clothes': rcmd_clothes['clothes'],
    'img': rcmd_img['img'],
}
print(context)
print(type(context))