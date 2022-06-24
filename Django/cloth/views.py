
from django.http import HttpResponse, JsonResponse

from .models import MyClothes
from rest_framework.decorators import api_view
from rest_framework.parsers import JSONParser
from .serializers import MyClothesSerializer

# Create your views here.

@api_view(['GET'])
def check_data(request, accountid):
    queryset = MyClothes.objects.filter(accountid=accountid).order_by('-buydate')
    serializer = MyClothesSerializer(queryset, many=True)
    return JsonResponse(serializer.data, safe=False)


@api_view(['GET'])
def check_category(request, accountid, category):
    queryset = MyClothes.objects.filter(accountid=accountid, mycategory=category).order_by('-buydate')

    serializer = MyClothesSerializer(queryset, many=True)
    return JsonResponse(serializer.data, safe=False)


@api_view(['GET'])
def check_cloth(request, myclothid):
    object = MyClothes.objects.filter(id=myclothid)
    serializer = MyClothesSerializer(object, many=True)

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