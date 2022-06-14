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

userID = input('ID를 입력하세요 >> ')

dummy = df2[df2['ID'] == userID]
Clothes['clothes'] = Clothes['color'] + ' - ' + Clothes['category']
dummy['clothes'] = dummy['myColor'] + ' - ' + dummy['myCategory']
Have = pd.merge(Clothes, dummy, left_on='clothes', right_on='clothes', how='left')
Have2 = Have[['clothes']].loc[Have['ID'].isnull()]
myMaxCategory = dummy['myCategory'].value_counts()[dummy['myCategory'].value_counts() ==
                                                   max((dummy['myCategory'].value_counts()))].head(1)
myMaxCategory
myMaxColor = dummy['myColor'].value_counts()[dummy['myColor'].value_counts() ==
                                             max(dummy['myColor'].value_counts())].head(1)
print(userID)
print(Have2[Have2['category'] == myMaxCategory]) # 듀블 말고 max유저 옷으로 바꾸기
