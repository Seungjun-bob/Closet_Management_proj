import io
from PIL import Image as im
import torch

from django.shortcuts import render
from django.views.generic.edit import CreateView

from .models import ImageModel
from .forms import ImageUploadForm

import pandas as pd

class UploadImage(CreateView):
    model = ImageModel
    template_name = 'image/imagemodel_form.html'
    fields = ["image"]

    def post(self, request, *args, **kwargs):
        form = ImageUploadForm(request.POST, request.FILES)
        if form.is_valid(): # is_valid() 메서드 데이터의 유효성 검사하는 역활
            img = request.FILES.get('image')
            img_instance = ImageModel(
                image=img
            )
            img_instance.save() # 넘파이나 바이너리로 저장하는 기능

            uploaded_img_qs = ImageModel.objects.filter().last()
            img_bytes = uploaded_img_qs.image.read()
            img = im.open(io.BytesIO(img_bytes))

            path_hubconfig = "C:/Users/crid2/django_yolo_web/yolov5_code" # yolov5 폴더 루트
            path_weightfile = "C:/Users/crid2/django_yolo_web/yolov5_code/train_file/yolov5s.pt" # yolov5 가중치로 학습한 pt파일위치
            model = torch.hub.load(path_hubconfig, 'custom',
                                   path=path_weightfile, source='local'  )

            results = model(img, size=640)
            test01 = results.pandas().xyxy[0]


            # Results

            results.render()
            for img in results.imgs:
                img_base64 = im.fromarray(img)
                img_base64.save("media/yolo_out/image0.jpg", format="JPEG")

            inference_img = "/media/yolo_out/image0.jpg"


            form = ImageUploadForm()
            context = {
                "form": form,
                "inference_img": inference_img,
                'test01' : test01



            }
            return render(request, 'image/imagemodel_form.html', context)

        else:
            form = ImageUploadForm()
        context = {
            "form": form
        }
        return render(request, 'image/imagemodel_form.html', context)




