from django.urls import path, include, re_path
from . import views

urlpatterns = [
    path('', include('rest_framework.urls', namespace='rest_framework_category'))
]