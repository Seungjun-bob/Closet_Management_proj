import pandas as pd
import random

ID = ['dummy'+str(i) for i in range(1,101)]
PW = ['dummy'+str(i) for i in range(1,101)]
PW2 = ['dummy'+str(i) for i in range(1,101)]
Name = ['dummy'+str(i) for i in range(1,101)]
dateIndex = pd.date_range(start='19500101', end='20101231')
print(dateIndex)
Birth = [random.choice(dateIndex) for i in range(1,101)]
Email = ['dummy'+str(i)+'@gmail.com' for i in range(1,101)]
GenderList = ['F', 'M']
Gender = [random.choice(GenderList) for i in range(1,101)]

UserData = pd.DataFrame(
    {'ID': ID,
     'PW': PW,
     'PW2': PW2,
     'Name': Name,
     'Birth': Birth,
     'Email': Email,
     'Gender': Gender})
print(UserData)
UserData.to_csv('dummyUser.csv', encoding='Utf-8')
