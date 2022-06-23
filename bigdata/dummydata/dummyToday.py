import pandas as pd
import random

todayid = ['Today'+str(i) for i in range(1, 1001)]
idList = [str(i) for i in range(1, 101)]
id = [random.choice(idList) for i in range(1, 1001)]
todaylist = pd.date_range(start='2022-06-08', end='2022-06-12', freq='H')
print(len(todaylist))
today = [random.choice(todaylist) for i in range(1, 1001)]
weatherlist = ['sun', 'cloud', 'rain', 'fog']
weather = [random.choice(weatherlist) for i in range(1, 1001)]
print(weather)
temp = [(random.randint(150, 300)/10) for i in range(1, 1001)]
dust = [(random.randint(0, 1500)/10) for i in range(1, 1001)]

TodayInfo = pd.DataFrame({
    'todayid': todayid,
    'id': id,
    'today': today,
    'weather': weather,
    'temp': temp,
    'dust': dust
})
print(TodayInfo)

TodayInfo.to_csv('dummyToday.csv', encoding='utf-8')
