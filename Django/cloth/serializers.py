from rest_framework import serializers
from .models import MyClothes

class MyClothesSerializer(serializers.ModelSerializer):
    def insert(self, validated_data):
        myclothes = MyClothes.objects.insert_cloth(
            accountid=validated_data["accountid"],
            mycolor=validated_data["mycolor"],
            mycategory=validated_data["mycategory"],
            buydate=validated_data["buydate"],
            myimg=validated_data["myimg"]
        )
        return myclothes
    class Meta:
        model = MyClothes
        fields = ['accountid','mycolor','mycategory','buydate','myimg']

