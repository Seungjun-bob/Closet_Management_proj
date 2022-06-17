from django.shortcuts import render
from django.template import loader
from django.http import HttpResponse, JsonResponse
import pandas as pd

def recommend(request) :
    name = request.GET.get('id', "")
    context = {'result' : name}
    return render(request, 'test.html', context)

def compare(request):
    MyClothes = pd.read_csv('C:/Users/Seungjun/Desktop/BIGDATA_edu/Closet_Management_proj/bigdata/dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
    UserData = pd.read_csv('C:/Users/Seungjun/Desktop/BIGDATA_edu/Closet_Management_proj/bigdata/dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
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

    return render(request, 'test.html', context)

