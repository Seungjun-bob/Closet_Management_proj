from django.urls import path
from django.conf import settings
from django.conf.urls.static import static


from . import views


app_name = "image"

urlpatterns = [
    #path("", views.UploadImage.as_view(), name="upload_image_url"),
    path("test/", views.doit, name = "test"),
    path("print/", views.test, name="print"),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)