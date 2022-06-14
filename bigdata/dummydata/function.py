import pandas as pd

Clothes = pd.read_csv('dummyClothes.csv', encoding='Utf-8')
MyClothes = pd.read_csv('dummyMyClothes.csv', encoding='Utf-8')
UserData = pd.read_csv('dummyUser.csv', encoding='Utf-8')
todayInfo = pd.read_csv('dummyToday.csv', encoding='Utf-8')

print(Clothes)
print(MyClothes)
print(UserData)
print(todayInfo)