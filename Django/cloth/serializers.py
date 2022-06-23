from rest_framework import serializers
from .models import MyClothes

class MyClothesSerializer(serializers.ModelSerializer):
    class Meta:
        model = MyClothes
        fields =  '__all__'