import base64
from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse, JsonResponse
from requests import Response

from .models import MyClothes
from register.models import Account

from rest_framework.decorators import api_view
from rest_framework import serializers
from rest_framework import viewsets
from rest_framework.parsers import JSONParser
from rest_framework.viewsets import ModelViewSet
from .serializers import MyClothesSerializer

# Create your views here.

@api_view(['GET'])
def check_data(request):
    print('test')
    Email = request.GET.get('email')
    print(Email)
    user = Account.objects.get(email=Email)
    # print(user)
    userID = user.id
    queryset = MyClothes.objects.filter(userid=userID).order_by('buydate')
    serializer = MyClothesSerializer(queryset, many=True)
    return JsonResponse(serializer.data, safe=False)


@api_view(['GET'])
def check_category(request, category):
    # print(request.GET.get('email'))
    # userID = request.GET.get('email')
    Email = request.GET.get('email')
    user = Account.objects.get(email=Email)
    userID = user.id
    queryset = MyClothes.objects.filter(userid=userID, category=category).order_by('buydate')
    serializer = MyClothesSerializer(queryset, many=True)
    return JsonResponse(serializer.data, safe=False)


@api_view(['GET'])
def check_cloth(request, pk):
    object = MyClothes.objects.filter(pk=pk)
    serializer = MyClothesSerializer(object)
    return JsonResponse(serializer.data, safe=False)


@api_view(['POST'])
def save_data(request):
    data = JSONParser().parse(request)
    serializer = MyClothesSerializer(data=data)
    if serializer.is_valid():
        serializer.save()
        return JsonResponse(serializer.data, status=200)
    return JsonResponse(serializer.errors, status=400)


@api_view(['PUT'])
def edit_data(request, pk):
    object = MyClothes.objects.get(pk=pk)
    data = JSONParser().parse(request)
    serializer = MyClothesSerializer(object, data=data)
    if serializer.is_valid():
        serializer.save()
        return JsonResponse(serializer.data, status=200)
    return JsonResponse(serializer.errors, status=400)


@api_view(['DELETE'])
def remove_data(request, pk):
    object = MyClothes.objects.get(pk=pk)
    object.delete()
    return HttpResponse(status=200)