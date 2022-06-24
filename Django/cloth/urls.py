from django.urls import path

from . import views

urlpatterns = [
    path('save/', views.save_data),
    path('check/<int:accountid>/', views.check_data),
    path('check/<int:accountid>/<str:category>/', views.check_category),
    path('check/cloth/<int:myclothid>/', views.check_cloth),
    path('edit/<int:pk>/', views.edit_data),
    path('remove/<int:pk>/', views.remove_data),
]
