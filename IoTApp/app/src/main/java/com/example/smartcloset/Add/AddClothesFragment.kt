package com.example.smartcloset.Add

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.smartcloset.R
import com.example.smartcloset.Main.MainActivity
import kotlinx.android.synthetic.main.addclothes.*
import kotlinx.android.synthetic.main.addclothes.view.*

class AddClothesFragment: Fragment() {

    val PERMISSION_Album = 101 // 앨범 권한 처리
    val REQUEST_STORAGE = 1

    val PERMISSION_CAMERA = 1001 //맞나?
    val REQUEST_CAMERA = 2 //맞나?

    lateinit var realUri: Uri

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    var datalist =ArrayList<Int>()
    lateinit var mainActivity: MainActivity

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewF = inflater.inflate(R.layout.addclothes, container, false)

        val myadapter1 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1, android.R.layout.simple_spinner_item)

        myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewF.tag1.adapter = myadapter1
        viewF.tag1.setSelection(0)

        viewF.tag1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewF.tag1txt?.text = (view as? TextView)?.text
                viewF.tag2.setSelection(0)
                viewF.tag3.setSelection(0)
                when(viewF.tag1txt?.text){
                    "" -> {
                        //두번째 태그 오픈
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                viewF.tag2txt?.text = (view as? TextView)?.text
                                // 세번째 태그 오픈
                                val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                viewF.tag3.adapter = myadapter3
                                viewF.tag3.setSelection(0)

                                viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                        viewF.tag3txt?.text = (view as? TextView)?.text
                                    }
                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                    }
                                }
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    }
                    "상의" -> {
                        //두번째 태그 오픈
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_1, android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    viewF.tag2txt?.text = (view as? TextView)?.text

                                    when (viewF.tag2txt.text) {
                                        "" -> {
                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                    viewF.tag3txt?.text = (view as? TextView)?.text
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                        else -> {
                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_4, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                    viewF.tag3txt?.text = (view as? TextView)?.text
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                    }
                                }
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }
                            }
                    }
                    "하의" -> {
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_2,android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    viewF.tag2txt?.text = (view as? TextView)?.text

                                    when (viewF.tag2txt.text) {
                                        "" -> {
                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                                                    viewF.tag3txt?.text = (view as? TextView)?.text
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                        "슬랙스" -> {
                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_5, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                                                        viewF.tag3txt?.text = (view as? TextView)?.text
                                                    }
                                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                                    }
                                                }
                                        }
                                        "청바지" -> {

                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_6, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                                                        viewF.tag3txt?.text = (view as? TextView)?.text
                                                    }
                                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                                    }
                                                }
                                        }
                                        "반바지" -> {
                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_5, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                                                    viewF.tag3txt?.text = (view as? TextView)?.text
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                        "츄리닝" -> {
                                            // 세번째 태그 오픈
                                            val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_5, android.R.layout.simple_spinner_item)
                                            myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            viewF.tag3.adapter = myadapter3
                                            viewF.tag3.setSelection(0)

                                            viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                                                    viewF.tag3txt?.text = (view as? TextView)?.text
                                                }
                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                }
                                            }
                                        }
                                    }
                                }
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }
                        }
                    }

                    "아우터" -> {
                        val myadapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_3,android.R.layout.simple_spinner_item)
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        viewF.tag2.adapter = myadapter2
                        viewF.tag2.setSelection(0)

                        viewF.tag2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                viewF.tag2txt?.text = (view as? TextView)?.text
                                when (viewF.tag2txt.text) {
                                    "" -> {
                                        // 세번째 태그 오픈
                                        val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagnone, android.R.layout.simple_spinner_item)
                                        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        viewF.tag3.adapter = myadapter3
                                        viewF.tag3.setSelection(0)

                                        viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                viewF.tag3txt?.text = (view as? TextView)?.text
                                            }
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }
                                        }

                                    }
                                    else -> {
                                        // 세번째 태그 오픈
                                        val myadapter3 = ArrayAdapter.createFromResource(mainActivity, R.array.tagdata1_4, android.R.layout.simple_spinner_item)
                                        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        viewF.tag3.adapter = myadapter3
                                        viewF.tag3.setSelection(0)

                                        viewF.tag3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                viewF.tag3txt?.text = (view as? TextView)?.text
                                            }
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }
                                        }

                                    }
                                }

                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                result.text = "선택된 가수가 없습니다."
            }

        }






        // 앨범 버튼 클릭 리스너 구현
        viewF.album_addclothes.setOnClickListener{
            requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
        }

        // 카메라 버튼 클릭 리스너 구현
        viewF.camera_addclothes.setOnClickListener(View.OnClickListener {
            requirePermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)

        })

        viewF.cancel_addclothes.setOnClickListener{
//            Toast.makeText(this,"취소 버튼입니다", Toast.LENGTH_SHORT).show()
        }
        viewF.save_addclothes.setOnClickListener{
//            Toast.makeText(this,"저장 버튼입니다", Toast.LENGTH_SHORT).show()
        }
        return viewF
    }

    /**자식 액티비티에서 권한 요청 시 직접 호출하는 메서드
     * @param permissions 권한 처리를 할 권한 목록
     * @param requestCode 권한을 요청한 주체가 어떤 것인지 구분하기 위함.
     * */
    @RequiresApi(Build.VERSION_CODES.N)
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
//        Logger.d("권한 요청")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            // isAllPermissionsGranted : 권한이 모두 승인 되었는지 여부 저장
            // all 메서드를 사용하면 배열 속에 들어 있는 모든 값을 체크할 수 있다.
            val isAllPermissionsGranted =
                permissions.all { ActivityCompat.checkSelfPermission(mainActivity,it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(mainActivity, permissions, requestCode)
            }
        }
    }
    /** 사용자가 권한을 승인하거나 거부한 다음에 호출되는 메서드
     * @param requestCode 요청한 주체를 확인하는 코드
     * @param permissions 요청한 권한 목록
     * @param grantResults 권한 목록에 대한 승인/미승인 값, 권한 목록의 개수와 같은 수의 결괏값이 전달된다.
     * */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> openCamera()
            PERMISSION_Album -> openGallery()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> Toast.makeText(
                mainActivity,
                "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()

            PERMISSION_Album -> Toast.makeText(
                mainActivity,
                "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_STORAGE)

    }



    //***************카메라
    @RequiresApi(Build.VERSION_CODES.N)
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            realUri = uri // var 맞나?
            // MediaStore.EXTRA_OUTPUT을 Key로 하여 Uri를 넘겨주면
            // 일반적인 Camera App은 이를 받아 내가 지정한 경로에 사진을 찍어서 저장시킨다.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQUEST_CAMERA)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.jpg"
    }

    private fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return mainActivity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }




    //******************실행
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    realUri?.let { uri ->
                        imagePreview.setImageURI(uri)
                    }
                }
                REQUEST_STORAGE -> {
                    data?.data?.let { uri ->
                        imagePreview.setImageURI(uri)
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Fragment에선 Activity에서 사용하는 메소드들을 사용할 수 없기 때문에 onAttach(프레그먼트가 액티비티에 붙여지는 생명주기)에서
        //Context를 받아와 MainActivity로 캐스팅해주면 메소드들을 사용할 수 있다.
        //여기에서는 Adapter를 생성할 때 context를 넘겨주기 위해 이 방법을 사용했다.
        mainActivity = context as MainActivity

    }

}