from django.db import models

class Account(models.Model):
    accountid = models.IntegerField(primary_key=True)
    GENDER_CHOICES=(
        (1,'남성'),
        (2,'여성'),
        )
    email = models.CharField(db_column='email', max_length=40)
    pw = models.CharField(db_column='pw', max_length=40)
    pwcheck = models.CharField(db_column='pwcheck', max_length=40)
    name = models.CharField(db_column='name', max_length=10)
    birth = models.DateField(db_column='birth')
    gender = models.IntegerField(db_column='gender', choices=GENDER_CHOICES)

    def insert_User(self, email, pw, pwcheck, name, birth, gender):
        if not email:
            raise ValueError('email을 입력하세요')
        if not pw:
            raise ValueError('email을 입력하세요')
        if not pwcheck:
            raise ValueError('email을 입력하세요')
        if not name:
            raise ValueError('email을 입력하세요')
        if not birth:
            raise ValueError('email을 입력하세요')
        if not gender:
            raise ValueError('email을 입력하세요')
        user = self.model(
            email=email,
            pw=pw,
            pwcheck=pwcheck,
            name=name,
            birth=birth,
            gender=gender
        )
        user.save(using=self._db)
        return user

    class Meta:
        managed = False
        db_table = 'account'

    def __str__(self):
        return "이름 : " + self.name