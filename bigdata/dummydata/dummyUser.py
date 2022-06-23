import pandas as pd
import random

id = [str(i) for i in range(1,101)]
pw = ['dummy'+str(i) for i in range(1,101)]
pwcheck = ['dummy'+str(i) for i in range(1,101)]
name = ['dummy'+str(i) for i in range(1,101)]
dateindex = pd.date_range(start='19500101', end='20101231')
print(dateindex)
birth = [random.choice(dateindex) for i in range(1,101)]
email = ['dummy'+str(i)+'@gmail.com' for i in range(1,101)]
GenderList = ['F', 'M']
gender = [random.choice(GenderList) for i in range(1,101)]

UserData = pd.DataFrame(
    {'id': id,
     'pw': pw,
     'pwcheck': pwcheck,
     'name': name,
     'birth': birth,
     'email': email,
     'gender': gender})
print(UserData.dtypes)
UserData.to_csv('dummyUser.csv', encoding='Utf-8')
