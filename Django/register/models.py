from django.db import models

class UserData(models.Model):
    GENDER_CHOICES=(
        (1,'남성'),
        (2,'여성'),
        )
    email = models.CharField(max_length=40)
    pw = models.CharField(max_length=40)
    pwcheck = models.CharField(max_length=40)
    name = models.CharField(max_length=10)
    birth = models.DateField()
    gender = models.IntegerField(choices=GENDER_CHOICES)