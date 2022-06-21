import io
from PIL import Image as im
import torch

from django.shortcuts import render
from django.views.generic.edit import CreateView

from .models import ImageModel
from .forms import ImageUploadForm

import urllib.request

# color avg 추출 lib
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import numpy as np
from scipy.stats import mode


# 스토리지 이미지 이름을 이용해서 접근 후 결과값 반환 코드 작성필요
# 1. 이미지 url을 모델에 넣어 돌려보기
# 1-1. 불필요한 거 지우기(버튼 등..)
# 1-2. 상하의 구분 없는 옷에 생기는 에러 해결
# 2. 결과 값(json)을 앱에서 출력하기
# 3. 실제 앱에서 생성된 이미지 url을 모델에 넣기(실시간)
# 4. (2), (3) 합쳐서 돌려보기
# 5. 색상 추출하기
# 6. 색상 결과 값 json으로 앱에서 출력하기
# 7. (4), (6) 합쳐서 돌려보기


class UploadImage(CreateView):
    model = ImageModel
    template_name = 'image/imagemodel_form.html'
    fields = ["image"]

    def post(self, request, *args, **kwargs):
        form = ImageUploadForm(request.POST, request.FILES)
        if form.is_valid(): # is_valid() 메서드 데이터의 유효성 검사하는 역할
            url = "https://closetimg103341-dev.s3.us-west-2.amazonaws.com/test2.png"
            path = "C:/Users/a/PycharmProjects/Closet_Management_proj/Django/media/images/" + "test3.png"
            urllib.request.urlretrieve(url, path)
            img = 'C:/Users/a/PycharmProjects/Closet_Management_proj/Django/media/images/test3.png'
            img_instance = ImageModel(
                image=img
            )
            img_instance.save() # 넘파이나 바이너리로 저장하는 기능

            uploaded_img_qs = ImageModel.objects.filter().last()
            img_bytes = uploaded_img_qs.image.read()
            img = im.open(io.BytesIO(img_bytes))

            path_hubconfig = "C:/Users/a/PycharmProjects/Closet_Management_proj/Django/yolov5_code" # yolov5 폴더 루트
            path_weightfile = "C:/Users/a/PycharmProjects/Closet_Management_proj/Django/yolov5_code/train_file/best.pt" # yolov5 가중치로 학습한 pt파일위치
            model = torch.hub.load(path_hubconfig, 'custom',
                                   path=path_weightfile, source='local'  )


            # 이미지 라벨 갯수 옵션 ( 보통 2개로 세팅 (상의,하의 ) , 사진이 1인 전신샷이라고 가정)
            model.max_det = 2

            # 라벨 지정 학률 (너무 낮은 확률이면 애매한 옷도 모두 지정해버림)
            model.conf = 0.6

            # 라벨링 된 옷 데이터만 따로 저장 기능



            results = model(img, size=640)


            # 크롭파일 이미지화 진행중
            # 이미지가 한개일때 에러 발생 , 해결해야됨
            crops = results.crop(save=True)  # cropped detections dictionary
            test01 = crops[0]
            test02 = crops[1]

            # imgNp = mpimg.imread('C:/Users/park/PycharmProjects/django_yolo_web/runs/detect/exp6/cro1ps/short_sleeved_shirt/image0.jpg')
            # # img color avg value
            # Red = []
            # Green = []
            # Blue = []
            #
            # for x in imgNp:
            #     for y in x:
            #         Red.append(y[0])
            #         Green.append(y[1])
            #         Blue.append(y[2])
            #
            # R_max = max(Red)
            # G_max = max(Green)
            # B_max = max(Blue)
            #
            # R_avg = sum(Red) / len(Red)
            # G_avg = sum(Green) / len(Green)
            # B_avg = sum(Blue) / len(Blue)
            #
            # R_mode = mode(Red)
            # G_mode = mode(Green)
            # B_mode = mode(Blue)
            #
            # print("Max Value")
            # print("R : ", R_max)
            # print("G : ", G_max)
            # print("B : ", B_max)
            #
            # print("Avg Value")
            # print("R : ", R_avg)
            # print("G : ", G_avg)
            # print("B : ", B_avg)
            #
            # print("Mode Value")
            # print("R : ", R_mode[0][0])
            # print("G : ", G_mode[0][0])
            # print("B : ", B_mode[0][0])

            # 반환시 좌표로 넘파이 어레이로 반환 다시 이미지파일 변환 과정 필요





            # 추가 옷 종류만 json 파일로 표시 가능
            cloths_type = results.pandas().xyxy[0]['name'].to_json(orient='records')
            #test = results.pandas().xyxy[0] (라벨데이터 전체출력)

            # Results 업로드 이미지와 추론라벨 넘파이 결과값을 다시 이미지로 변환

            results.render()
            for img in results.imgs:
                img_base64 = im.fromarray(img)
                # 결과 저장 및 폴더지정
                img_base64.save("media/yolo_out/result.jpg", format="JPEG")
            inference_img = "/media/yolo_out/result.jpg"


            form = ImageUploadForm()
            context = {
                "form": form,
                "inference_img": inference_img,
                'cloths_type' : cloths_type,
                'test01' : test01 ,
                'test02' : test02

            }
            return render(request, 'image/imagemodel_form.html', context)

        else:
            form = ImageUploadForm()
        context = {
            "form": form
        }
        return render(request, 'image/imagemodel_form.html', context)



