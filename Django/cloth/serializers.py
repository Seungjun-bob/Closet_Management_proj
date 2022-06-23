from rest_framework import serializers
from .models import User, MyClothes

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields =  '__all__'

class MyClothesSerializer(serializers.ModelSerializer):
    class Meta:
        model = MyClothes
        fields =  '__all__'