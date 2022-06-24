from django.urls import path, include, re_path
from . import views

urlpatterns = [
    path('', views.register),
    path('login', views.login),
    path('findid', views.findid),
    path('findpw', views.findpw),
    path('emailcheck', views.emailcheck)

]
