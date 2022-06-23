from django.db import models

# Create your models here.

class User(models.Model):
    email = models.CharField(max_length=100)
    pw = models.CharField(max_length=100)
    name = models.CharField(max_length=100)
    gender = models.CharField(max_length=100)
    birth = models.DateField()
    

class MyClothes(models.Model):
    userid = models.ForeignKey(User, on_delete=models.CASCADE)
    mycolor = models.CharField(max_length=100)
    mycategory = models.CharField(max_length=100)
    buydate = models.DateField()
    myimg = models.CharField(max_length=100)