import pandas as pd
import random

ClothesCODE = ['CODE'+str(i) for i in range(1, 101)]
colorList = ['red', 'blue', 'white', 'black', 'yellow']
color = [random.choice(colorList) for i in range(1, 101)]
categoryList = ['jean', 'cotton', 'jogger', 'slacks', 'skirt',
                'longPants', 'shortPants', 'bottomEtc', 'hoodie', 'knit',
                'longShirts', 'shortShirts', 'sweatShirts', 'dressShirts','topEtc']
category = [random.choice(categoryList) for i in range(1, 101)]
img = ['img'+str(i) for i in range(1, 101)]

Clothes = pd.DataFrame(
    {'ClothesCODE': ClothesCODE,
     'color': color,
     'category': category,
     'img': img
     })
print(Clothes)
Clothes.to_csv('dummyClothes.csv', encoding='Utf-8')
