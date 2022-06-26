from django.db import models
from register.models import Account

class MyClothes(models.Model):
    myclothid = models.IntegerField(primary_key=True)
    accountid = models.ForeignKey(Account, on_delete=models.CASCADE, db_column='accountid')
    mycolor = models.CharField(max_length=40)
    mycategory = models.CharField(max_length=40)
    buydate = models.DateField()
    myimg = models.CharField(max_length=40)

    def insert_cloth(self, accountid, mycolor, mycategory, buydate, myimg):
        if not accountid:
            raise ValueError('email을 입력하세요')
        if not mycolor:
            raise ValueError('email을 입력하세요')
        if not mycategory:
            raise ValueError('email을 입력하세요')
        if not buydate:
            raise ValueError('email을 입력하세요')
        if not myimg:
            raise ValueError('email을 입력하세요')
        cloth = self.model(
            accountid=accountid,
            mycolor=mycolor,
            mycategory=mycategory,
            buydate=buydate,
            myimg=myimg
        )
        cloth.save(using=self._db)
        return cloth

    class Meta:
        managed = False
        db_table = 'MyClothes'

    def __str__(self):
        return "이름 : " + self.name