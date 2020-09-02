package com.vncoder.retrofit2_employee

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.ContactCreate
import com.vncoder.retrofit2_employee.Model.PostContact
import com.vncoder.retrofit2_employee.Model.custom
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitClient
import kotlinx.android.synthetic.main.activity_detail_staff.*
import kotlinx.android.synthetic.main.processbar.view.*
import retrofit2.Call
import retrofit2.Response

class DetailStaff : AppCompatActivity() {
    val KITKAT_VALUE = 1002
    private var REQUEST_SELECT_IMAGE = 200
    var ContactCreate: ContactCreate = ContactCreate()
    var postContact: PostContact = PostContact()
    var contact: Contact = Contact()
    var custom: custom = custom()
    var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_staff)
        val bundle = intent.extras
        contact = bundle?.getSerializable("detailEmployee")!! as Contact

        detail_btn_avatar.setOnClickListener {
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

//            if (Build.VERSION.SDK_INT < 19) {
//                intent = Intent()
//                intent.action = Intent.ACTION_GET_CONTENT
//                intent.type = "*/*"
//                startActivityForResult(intent, REQUEST_SELECT_IMAGE)
//            } else {
//                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
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
        detail_FirstName.setText(contact.FirstName.toString())
        detail_LastName.setText(contact.LastName.toString())
        detail_Email.setText(contact.Email)
        detail_btn_avatar.setImageURI(Uri.parse(contact.custom_fields?.get(0)?.value.toString()))
        detail_contact_id.setText(contact.contact_id.toString())

        imageUri = contact.custom_fields?.get(0)?.value.toString()

        detail_update.setOnClickListener {
            llProgressBarDetail.visibility = View.VISIBLE

            custom.string_Test_Field = imageUri.toString()

            postContact.FirstName = detail_FirstName.text.toString()
            postContact.LastName = detail_LastName.text.toString()
            postContact.Email = detail_Email.text.toString()
            postContact.custom = custom
            ContactCreate.PostContact = postContact

            var request = RetrofitClient.instance
            var call: Call<ContactCreate> = request.postContact(ContactCreate)

            call.enqueue(object : retrofit2.Callback<ContactCreate> {
                override fun onFailure(call: Call<ContactCreate>?, t: Throwable?) {
                    Toast.makeText(this@DetailStaff, "cancel update", Toast.LENGTH_LONG).show()
                    llProgressBarDetail.visibility = View.GONE
                    if (call!!.isCanceled) {
                        Log.e("log1", "đã bị hủy")
                    } else {
                        Log.e("log2", "chưa bị hủy")
                    }
                }

                override fun onResponse(
                    call: Call<ContactCreate>?,
                    response: Response<ContactCreate>?
                ) {
                    if (response!!.isSuccessful) {
                        llProgressBarDetail.visibility = View.GONE
                        setResult(Activity.RESULT_OK)
                        finish()

                    } else {
                        Log.d("error1", response.code().toString())
                    }
                }
            })
            llProgressBarDetail.btn_cancel.setOnClickListener {
                call.cancel()
            }
        }
        detail_cancell.setOnClickListener {
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
//            imageUri = data?.data.toString()
//            detail_btn_avatar.setImageURI(Uri.parse(imageUri))
//        }

        if (requestCode == KITKAT_VALUE ) {
            if (resultCode == Activity.RESULT_OK) {
                // do something here
                imageUri = data?.data.toString()
            detail_btn_avatar.setImageURI(Uri.parse(imageUri))
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
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}