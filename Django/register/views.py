from rest_framework import viewsets
from rest_framework.response import Response
from .models import UserData
from .serializers import UserDataSerializer

class UserDataViewSet(viewsets.ModelViewSet):
    queryset = UserData.objects.all()
    serializer_class = UserDataSerializer

class CheckAccountViewset(viewsets.ModelViewSet):
    queryset = UserData.objects.all()
    serializer_class = UserDataSerializer

    def create(self, request):
        checkID = post_data['checkID']
        checkPW = post_data['checkPW']

        if UserData.objects.filter(email=checkID).exists():
            if UserData.objects.filter(pw=checkPW).exists():
                return Response(status=200)
        return Response(status=400)