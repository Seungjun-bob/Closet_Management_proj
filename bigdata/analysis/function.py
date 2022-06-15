import pandas as pd

Clothes = pd.read_csv('../dummydata/dummyClothes.csv', encoding='Utf-8')
MyClothes = pd.read_csv('../dummydata/dummyMyClothes.csv', encoding='Utf-8')
UserData = pd.read_csv('../dummydata/dummyUser.csv', encoding='Utf-8')
todayInfo = pd.read_csv('../dummydata/dummyToday.csv', encoding='Utf-8')

def top_rcmd(request):
    df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
    user_id = request
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
    print(top_clothes[top_clothes['category'] == my_max_category[0]].loc[:, ['color', 'category', 'img']])


def bottom_rcmd(request):
    df = pd.merge(UserData, MyClothes, left_on='ID', right_on='ID', how='left')
    userid = request
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
    print(bottom_clothes[bottom_clothes['category'] == my_max_category[0]].loc[:, ['color', 'category', 'img']])

bottom_rcmd('dummy1')