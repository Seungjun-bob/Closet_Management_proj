from django.shortcuts import render
from django.template import loader
from django.http import HttpResponse, JsonResponse
import pandas as pd
from .models import Account
from .models import myClothes
from .models import Clothes
from django.http import JsonResponse


def recommend(request) :
    name = request.GET.get('id', "")
    context = {'result' : name}
    return render(request, 'test.html', context)

def rcmd(request):
    user_id = request.POST.get("id")
    clothes = Clothes.objects.get()
    myclothes = myClothes.objects.get(id=user_id)
    userdata = Account.objects.get(id=user_id)
    df = pd.merge(userdata, myclothes, left_on='id', right_on='id', how='left')
    df['id'] = df['id'].apply(str)
    dummy = df[df['id'] == user_id]
    dummy['clothes'] = dummy['mycolor'] + ' - ' + dummy['mycategory']
    clothes['clothes'] = clothes['color'] + ' - ' + clothes['category']
    have = pd.merge(clothes, dummy, left_on='clothes', right_on='clothes', how='left')
    my_max_category = have['myCategory'].value_counts().head(3)
    my_max_category = pd.DataFrame(my_max_category)
    my_max_category = my_max_category.reset_index().values.tolist()
    my_max_category = sum(my_max_category, [])
    rcmd_clothes = clothes[clothes['category'] == my_max_category[0]].loc[:, ['clothes']].head(5)
    rcmd_img = clothes[clothes['category'] == my_max_category[0]].loc[:, ['img']].head(5)
    for i in range(1, 3):
        rcmd_clothes_add = clothes[clothes['category'] == my_max_category[2*i]].loc[:, ['clothes']].head(5)
        rcmd_img_add = clothes[clothes['category'] == my_max_category[2*i]].loc[:, ['img']].head(5)
        rcmd_clothes = pd.concat([rcmd_clothes, rcmd_clothes_add])
        rcmd_img = pd.concat([rcmd_img, rcmd_img_add])
    context = {
        'clothes': rcmd_clothes['clothes'],
        'img': rcmd_img['img'],
    }
    return JsonResponse(context, safe=False, json_dumps_params={'ensure_ascii': False})

def compare(request):
    userid = request.POST.get("id")
    myclothes = myClothes.objects.get(id=user_id)
    userdata = Account.objects.get(id=user_id)
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

