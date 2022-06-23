from django.contrib import admin
from django.urls import path, include
from . import views


urlpatterns = [
    path('save/', views.save_data),
    path('check/', views.check_data),
    path('check/<str:category>/', views.check_category),
    path('check/<int:pk>/', views.check_cloth),
    path('edit/<int:pk>/', views.edit_data),
    path('remove/<int:pk>/', views.remove_data),
]
