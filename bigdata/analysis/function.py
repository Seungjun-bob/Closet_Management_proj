import pandas as pd
from django.shortcuts import render

def top_rcmd(request):
    Clothes = pd.read_csv('D:/lee/study/cluster project/Closet_Management_proj/bigdata/dummydata/dummyClothes.csv', encoding='Utf-8', index_col=0)
    MyClothes = pd.read_csv('D:/lee/study/cluster project/Closet_Management_proj/bigdata/dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
    UserData = pd.read_csv('D:/lee/study/cluster project/Closet_Management_proj/bigdata/dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
    df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
    user_id = request.GET.get("id")
    dummy = df[df['ID'] == user_id]
    Clothes['MainCategory'] = ['bottom'
                               if s == 'jean'
                                  or s == 'cotton'
                                  or s == 'jogger'
                                  or s == 'slacks'
                                  or s == 'skirt'
                                  or s == 'longPants'
                                  or s == 'shortPants'
                               else 'top'
                               for s in Clothes['category']]
    dummy['MainCategory'] = ['bottom'
                             if s == 'jean'
                                or s == 'cotton'
                                or s == 'jogger'
                                or s == 'slacks'
                                or s == 'skirt'
                                or s == 'longPants'
                                or s == 'shortPants'
                             else 'top'
                             for s in dummy['myCategory']]
    top_clothes = Clothes[Clothes['MainCategory'] == 'top']
    top_clothes['clothes'] = top_clothes['color'] + ' - ' + top_clothes['category']
    top_dummy = dummy[dummy['MainCategory'] == 'top']
    top_dummy['clothes'] = top_dummy['myColor'] + ' - ' + top_dummy['myCategory']
    have = pd.merge(top_clothes, top_dummy, left_on='clothes', right_on='clothes', how='left')
    my_max_category = have['myCategory'].value_counts()[have['myCategory'].value_counts() ==
                                                        max((have['myCategory'].value_counts()))].head(1)
    my_max_category = pd.DataFrame(my_max_category)
    my_max_category = my_max_category.reset_index().values.tolist()
    my_max_category = sum(my_max_category, [])
    top_rcmd_clothes = top_clothes[top_clothes['category'] == my_max_category[0]].loc[:, ['clothes']]
    top_rcmd_img = top_clothes[top_clothes['category'] == my_max_category[0]].loc[:, ['img']]
    context = {
        'top_clothes' : top_rcmd_clothes,
        'top_img' : top_rcmd_img
    }
    return render(request, 'test.html', context)

def bottom_rcmd(request):
    Clothes = pd.read_csv('../dummydata/dummyClothes.csv', encoding='Utf-8', index_col=0)
    MyClothes = pd.read_csv('../dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
    UserData = pd.read_csv('../dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
    df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
    userid = request.GET.get("id")
    dummy = df[df['ID'] == userid]
    Clothes['MainCategory'] = ['bottom'
                               if s == 'jean'
                                  or s == 'cotton'
                                  or s == 'jogger'
                                  or s == 'slacks'
                                  or s == 'skirt'
                                  or s == 'longPants'
                                  or s == 'shortPants'
                               else 'top'
                               for s in Clothes['category']]
    dummy['MainCategory'] = ['bottom'
                             if s == 'jean'
                                or s == 'cotton'
                                or s == 'jogger'
                                or s == 'slacks'
                                or s == 'skirt'
                                or s == 'longPants'
                                or s == 'shortPants'
                             else 'top'
                             for s in dummy['myCategory']]
    bottom_clothes = Clothes[Clothes['MainCategory'] == 'bottom']
    bottom_clothes['clothes'] = bottom_clothes['color'] + ' - ' + bottom_clothes['category']
    bottom_dummy = dummy[dummy['MainCategory'] == 'bottom']
    bottom_dummy['clothes'] = bottom_dummy['myColor'] + ' - ' + bottom_dummy['myCategory']
    have = pd.merge(bottom_clothes, bottom_dummy, left_on='clothes', right_on='clothes', how='left')
    my_max_category = have['myCategory'].value_counts()[have['myCategory'].value_counts() ==
                                                        max((have['myCategory'].value_counts()))].head(1)
    my_max_category = pd.DataFrame(my_max_category)
    my_max_category = my_max_category.reset_index().values.tolist()
    my_max_category = sum(my_max_category, [])
    bottom_rcmd_clothes = bottom_clothes[bottom_clothes['category'] == my_max_category[0]].loc[:, ['clothes']]
    bottom_rcmd_img = bottom_clothes[bottom_clothes['category'] == my_max_category[0]].loc[:, ['img']]
    context = {
        'bottom_clothes': bottom_rcmd_clothes,
        'bottom_img': bottom_rcmd_img
    }
    return render(request, 'test.html', context)

def compare(request):
    MyClothes = pd.read_csv('../dummydata/dummyMyClothes.csv', encoding='Utf-8', index_col=0)
    UserData = pd.read_csv('../dummydata/dummyUser.csv', encoding='Utf-8', index_col=0)
    df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
    userid = request.GET.get("id")
    category = request.GET.get("category")
    color = request.GET.get("color")
    dummy = df[df['ID'] == userid]
    compare_category = dummy[(dummy['myCategory'] == category) & (dummy['myColor'] == color)].loc[:, ['myCategory']]
    compare_color = dummy[(dummy['myCategory'] == category) & (dummy['myColor'] == color)].loc[:, ['myColor']]
    compare_img = dummy[(dummy['myCategory'] == category) & (dummy['myColor'] == color)].loc[:, ['myImg']]
    context = {
        'compare_category': compare_category,
        'compare_color': compare_color,
        'compare_img': compare_img
    }
    return render(request, 'test.html', context)
