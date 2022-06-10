import pandas as pd
import sys
import matplotlib.pyplot as plt
from matplotlib import font_manager, rc
from matplotlib import cm
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

#연간 폐의류
trash = pd.read_csv('trash.csv')
trash = trash.set_index('year')
print(trash)

tplt = trash['ton/day'].plot(kind='line', figsize=(10, 8), grid=True)
tplt.text(4, 120, '(단위 : 톤)', ha='right', fontsize=11)
tplt.legend(loc=2)
plt.title('일간 폐의류량')
plt.savefig("output1.png")

#백화점
Dprofit = pd.read_csv('DepartmentProfit.csv')
Dprofit = Dprofit.set_index('year')
print(Dprofit)

df = Dprofit.plot(kind='line', figsize=(10, 8), grid=True)
df.text(1, 1, '(단위 : %)', ha='right', fontsize=11)
df.legend(loc=2)
plt.title('백화점 방문객 증감률')
plt.savefig("output2.png")

#패션 앱 사용자 수
appuser = pd.read_csv('appuser.csv')
appuser = appuser.set_index('appname')
print(appuser)

au1 = appuser['user'].plot(kind='bar', figsize=(10, 8), grid=True)
au1.get_legend().set_visible(False)
au1.text(60, 1000000, '(단위 : 명)', ha='right', fontsize=11)
plt.title('패션앱 이용자 수')
plt.savefig("output3.png")

appuser2 = pd.read_csv('appuser.csv')
appuser2 = appuser2.loc[:,['appname','share']]
appuser2 = appuser2.set_index('appname')

au2 = appuser2.plot(kind='bar', figsize=(10, 8), grid=True)
plt.title('패션앱 시장 점유율')
au2.get_legend().set_visible(False)
au2.text(60, 40, '(단위 : %)', ha='right', fontsize=11)
plt.savefig("output4.png")

#온라인몰 유형별 매출액#
#판매매체별#
salesType = pd.read_csv('salesType.csv')
salesType = salesType.set_index('year')
salesType = salesType/1000
print(salesType)

st = salesType.plot(kind='bar', figsize=(10, 8), grid=True)
st.text(5, 18000, '(단위 : 십억)', ha='right', fontsize=11)
st.legend(loc=2)
plt.title('판매 채널별 거래액')
plt.savefig("output5.png")

#상품범위별
productType = pd.read_csv('productType.csv')
productType = productType.set_index('year')
productType = productType/1000
print(productType)

pt = productType.plot(kind='bar', figsize=(10, 8), grid=True)
pt.text(5, 18000, '(단위 : 십억)', ha='right', fontsize=11)
pt.legend(loc=2)
plt.title('상점 규모별 거래액')
plt.savefig("output6.png")

#운영유형별
businessType = pd.read_csv('businessType.csv')
businessType = businessType.set_index('year')
businessType = businessType/1000
print(businessType)

bt = businessType.plot(kind='bar', figsize=(10, 8), grid=True)
bt.text(5, 18000, '(단위 : 십억)', ha='right', fontsize=11)
bt.legend(loc=2)
plt.title('운영 유형별 거래액')
plt.savefig("output7.png")
