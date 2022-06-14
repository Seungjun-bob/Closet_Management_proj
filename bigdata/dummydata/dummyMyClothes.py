import pandas as pd
import random

CODE = ['my'+str(i) for i in range(1,2001)]
ClothesCODEList = ['CODE'+str(i) for i in range(1,101)]
IDList = ['dummy'+str(i) for i in range(1,101)]
ID = [random.choice(IDList) for i in range(1,2001)]
BuyDateList = pd.date_range(start='20180101', end='20211231')
BuyDate = [random.choice(BuyDateList) for i in range(1,2001)]
colorList = ['red', 'blue', 'white', 'black', 'yellow']
myColor = [random.choice(colorList) for i in range(1, 2001)]
categoryList = ['jean', 'cotton', 'jogger', 'slacks', 'skirt',
                'longPants', 'shortPants', 'bottomEtc', 'hoodie', 'knit',
                'longShirts', 'shortShirts', 'sweatShirts', 'dressShirts','topEtc']
myCategory = [random.choice(categoryList) for i in range(1, 2001)]
myImg = ['img'+str(i) for i in range(1, 2001)]


MyClothes = pd.DataFrame(
    {'CODE': CODE,
     'ID': ID,
     'myColor': myColor,
     'myCategory': myCategory,
     'myImg': myImg,
     'BuyDate': BuyDate
     }
)
print(MyClothes)
MyClothes.to_csv('dummyMyClothes.csv', encoding='utf-8')
