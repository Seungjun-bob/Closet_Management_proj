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
    userid = request.POST.get("id")
    musinsaclothes = MusinsaClothes.objects.get()
    myclothes = MyClothes.objects.get(id=userid)
    userdata = Account.objects.get(id=userid)
    df = pd.merge(userdata, myclothes, left_on='id', right_on='id', how='left')
    df['id'] = df['id'].apply(str)
    dummy = df[df['id'] == userid]
    dummy['clothes'] = dummy['mycolor'] + ' - ' + dummy['mycategory']
    musinsaclothes['clothes'] = musinsaclothes['color'] + ' - ' + musinsaclothes['category']
    have = pd.merge(musinsaclothes, dummy, left_on='clothes', right_on='clothes', how='left')
    my_max_category = have['myCategory'].value_counts().head(3)
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
    context = {
        'clothes': rcmd_clothes['clothes'],
        'img': rcmd_img['img'],
    }
    return JsonResponse(context, safe=False, json_dumps_params={'ensure_ascii': False})

def compare(request):
    userid = request.POST.get("id")
    myclothes = MyClothes.objects.get(id=userid)
    userdata = Account.objects.get(id=userid)
    df = pd.merge(userdata, myclothes, left_on='id', right_on='id', how='left')
    category = request.POST.get("category")
    df['id'] = df['id'].apply(str)
    print(df.dtypes)
    dummy = df[df['id'] == userid]
    compare_img = dummy[(dummy['mycategory'] == category)].loc[:, ['myimg']]

    # 결과값 리스트에 저장
    img_lst = []

    for img_name in compare_img['myimg']:
        img_lst.append(img_name)

    # 결과값 Json 형식으로 변환
    context = {
        'result' : img_lst
    }
    {"result": []}
    return JsonResponse(context, safe=False, json_dumps_params={'ensure_ascii': False})

def mypiecategory(request):
    # userid = request.POST.get("id")
    # myclothes = MyClothes.objects.get(id=userid)
    userid = '77'
    myclothes = pd.read_csv('C:/Users/Seungjun/Desktop/BIGDATA_edu/Closet_Management_proj/Django/recommend/dummydata/MyClothes.csv', encoding='Utf-8', index_col=0)

    myclothes['userid'] = myclothes['userid'].apply(str)
    myclothes['myclothesid'] = myclothes['myclothesid'].apply(str)
    df = myclothes[myclothes['userid'] == userid]
    df = df.groupby('mycategory').count().loc[:, 'userid']
    df = df.to_frame()
    form = pd.DataFrame(
        {"count": (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},
        index={'long_sleeve_dress', 'long_sleeve_outer', 'long_sleeve_top', 'short_sleeve_dress', 'short_sleeve_outer',
               'short_sleeve_top', 'shorts', 'skirt', 'sling', 'sling_dress', 'trousers', 'vest', 'vest_dress'})
    df = pd.merge(form, df, left_index=True, right_index=True, how='left')
    df = df.loc[:, 'userid'].sort_index()
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
    userid = request.POST.get("id")
    myclothes = MyClothes.objects.get(id=userid)
    myclothes['userid'] = myclothes['userid'].apply(str)
    myclothes['myclothesid'] = myclothes['myclothesid'].apply(str)
    df = myclothes[myclothes['userid'] == userid]
    df = df.groupby('mycolor').count().loc[:, 'userid']
    df = df.to_frame()
    form = pd.DataFrame(
        {"count": (0, 0, 0, 0, 0, 0, 0, 0)},
        index={'black', 'blue', 'red', 'green', 'white', 'pattern', 'gray', 'beige'})
    df = pd.merge(form, df, left_index=True, right_index=True, how='left')
    df = df.loc[:, 'userid'].sort_index()
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
    return JsonResponse(context, safe=False, json_dumps_params={'ensure_ascii': False})