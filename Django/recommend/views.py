from django.shortcuts import render
from django.template import loader
from django.http import HttpResponse, JsonResponse
import pandas as pd

def recommend(request) :
    name = request.GET.get('id', "")
    context = {'result' : name}
    return render(request, 'test.html', context)

def rcmd(request):
    Clothes = pd.read_csv('../Django/recommend/dummydata/dummyClothes.csv', encoding='Utf-8', index_col=0)
    MyClothes = pd.read_csv('../Django/recommend/dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
    UserData = pd.read_csv('../Django/recommend/dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
    df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
    user_id = request.GET.get("id")
    dummy = df[df['ID'] == user_id]
    dummy['clothes'] = dummy['myColor'] + ' - ' + dummy['myCategory']
    Clothes['clothes'] = Clothes['color'] + ' - ' + Clothes['category']
    have = pd.merge(Clothes, dummy, left_on='clothes', right_on='clothes', how='left')
    my_max_category = have['myCategory'].value_counts().head(3)
    my_max_category = pd.DataFrame(my_max_category)
    my_max_category = my_max_category.reset_index().values.tolist()
    my_max_category = sum(my_max_category, [])
    rcmd_clothes = Clothes[Clothes['category'] == my_max_category[0]].loc[:, ['clothes']].head(5)
    rcmd_img = Clothes[Clothes['category'] == my_max_category[0]].loc[:, ['img']].head(5)
    for i in range(1, 3):
        rcmd_clothes_add = Clothes[Clothes['category'] == my_max_category[2*i]].loc[:, ['clothes']].head(5)
        rcmd_img_add = Clothes[Clothes['category'] == my_max_category[2*i]].loc[:, ['img']].head(5)
        rcmd_clothes = pd.concat([rcmd_clothes, rcmd_clothes_add])
        rcmd_img = pd.concat([rcmd_img, rcmd_img_add])
    context = {
        'clothes' : rcmd_clothes['clothes'],
        'img' : rcmd_img['img'],
    }
    return render(request, 'test1.html', context)

def compare(request):
    MyClothes = pd.read_csv('../Django/recommend/dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
    UserData = pd.read_csv('../Django/recommend/dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
    df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
    userid = request.GET.get("id")
    category = request.GET.get("category")
    color = request.GET.get("color")
    dummy = df[df['ID'] == userid]
    compare_category = dummy[(dummy['myCategory'] == category) & (dummy['myColor'] == color)].loc[:, ['myCategory']]
    compare_color = dummy[(dummy['myCategory'] == category) & (dummy['myColor'] == color)].loc[:, ['myColor']]
    compare_img = dummy[(dummy['myCategory'] == category)].loc[:, ['myImg']]

    # 결과값 리스트에 저장
    img_lst = []

    for img_name in compare_img['myImg']:
        img_lst.append(img_name)

    # 결과값 Json 형식으로 변환
    context = {
        'result' : img_lst
    }

    return render(request, 'test2.html', context)

