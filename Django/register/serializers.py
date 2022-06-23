from rest_framework import serializers
from .models import Account

class UserSerializer(serializers.ModelSerializer):
    def insert(self, validated_data):
        account = Account.objects.insert_User(
            email = validated_data["email"],
            pw = validated_data["pw"],
            pwcheck = validated_data["pwcheck"],
            name = validated_data["name"],
            birth = validated_data["birth"],
            gender = validated_data["gender"]
        )
        return account

    class Meta:
        model = Account
        fields = ['email','pw','pwcheck','name','birth','gender']