package pe.edu.idat.appgaleriafoto

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
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
                crearImagenFoto()
                val fotoUri: Uri = FileProvider.getUriForFile(applicationContext,
                    "pe.edu.idat.appgaleriafoto.fileprovider",
                    archivo)
                it.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri)

            }
        }
        abrirCamara.launch(intentcamara)

    }
    private val abrirCamara = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        resultado ->
        if(resultado.resultCode == RESULT_OK){
            val fotoBitMap = convertirFotoBitMap()
            binding.ivfoto.setImageBitmap(fotoBitMap)
        }
    }
    private fun convertirFotoBitMap(): Bitmap{
        return BitmapFactory.decodeFile(archivo.toString())
    }
    private fun crearImagenFoto(){
        val directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        archivo = File.createTempFile(
            "IMG_${System.currentTimeMillis()}",
            ".jpg",
            directorio
        )
        rutaFotoActual = archivo.absolutePath
    }
}