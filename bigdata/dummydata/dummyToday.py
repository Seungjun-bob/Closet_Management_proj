import pandas as pd
import random
import numpy as np

Clothes = pd.read_csv('dummyClothes.csv', encoding='Utf-8')
MyClothes = pd.read_csv('dummyMyClothes.csv', encoding='Utf-8')

TodayID = ['Today'+str(i) for i in range(101)]
TodayList = pd.date_range(start='20220101', end='20220610')
print(type(TodayList))
date = [random.choice(TodayList) for i in range(101)]

MyList = pd.merge(MyClothes, Clothes, left_on='ClothesCODE', right_on='ClothesCODE', how='left')
TopClothes = MyList.loc[MyList.MainCategory == 'top', ['CODE']]
TopClothes = TopClothes.values.tolist()
TopClothes = np.array(TopClothes).flatten().tolist()
BottomClothes = MyList.loc[MyList.MainCategory == 'bottom', ['CODE']]
BottomClothes = BottomClothes.values.tolist()
BottomClothes = np.array(BottomClothes).flatten().tolist()
print(type(BottomClothes))
CodeTop = [random.choice(TopClothes) for i in range(101)]
CodeBottom = [random.choice(BottomClothes) for i in range(101)]

TodayInfo = pd.DataFrame({
    'TodayID': TodayID,
    'date': date,
    'CodeTop': CodeTop,
    'CodeBottom': CodeBottom
})
print(TodayInfo)

TodayInfo.to_csv('dummyToday.csv', encoding='utf-8')