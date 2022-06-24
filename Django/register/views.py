from rest_framework import viewsets
from rest_framework.response import Response
from .models import Account
from .serializers import UserSerializer
from django.http import JsonResponse
from rest_framework.parsers import JSONParser


def register(request):
    if request.method == 'POST':
        print("register_request_ok")
        print(request)
        android_data = JSONParser().parse(request)
        print(android_data)
        serializer = UserSerializer(data=android_data)

        serializer.is_valid(raise_exception=True)
        serializer.save()
        return JsonResponse(":okay:", safe=False, json_dumps_params={'ensure_ascii': False})


def login(request):
    if request.method == 'POST':
        print("login_request_ok")

        data = JSONParser().parse(request)
        print(data)
        # 이메일뽑아와서
        UserId = data["email"]
        print(UserId)
        # 해당 이메일에 해당하는 유저 obj 찾아오기
        obj = Account.objects.get(email=UserId)
        print(obj.accountid)
        print(data['pw'], obj.pw)

        if data['pw'] == obj.pw:
            return JsonResponse(":okay:" + str(obj.accountid) + ":", safe=False, json_dumps_params={'ensure_ascii': False})
        else:
            return JsonResponse(":fail:", safe=False, json_dumps_params={'ensure_ascii': False})


def findid(request):
    if request.method == 'POST':
        print("findid_request_ok")

        data = JSONParser().parse(request)
        print(data)
        # 이름뽑아와서
        UserName = data["name"]
        print(UserName)
        UserBirth = data["birth"]

        # 해당 이름에 해당하는 유저 obj 찾아오기
        obj = Account.objects.filter(name=UserName)
        print(obj)
        # 그 중 해당 생일에 해당하는 유저 obj 찾아오기
        obj2 = obj.get(birth=UserBirth)
        print(obj2.email)

        if obj2 != None: #obj2가 존재하면
            return JsonResponse(":okay:" + str(obj2.email) + ":", safe=False, json_dumps_params={'ensure_ascii': False})
        else:
            return JsonResponse(":fail:", safe=False, json_dumps_params={'ensure_ascii': False})


def findpw(request):
    if request.method == 'POST':
        print("findpw_request_ok")

        data = JSONParser().parse(request)
        print(data)
        # 이메일뽑아와서
        UserId = data["email"]
        print(UserId)
        # 해당 이메일에 해당하는 유저 obj 찾아오기
        obj = Account.objects.get(email=UserId)

        if data['name'] == obj.name and data['birth'] == obj.birth:
            return JsonResponse(":okay:" + str(obj.pw) + ":", safe=False, json_dumps_params={'ensure_ascii': False})
        else:
            return JsonResponse(":fail:", safe=False, json_dumps_params={'ensure_ascii': False})

def emailcheck(request):
    if request.method == 'POST':
        print("emailcheck_request_ok")

        data = JSONParser().parse(request)
        print(data)
        # 이메일뽑아와서
        UserId = data["email"]
        print(UserId)
        # 해당 이메일에 해당하는 유저 obj있는지 찾아오기
        try:
            obj = Account.objects.get(email=UserId)
            if obj.email is False:  # 해당 이메일을 가진 obj가 없으면
                return JsonResponse(":okay:", safe=False, json_dumps_params={'ensure_ascii': False})
            else:
                return JsonResponse(":fail:", safe=False, json_dumps_params={'ensure_ascii': False})
        except:
            return JsonResponse(":okay:", safe=False, json_dumps_params={'ensure_ascii': False})




