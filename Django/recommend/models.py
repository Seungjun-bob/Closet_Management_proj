from django.db import models

# Create your models here.
class MusinsaClothes(models.Model):
    musinsaid = models.IntegerField(primary_key=True)
    color = models.CharField(max_length=40)
    category = models.CharField(max_length=40)
    img = models.CharField(max_length=40)