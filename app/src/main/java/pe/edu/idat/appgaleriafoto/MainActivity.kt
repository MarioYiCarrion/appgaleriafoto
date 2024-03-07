package pe.edu.idat.appgaleriafoto

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import pe.edu.idat.appgaleriafoto.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var archivo: File
    private var rutaFotoActual = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btntomarfoto.setOnClickListener(this)
        binding.btncompartirfoto.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btntomarfoto -> tomarfoto()
            R.id.btncompartirfoto -> compartirfoto()
        }
    }

    private fun compartirfoto() {

    }

    private fun tomarfoto() {
        //abrirCamara.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        val intentcamara = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            it.resolveActivity(packageManager).also {
                componente ->

            }
        }
        abrirCamara.launch(intentcamara)

    }
    private val abrirCamara = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        resultado ->
        if(resultado.resultCode == RESULT_OK){
            val foto = resultado.data!!
            val fotoBitMap = foto.extras!!.get("data") as Bitmap
            binding.ivfoto.setImageBitmap(fotoBitMap)
        }
    }
}