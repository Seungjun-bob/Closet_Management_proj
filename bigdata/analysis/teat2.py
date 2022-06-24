import datetime
import random
import pandas as pd

from matplotlib import pyplot as plt

MyClothes = pd.DataFrame(
 {'CODE': [],
  'ID': [],
  'myColor': [],
  'myCategory': [],
  'myImg': [],
  'BuyDate': []
  })
cloths_type = ['shirt', 'trousers']
color_type = ['white', 'blue']
CODE = ['my'+str(len(MyClothes['CODE'])+1+i) for i in range(len(cloths_type))]
IDList = ['dummy'+str(i) for i in range(1, 101)]
ID = [random.choice(IDList)]*len(cloths_type)
BuyDate = [datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S') for i in range(len(cloths_type))]
myImg = ['img'+str(len(MyClothes['CODE'])+1) for i in range(len(cloths_type))]


MyClothes_add = pd.DataFrame(
    {'CODE': CODE,
     'ID': ID,
     'myColor': color_type,
     'myCategory': cloths_type,
     'myImg': myImg,
     'BuyDate': BuyDate
     })
MyClothes = pd.concat([MyClothes, MyClothes_add])

def myclothes(request):
    MyClothes = pd.DataFrame(
        {'CODE': [],
         'ID': [],
         'myColor': [],
         'myCategory': [],
         'myImg': [],
         'BuyDate': []
         })
    cloths_type = ['shirt', 'trousers']
    color_type = ['white', 'blue']
    CODE = ['my' + str(len(MyClothes['CODE']) + 1 + i) for i in range(len(cloths_type))]
    IDList = ['dummy' + str(i) for i in range(1, 101)]
    ID = [random.choice(IDList)] * len(cloths_type)
    BuyDate = [datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S') for i in range(len(cloths_type))]
    myImg = ['img' + str(len(MyClothes['CODE']) + 1) for i in range(len(cloths_type))]

    MyClothes_add = pd.DataFrame(
        {'CODE': CODE,
         'ID': ID,
         'myColor': color_type,
         'myCategory': cloths_type,
         'myImg': myImg,
         'BuyDate': BuyDate
         })
    MyClothes = pd.concat([MyClothes, MyClothes_add])
    return render(request, 'test1.html')