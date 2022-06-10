import pandas as pd
import random

CODE = ['my'+str(i) for i in range(1,2001)]
ClothesCODEList = ['CODE'+str(i) for i in range(1,101)]
ClothesCODE = [random.choice(ClothesCODEList) for i in range(1,2001)]
IDList = ['dummy'+str(i) for i in range(1,101)]
ID = [random.choice(IDList) for i in range(1,2001)]
BuyDateList = pd.date_range(start='20180101', end='20211231')
BuyDate = [random.choice(BuyDateList) for i in range(1,2001)]

MyClothes = pd.DataFrame(
    {'CODE': CODE,
     'ClothesCODE': ClothesCODE,
     'ID': ID,
     'BuyDate': BuyDate,
    }
)
print(MyClothes)
MyClothes.to_csv('dummyMyClothes.csv', encoding='utf-8')
