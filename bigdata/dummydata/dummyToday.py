import pandas as pd
import random

TodayID = ['Today'+str(i) for i in range(1, 1001)]
IDList = ['dummy'+str(i) for i in range(1, 101)]
ID = [random.choice(IDList) for i in range(1, 1001)]
TodayList = pd.date_range(start='20220401', end='20220610')
today = [random.choice(TodayList) for i in range(1, 1001)]
weatherList = ['sun', 'cloud', 'rain', 'fog']
weather = [random.choice(weatherList) for i in range(1, 1001)]
temp = [(random.randint(150, 300)/10) for i in range(1, 1001)]
dust = [(random.randint(0, 1500)/10) for i in range(1, 1001)]

TodayInfo = pd.DataFrame({
    'TodayID': TodayID,
    'ID': ID,
    'today': today,
    'weather': weather,
    'temp': temp,
    'dust': dust
})
print(TodayInfo)

TodayInfo.to_csv('dummyToday.csv', encoding='utf-8')