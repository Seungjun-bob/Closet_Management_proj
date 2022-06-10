import pandas as pd
import random
import numpy as np

Clothes = pd.read_csv('dummyClothes.csv', encoding='Utf-8')
MyClothes = pd.read_csv('dummyMyClothes.csv', encoding='Utf-8')
UserData = pd.read_csv('dummyUser.csv', encoding='Utf-8')

MyList = pd.merge(MyClothes, Clothes, left_on='ClothesCODE', right_on='ClothesCODE', how='left')
MyList = pd.merge(MyList, UserData, left_on='ID', right_on='ID', how='left')
TodayID = ['Today'+str(i) for i in range(1, 1001)]
TodayList = pd.date_range(start='20220101', end='20220610')
date = [random.choice(TodayList) for i in range(1, 1001)]

i = 0
dummy = []
CodeTop = list()
CodeBottom = list()

while i <= 99:
    i += 1
    for j in range(1,11):
        dummy = MyList.loc[MyList.ID == "dummy"+str(i), :]
        TopClothes = dummy.loc[dummy.MainCategory == "top", ["CODE"]]
        TopClothes = TopClothes.values.tolist()
        TopClothes = np.array(TopClothes).flatten().tolist()
        CodeTopList = [random.choice(TopClothes)]
        CodeTop.append(CodeTopList)
        BottomClothes = dummy.loc[dummy.MainCategory == "bottom", ["CODE"]]
        BottomClothes = BottomClothes.values.tolist()
        BottomClothes = np.array(BottomClothes).flatten().tolist()
        CodeBottomList = [random.choice(BottomClothes)]
        CodeBottom.append(CodeBottomList)
        print(i," ",j)
    print(CodeTop)
    print(CodeBottom)

CodeTop = np.array(CodeTop).flatten().tolist()
CodeBottom = np.array(CodeBottom).flatten().tolist()
print(CodeTop)
print(CodeBottom)


TodayInfo = pd.DataFrame({
    'TodayID': TodayID,
    'date': date,
    'CodeTop': CodeTop,
    'CodeBottom': CodeBottom
})
print(TodayInfo)

TodayInfo.to_csv('dummyToday.csv', encoding='utf-8')