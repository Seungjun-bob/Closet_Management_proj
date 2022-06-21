import pandas as pd
import random


MyClothes = pd.read_csv('../dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
CODE = ['my'+str(i) for i in range(1,2001)]
ClothesCODEList = ['CODE'+str(i) for i in range(1,101)]
IDList = ['dummy'+str(i) for i in range(1,101)]
ID = [random.choice(IDList) for i in range(1,2001)]
BuyDateList = pd.date_range(start='20180101', end='20211231').strftime('%Y-%m-%d %H:%M:%S')
BuyDate = [random.choice(BuyDateList) for i in range(1,2001)]
colorList = ['red', 'blue', 'white', 'black', 'blue', 'pattern']
myColor = [random.choice(colorList) for i in range(1, 2001)]
categoryList = ['short_sleeve_top', 'long_sleeve_top', 'short_sleeve_outer', 'long_sleeve_outer',
                'vest', 'sling', 'shirts', 'trousers', 'skirt', 'short_sleeve_dress', 'long_sleeve_dress',
                'vest_dress', 'sling_dress']
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
