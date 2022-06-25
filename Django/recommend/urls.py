from django.urls import path
from . import views

app_name = 'recommend'
urlpatterns = [
    path('', views.recommend),
    path('rcmd/', views.rcmd),
    path('mypiecategory/', views.mypiecategory),
    path('mypiecolor/', views.mypiecolor)
]