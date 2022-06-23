import pandas as pd
import random

clothescode = ['CODE'+str(i) for i in range(1, 101)]
colorlist = ['red', 'blue', 'white', 'black', 'yellow']
color = [random.choice(colorlist) for i in range(1, 101)]
categoryList = ['short_sleeve_top', 'long_sleeve_top', 'short_sleeve_outer', 'long_sleeve_outer',
                'vest', 'sling', 'shirts', 'trousers', 'skirt', 'short_sleeve_dress', 'long_sleeve_dress',
                'vest_dress', 'sling_dress']
category = [random.choice(categoryList) for i in range(1, 101)]
img = ['img'+str(i) for i in range(1, 101)]

Clothes = pd.DataFrame(
    {'clothescode': clothescode,
     'color': color,
     'category': category,
     'img': img
     })
print(Clothes)
Clothes.to_csv('dummyClothes.csv', encoding='Utf-8')
