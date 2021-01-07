package com.tech.myruntimeptest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    var list: ListView? = null
    private val READ_STORAGE_CODE = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(R.id.listview)

        var status =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//1)get the permission status

        //2)check whether user has granted permission or not?
        //if not request again
        if (status == PackageManager.PERMISSION_GRANTED) {
            fetchData()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_CODE
            )
        }


    }

    //3)override onPermissions Result method
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchData()
        } else {
            Toast.makeText(
                this,
                "user is not allowing to accesss the internal data",
                Toast.LENGTH_SHORT
            )
        }
    }

    //to get menus in koltin file we must override this method
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        return super.onCreateOptionsMenu(menu)
    }
//to get the click event on menus we override flloing method
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_android -> showTest("android menu")
            R.id.menu_java -> showTest("java menu")
            R.id.menu_cpp -> showTest("cpp menu")
            R.id.menu_call -> showTest("caLL menu")
            R.id.menu_kotlin -> showTest("kotlin menu")
            R.id.menu_python -> showTest("python menu")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showTest(s: String) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun fetchData() {
        var path = "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/"

        var f = File(path)
        if (!f.exists()) {
            path = "/storage/SdCard/0/WhatsApp/Media/WhatsApp Images/"
        }
        var namesList = f.list()

        var myadapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, namesList)
        list?.adapter = myadapter
    }
}