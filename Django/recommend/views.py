from django.shortcuts import render
from django.template import loader
from django.http import HttpResponse, JsonResponse


def recommend(request) :
    name = request.GET.get('id', "")
    context = {'result' : name}
    return render(request, 'test.html', context)

