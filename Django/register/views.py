from rest_framework import viewsets
from rest_framework.response import Response
from .models import User
from .serializers import UserSerializer
from django.http import JsonResponse
from rest_framework.parsers import JSONParser

def register(request):
    if request.method == 'POST':
        print("request_ok")
        print(request)
        android_data = JSONParser().parse(request)
        print(android_data)
        serializer = UserSerializer(data = android_data)

        serializer.is_valid(raise_exception=True)
        serializer.save()
        return JsonResponse(serializer.data, safe=False, json_dumps_params={'ensure_ascii' : False})

def login(request):
    if request.method == 'POST':
        print("request_ok")

        data = JSONParser().parse(request)
        print(data)

        UserId = data["email"]
        print(UserId)

        obj = User.objects.get(id=int(UserId))
        print(obj)

        if data['writer'] == obj.writer:
            return JsonResponse("ok" + obj.id, safe=False, json_dumps_params={'ensure_ascii': False})
        else:
            return JsonResponse("fail", safe=False, json_dumps_params={'ensure_ascii': False})

