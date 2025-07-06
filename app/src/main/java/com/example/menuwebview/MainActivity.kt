package com.example.menuwebview

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerNoticias: RecyclerView
    private lateinit var adapter: NoticiaAdapter
    private var listaNoticias = mutableListOf<Noticia>()
    private var posicionSeleccionada: Int = -1 // Para el context menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inicializar lista de noticias
        listaNoticias = mutableListOf(
            Noticia(
                "Google presenta Android 15",
                "Google anunció nuevas funciones de IA en Android 15.",
                "https://www.xataka.com/android/google-presenta-android-15"
            ),
            Noticia(
                "Lanzan nuevo chip Snapdragon",
                "Qualcomm lanza el Snapdragon 8 Gen 3 para móviles de gama alta.",
                "https://www.xataka.com/moviles/snapdragon-8-gen-3"
            ),
            Noticia(
                "Avances en baterías",
                "Nuevas baterías prometen durar el doble en smartphones.",
                "https://www.xataka.com/energia/baterias-nuevas"
            )
            // Puedes agregar más noticias
        )

        // Configurar RecyclerView y Adapter
        recyclerNoticias = findViewById(R.id.recyclerNoticias)
        recyclerNoticias.layoutManager = LinearLayoutManager(this)
        adapter = NoticiaAdapter(listaNoticias) { view, position ->
            posicionSeleccionada = position
            view.showContextMenu()
        }
        recyclerNoticias.adapter = adapter

        // Registrar el RecyclerView para context menu
        registerForContextMenu(recyclerNoticias)
    }

    // --------------------------------
    // Opciones del menú (Options Menu)
    // --------------------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_acerca -> {
                Toast.makeText(this, "Acerca de MenuWebView", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_salir -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // --------------------------------
    // Menú contextual (Context Menu)
    // --------------------------------
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_abrir_webview -> {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", listaNoticias[posicionSeleccionada].url)
                startActivity(intent)
                true
            }
            R.id.menu_compartir -> {
                Toast.makeText(this, "Compartir: ${listaNoticias[posicionSeleccionada].titulo}", Toast.LENGTH_SHORT).show()
                // Aquí puedes implementar la función de compartir
                true
            }
            else -> super.onContextItemSelected(item)
        }


    }
}
