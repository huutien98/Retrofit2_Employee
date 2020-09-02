package com.vncoder.retrofit2_employee

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vncoder.retrofit2_employee.Model.ContactCreate
import com.vncoder.retrofit2_employee.Model.PostContact
import com.vncoder.retrofit2_employee.Model.custom
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitClient
import kotlinx.android.synthetic.main.activity_add_staff.*
import kotlinx.android.synthetic.main.processbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class AddStaff : AppCompatActivity() {
    val KITKAT_VALUE = 1002
    private var REQUEST_SELECT_IMAGE =1
    private var uriImage :String? = null
    val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" +
                "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_staff)

        btn_avatar.setOnClickListener {

            val intent: Intent

            if (Build.VERSION.SDK_INT < 19) {
                intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(intent, KITKAT_VALUE)
            } else {
                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, KITKAT_VALUE)
            }

//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    REQUEST_SELECT_IMAGE);
//            } else {
//                intent = Intent(Intent.ACTION_OPEN_DOCUMENT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                intent.addCategory(Intent.CATEGORY_OPENABLE)
//                intent.type = "*/*"
//                startActivityForResult(intent, REQUEST_SELECT_IMAGE)
//            }


//            val intent = Intent(
//                Intent.ACTION_OPEN_DOCUMENT,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            )
//            startActivityForResult(intent, REQUEST_SELECT_IMAGE)
        }

        edt_FirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        edt_LastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        edt_Email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        btn_create.setOnClickListener {


            if (edt_FirstName.text.toString().trim().isEmpty()){
                edt_FirstName.setError("FirstName not null")
            }else if (edt_LastName.text.toString().trim().isEmpty()){
                edt_LastName.setError("LastName not null")
            }else if (edt_Email.text.toString().trim().isEmpty()
                || !EMAIL_ADDRESS.matcher(edt_Email.text.toString()).matches()){
                edt_Email.setError("Email invalid")
            }else{
                llProgressBarDetail.visibility = View.VISIBLE
                var custom : custom = custom()
                custom.string_Test_Field = uriImage.toString()

                var ContactCreate : ContactCreate = ContactCreate()
                val postContact :PostContact = PostContact()
                postContact.FirstName = edt_FirstName.text.toString().trim()
                postContact.LastName = edt_LastName.text.toString().trim()
                postContact.Email = edt_Email.text.toString().trim()
                postContact.custom =  custom
                ContactCreate.PostContact = postContact

                var request = RetrofitClient.instance
                var call: Call<ContactCreate> = request.postContact(ContactCreate)

                call.enqueue(object : Callback<ContactCreate> {
                    override fun onResponse(
                        call: Call<ContactCreate>?,
                        response: Response<ContactCreate>?
                    ) {
                        if (response!!.isSuccessful) {
                            finish()
                        } else {
                            Toast.makeText(
                                this@AddStaff,
                                "response not successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ContactCreate>?, t: Throwable?) {
                        Toast.makeText(this@AddStaff, "onFailure", Toast.LENGTH_SHORT).show()
                        llProgressBarDetail.visibility = View.GONE
                        Log.d("this5", t.toString())
                    }
                })

                llProgressBarDetail.btn_cancel.setOnClickListener {
                    call.cancel()
                }
            }

        }



        btn_cancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_SELECT_IMAGE && data != null && data.data != null) {
//            uriImage =data.data.toString()
//            btn_avatar.setImageURI(Uri.parse(uriImage))
//        }
        if (requestCode == KITKAT_VALUE ) {
            if (resultCode == Activity.RESULT_OK) {
                // do something here
                uriImage =data?.data.toString()
            btn_avatar.setImageURI(Uri.parse(uriImage))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        if (requestCode == REQUEST_SELECT_IMAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "*/*"
                startActivityForResult(intent, REQUEST_SELECT_IMAGE)
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}