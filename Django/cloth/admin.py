from django.contrib import admin
from .models import User, MyClothes

# Register your models here.
admin.site.register(User)
admin.site.register(MyClothes)