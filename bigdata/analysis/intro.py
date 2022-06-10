import pandas as pd
import sys
import matplotlib.pyplot as plt
from matplotlib import font_manager, rc

if sys.platform == 'darwin':
    path = '.....'
elif sys.platform == 'win32':
    path = "font/MaplestoryBold.ttf"
else:
    print('Unknown system... sorry~~~~')
font_name = font_manager.FontProperties(fname=path).get_name()
rc('font', family=font_name)
plt.rcParams['axes.unicode_minus'] = False
plt.rcParams['font.family'] = 'NanumGothic'

#연간 폐의류#
trash = pd.read_csv('trash.csv')
trash = trash.set_index('year')
print(trash)

trash['ton/day'].plot(kind='line', figsize=(10, 8), grid=True)
plt.text(4, 7, '(단위 : 톤)')
plt.legend(loc=2)
plt.title('일간 폐의류량')
plt.savefig("output1.png")

#백화점#
Dprofit = pd.read_csv('DepartmentProfit.csv')
Dprofit = Dprofit.set_index('year')
print(Dprofit)

Dprofit.plot(kind='line', figsize=(10, 8), grid=True)
plt.title('백화점 방문객 증감률')
plt.text(4, 7, '(단위 : %)')
plt.legend(loc=2)
plt.savefig("output2.png")

#패션 앱 사용자 수#
appuser = pd.read_csv('appuser.csv')
appuser = appuser.loc[:,['appname', 'user']]
appuser = appuser.set_index('appname')
print(appuser)

appuser.plot(kind='bar', figsize=(10, 8), grid=True)
plt.title('패션앱 이용자 수')
plt.text(55, 1050000, '(단위 : 명)')
plt.savefig("output3.png")

appuser2 = pd.read_csv('appuser.csv')
appuser2 = appuser2.loc[:,['appname','share']]
appuser2 = appuser2.set_index('appname')

appuser2.plot(kind='bar', figsize=(10, 8), grid=True)
plt.title('패션앱 시장 점유율')
plt.text(55, 40, '(단위 : %)')
plt.savefig("output4.png")

#온라인몰 유형별 매출액#
#판매매체별#
salesType = pd.read_csv('salesType.csv')
salesType = salesType.set_index('year')
salesType = salesType/1000
print(salesType)

salesType.plot(kind='bar', figsize=(10, 8), grid=True)
plt.text(4, 18000, '(단위 : 십억)')
plt.legend(loc=2)
plt.title('판매 채널별 거래액')
plt.savefig("output5.png")

#상품범위별#
productType = pd.read_csv('productType.csv')
productType = productType.set_index('year')
productType = productType/1000
print(productType)

productType.plot(kind='bar', figsize=(10, 8), grid=True)
plt.text(4, 18000, '(단위 : 십억)')
plt.legend(loc=2)
plt.title('상점 규모별 거래액')
plt.savefig("output6.png")

#운영유형별#
businessType = pd.read_csv('businessType.csv')
businessType = businessType.set_index('year')
businessType = businessType/1000
print(businessType)

businessType.plot(kind='bar', figsize=(10, 8), grid=True)
plt.text(4, 18000, '(단위 : 십억)')
plt.legend(loc=2)
plt.title('운영 유형별 거래액')
plt.savefig("output7.png")