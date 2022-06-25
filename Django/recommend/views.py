from django.shortcuts import render
from django.template import loader
from django.http import HttpResponse, JsonResponse
import pandas as pd
from register.models import Account
from cloth.models import MyClothes
from .models import MusinsaClothes
from django.http import JsonResponse

def recommend(request) :
    name = request.GET.get('id', "")
    context = {'result' : name}
    return render(request, 'test.html', context)

def rcmd(request):
    userid = request.GET.get("id")
    musinsaclothes = pd.DataFrame(MusinsaClothes.objects.all().values())
    myclothes = pd.DataFrame(MyClothes.objects.filter(accountid=userid).values())
    myclothes['clothes'] = myclothes['mycolor'] + ' - ' + myclothes['mycategory']
    musinsaclothes['clothes'] = musinsaclothes['color'] + ' - ' + musinsaclothes['category']
    have = pd.merge(musinsaclothes, myclothes, left_on='clothes', right_on='clothes', how='left')
    my_max_category = have['mycategory'].value_counts().head(3)
    my_max_category = pd.DataFrame(my_max_category)
    my_max_category = my_max_category.reset_index().values.tolist()
    my_max_category = sum(my_max_category, [])
    rcmd_clothes = musinsaclothes[musinsaclothes['category'] == my_max_category[0]].loc[:, ['clothes']].head(5)
    rcmd_img = musinsaclothes[musinsaclothes['category'] == my_max_category[0]].loc[:, ['img']].head(5)
    for i in range(1, 3):
        rcmd_clothes_add = musinsaclothes[musinsaclothes['category'] == my_max_category[2*i]].loc[:, ['clothes']].head(5)
        rcmd_img_add = musinsaclothes[musinsaclothes['category'] == my_max_category[2*i]].loc[:, ['img']].head(5)
        rcmd_clothes = pd.concat([rcmd_clothes, rcmd_clothes_add])
        rcmd_img = pd.concat([rcmd_img, rcmd_img_add])
    img_lst = []
    for img_name in rcmd_img['img']:
        img_lst.append(img_name)

    context = {
        'result': img_lst
    }
    print(context)
    {"result": []}
    return JsonResponse(context, safe=False, json_dumps_params={'ensure_ascii': False})

def mypiecategory(request):
    userid = request.GET.get("id")
    myclothes = pd.DataFrame(MyClothes.objects.filter(accountid=userid).values())
    # userid = '77'
    # myclothes = pd.read_csv('C:/Users/Seungjun/Desktop/BIGDATA_edu/Closet_Management_proj/Django/recommend/dummydata/MyClothes.csv', encoding='Utf-8', index_col=0)
    print(myclothes)
    df = myclothes.groupby('mycategory').count().loc[:, 'myclothid']
    print(df, type(df))
    form = pd.DataFrame(
        {"count": (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},
        index={'long_sleeve_dress', 'long_sleeve_outer', 'long_sleeve_top', 'short_sleeve_dress', 'short_sleeve_outer',
               'short_sleeve_top', 'shorts', 'skirt', 'sling', 'sling_dress', 'trousers', 'vest', 'vest_dress'})
    df = pd.merge(form, df, left_index=True, right_index=True, how='left')
    df = df.loc[:, 'myclothid'].sort_index()
    df = df.fillna(0)
    df = df.astype('int')
    df = df.reset_index().values.tolist()
    category = []
    n = []
    k = 1

    for i in df:
        for j in i:
            if k % 2 == 0:
                n.append(j)
                k += 1
            else:
                category.append(j)
                k += 1
    context = {
        'category': category,
        'n': n
    }
    print(context)
    return JsonResponse(context, safe=False, json_dumps_params={'ensure_ascii': False})

def mypiecolor(request):
    userid = request.GET.get("id")
    myclothes = pd.DataFrame(MyClothes.objects.filter(accountid=userid).values())
    df = myclothes.groupby('mycolor').count().loc[:, 'myclothid']
    print(df, type(df))
    form = pd.DataFrame(
        {"count": (0, 0, 0, 0, 0, 0, 0, 0)},
        index={'black', 'blue', 'red', 'green', 'white', 'pattern', 'gray', 'beige'})
    df = pd.merge(form, df, left_index=True, right_index=True, how='left')
    df = df.loc[:, 'myclothid'].sort_index()
    df = df.fillna(0)
    df = df.astype('int')
    df = df.reset_index().values.tolist()
    color = []
    n = []
    k = 1
    for i in df:
        for j in i:
            if k % 2 == 0:
                n.append(j)
                k += 1
            else:
                color.append(j)
                k += 1
    context = {
        'color': color,
        'n': n
    }
    print(context)
    return JsonResponse(context, safe=False, json_dumps_params={'ensure_ascii': False})