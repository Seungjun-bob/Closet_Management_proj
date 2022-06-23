from rest_framework import serializers
from .models import User

class UserSerializer(serializers.ModelSerializer):
    def insert(self, validated_data):
        user = User.objects.insert_User(
            email = validated_data["email"],
            pw = validated_data["pw"],
            pwcheck = validated_data["pwcheck"],
            name = validated_data["name"],
            birth = validated_data["birth"],
            gender = validated_data["gender"]
        )
        return user

    class Meta:
        model = User
        fields = ['email','pw','pwcheck','name','birth','gender']