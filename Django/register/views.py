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

        # 클라이언트가 전달해주는 json데이터를 파싱
        data = JSONParser().parse(request)
        print(data)
        # 파싱한 json데이터에서 boardNo이름으로 정의된 값을 추출
        UserId = data["id"]
        print(boardNo)
        # 추출한 id를 이용해서 DB에서 데이터조회하기
        obj = Board.objects.get(boardNo=int(boardNo))
        print(obj)
        # db에서 조회한 데이터와 패스워드가 일치하면 로그인 사용자가 존재하는 것이므로 ok를 안드로이드로 보내고
        # 패스워드가 일치하지 않으면 사용자가 없으므로 fail을 안드로이드로 보내기
        # data['writer'] : json데이터(안드로이드가 보낸 데이터)
        # obj.writer : db에서 id로 조회한 레코드에 있는 writer(패스워드)
        if data['writer'] == obj.writer:
            return JsonResponse("ok", safe=False, json_dumps_params={'ensure_ascii': False})
        else:
            return JsonResponse("fail", safe=False, json_dumps_params={'ensure_ascii': False})

