import pandas as pd

Clothes = pd.read_csv('../dummydata/dummyClothes.csv', encoding='Utf-8')
MyClothes = pd.read_csv('../dummydata/dummyMyClothes.csv', encoding='Utf-8')
UserData = pd.read_csv('../dummydata/dummyUser.csv', encoding='Utf-8')
todayInfo = pd.read_csv('../dummydata/dummyToday.csv', encoding='Utf-8')

print(Clothes)
print(MyClothes)
print(UserData)
print(todayInfo)

df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
df2 = pd.merge(df, todayInfo, left_on='ID', right_on='ID', how='left')
print(df2)

for i in range(1, 101):
    dummy = df2[df2['ID'] == 'dummy'+str(i)]
    Clothes['clothes'] = Clothes['color'] + ' - ' + Clothes['category']
    dummy['clothes'] = dummy['myColor'] + ' - ' + dummy['myCategory']
    dontHave = pd.merge(Clothes, dummy, left_on='clothes', right_on='clothes', how='left')
    dontHave2 = dontHave[['clothes','img']].loc[dontHave['ID'].isnull()]
    print('dummy'+str(i))
    print(dontHave2.drop_duplicates())
    print()
