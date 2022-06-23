import pandas as pd
import random

code = ['my'+str(i) for i in range(1,2001)]
IDList = [str(i) for i in range(1,101)]
id = [random.choice(IDList) for i in range(1, 2001)]
buydatelist = pd.date_range(start='20180101', end='20211231').strftime('%Y-%m-%d %H:%M:%S')
buydate = [random.choice(buydatelist) for i in range(1, 2001)]
colorlist = ['red', 'blue', 'white', 'black', 'blue', 'pattern']
mycolor = [random.choice(colorlist) for i in range(1, 2001)]
categorylist = ['short_sleeve_top', 'long_sleeve_top', 'short_sleeve_outer', 'long_sleeve_outer',
                'vest', 'sling', 'shirts', 'trousers', 'skirt', 'short_sleeve_dress', 'long_sleeve_dress',
                'vest_dress', 'sling_dress']
mycategory = [random.choice(categorylist) for i in range(1, 2001)]
myimg = ['img'+str(i) for i in range(1, 2001)]


MyClothes = pd.DataFrame(
    {'code': code,
     'id': id,
     'mycolor': mycolor,
     'mycategory': mycategory,
     'myimg': myimg,
     'buydate': buydate
     }
)
print(MyClothes)
MyClothes.to_csv('dummyMyClothes.csv', encoding='utf-8')
