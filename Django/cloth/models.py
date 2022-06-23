from django.db import models
from register.models import Account

class MyClothes(models.Model):
    myclothid = models.IntegerField(primary_key=True)
    accountid = models.ForeignKey(Account, on_delete=models.CASCADE, db_column='accountid')
    mycolor = models.CharField(max_length=40)
    mycategory = models.CharField(max_length=40)
    buydate = models.DateField()
    myimg = models.CharField(max_length=40)