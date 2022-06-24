from django.shortcuts import render
from rest_framework import viewsets
from rest_framework.response import Response

# class CheckAccountViewset(viewsets.ModelViewSet):
#     queryset = UserData.objects.all()
#     serializer_class = UserDataSerializer
#
#     # def create(self, request):
#     #     checkID = post_data['checkID']
#     #     checkPW = post_data['checkPW']
#     #
#     #     if UserData.objects.filter(email=checkID).exists():
#     #         if UserData.objects.filter(pw=checkPW).exists():
#     #             return Response(status=200)
#     #     return Response(status=400)
# # Create your views here.
