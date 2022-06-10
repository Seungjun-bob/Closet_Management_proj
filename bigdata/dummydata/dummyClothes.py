import pandas as pd
import random

ClothesCODE = ['CODE'+str(i) for i in range(101)]
colorList = ['red', 'blue', 'white', 'black', 'yellow']
color = [random.choice(colorList) for i in range(101)]
categoryList = ['jean', 'cotton', 'jogger', 'slacks', 'skirt',
                'longPants', 'shortPants', 'hoodie', 'knit',
                'longShirts', 'shortShirts', 'sweatShirts', 'dressShirts']
category = [random.choice(categoryList) for i in range(101)]
img = ['img'+str(i) for i in range(101)]

Clothes = pd.DataFrame(
    {'ClothesCODE': ClothesCODE,
     'color': color,
     'category': category,
     'img': img
     })
print(Clothes)
Clothes['MainCategory'] = ['bottom'
                           if s == 'jean'
                              or s == 'cotton'
                              or s == 'jogger'
                              or s == 'slacks'
                              or s == 'skirt'
                              or s == 'longPants'
                              or s == 'shortPants'
                           else
                                'top'
                           for s in Clothes['category']]
print(Clothes)
Clothes.to_csv('dummyClothes.csv', encoding='Utf-8')
